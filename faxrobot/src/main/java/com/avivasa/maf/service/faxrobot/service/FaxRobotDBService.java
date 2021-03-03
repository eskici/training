package com.avivasa.maf.service.faxrobot.service;

import com.avivasa.maf.dataaccess.crm.repository.servis.DocumentRepository;
import com.avivasa.maf.dataaccess.crm.repository.servis.IndexStationLogRepository;
import com.avivasa.maf.dbmodel.crm.servis.Document;
import com.avivasa.maf.dbmodel.crm.servis.IndexStationLog;
import com.avivasa.maf.service.faxrobot.dto.DocumentDto;
import com.avivasa.maf.service.faxrobot.enums.DocumentProcessTypeEnum;
import com.avivasa.maf.service.faxrobot.enums.DocumentStatusEnum;
import com.avivasa.maf.service.faxrobot.util.FaxRobotConstants;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaxRobotDBService {
	
	@Autowired
	private IndexStationLogRepository indexStationLogRepository;

	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
	private DozerBeanMapper dozerMapper;
	
	public DocumentDto findOneRawRecord() {
		Document rawDocument = documentRepository.findTopByStatusAndCmRefIsNotNullAndLastCmRefIsNullOrderByDocumentDateAsc(DocumentStatusEnum.DURUM_FILE_CREATED_FROM_APPLICATION.getStatusCode());
		return rawDocument == null ? null : dozerMapper.map(rawDocument, DocumentDto.class);
	}
	
	public DocumentDto insertDocument(DocumentDto isdocument) {
		Document newDocument = dozerMapper.map(isdocument, Document.class);
		newDocument = documentRepository.save(newDocument);
		return dozerMapper.map(newDocument, DocumentDto.class);
	}	
	
	public void updateDocument(DocumentDto isdocument) {
		Document newDocument = dozerMapper.map(isdocument, Document.class);
		documentRepository.save(newDocument);
	}	

	/**
	 * @param documentDto
	 * @param resultBarcodeProcess
	 */
	public void logResultBarcodeProcess(DocumentDto documentDto, int resultBarcodeProcess) {
		if (resultBarcodeProcess == 0 || resultBarcodeProcess >= 10) {
			if (documentDto.getPackageCode() != null) {
				if ("E".equalsIgnoreCase(documentDto.getPackageCode().trim())) {
					indexStationKaydet(documentDto, DocumentProcessTypeEnum.BESTALEP, 110088);
				} else {
					indexStationKaydet(documentDto, DocumentProcessTypeEnum.HAYAT_TALEP, 110088);
				}
			}
		} else {
			indexStationKaydet(documentDto, DocumentProcessTypeEnum.INDEXLENMEMIS, 100037);
		}
	}

	public void indexStationKaydet(DocumentDto documentDto, DocumentProcessTypeEnum nextProcessType, int user) {
		String processName = documentDto.getProcessType();
		String fileNetId = documentDto.getCmRef();
		insertIndexStationLog(DocumentProcessTypeEnum.getDocumentProcessEnumByName(processName).getProcessId(), nextProcessType.getProcessId(), user, fileNetId);
	}

	/* Ana dokuman, sayfa sayısı kadar child dokümanlara ayrıldığı için indeks istasyonunda 
	 * sadece child dokümanların işlenmesi gerekmektedir.
	 * O nedenle ana doküman TAMAMLANDI statüsüne çekilir ve indeks istasyonunda
	 * sol menüye gelmesi engellenir.
	 */
	public void completeParentDocumentByStatus(DocumentDto parentDocumentDto, DocumentStatusEnum status) {
		parentDocumentDto.setStatus(status.getStatusCode());
		parentDocumentDto.setCompleted(FaxRobotConstants.COMPLETED);
		updateDocument(parentDocumentDto);
	}
	
	private void insertIndexStationLog(int documentPrevStatus, int documentNextStatus, int registryNo, String cmRef) {
		IndexStationLog indexStationLog = new IndexStationLog();
		indexStationLog.setDocumentPrevStatus(documentPrevStatus);
		indexStationLog.setDocumentNextStatus(documentNextStatus);
		indexStationLog.setRegistryNo(registryNo);
		indexStationLog.setPath("FAXROBOTUPROCESS");
		indexStationLog.setDescription("FAXROBOTU");
		indexStationLog.setCmRef(cmRef);
		indexStationLogRepository.save(indexStationLog);
	}
}
