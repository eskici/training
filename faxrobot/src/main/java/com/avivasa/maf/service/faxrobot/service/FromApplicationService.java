package com.avivasa.maf.service.faxrobot.service;

import com.avivasa.exception.MafSystemException;
import com.avivasa.logging.Logger;
import com.avivasa.logging.LoggerFactory;
import com.avivasa.maf.service.faxrobot.dto.DocumentDto;
import com.avivasa.maf.service.faxrobot.enums.DocumentStatusEnum;
import com.avivasa.maf.service.faxrobot.util.FaxRobotConstants;
import com.avivasa.maf.service.faxrobot.util.FaxRobotUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Taner YILDIRIM
 */

@Service
public class FromApplicationService extends FaxRobotDirectories {

    private Logger log = LoggerFactory.getLogger(FromApplicationService.class);

    @Autowired
    private FaxRobotDBService dbOperations;

    @Autowired
    private FaxRobotDYSService dysOperations;

    public DocumentDto fetchRawDocument() {
        return dbOperations.findOneRawRecord();
    }

    /**
     * @param documentDto
     * @throws MafSystemException
     * @return
     */
    public DocumentDto insertRawDocument(DocumentDto documentDto) throws MafSystemException {
        log.info("{} için processRawDocument süreci başlatıldı", documentDto.getInfoLog());

        if (documentDto != null && documentDto.getCmRef() != null && !StringUtils.isEmpty(documentDto.getCmRef().trim())) {
            String filePath = createPhysicallyFileNetDocumentById(documentDto);

            if(!StringUtils.isEmpty(filePath)) {
                File cmFile = new File(filePath);
                documentDto.setNewDocumentName(cmFile.getName());
                documentDto.setNewDocumentPath(cmFile.getPath());
                documentDto.setDocumentSize(cmFile.length());
                documentDto.setDocumentChannel(FaxRobotConstants.EVRAK);
                documentDto.setProcessType(FaxRobotConstants.EVRAK);
                documentDto.setEntityType(FaxRobotConstants.INDEXLENMEMIS);
                documentDto.setStatus(DocumentStatusEnum.DURUM_FILE_CREATED.getStatusCode());
                documentDto.setCompleted(FaxRobotConstants.NOT_COMPLETED);

                documentDto = dbOperations.insertDocument(documentDto);
                log.info("{} statüsü 2 olarak güncellenmiştir.", documentDto.getInfoLog());
            }
        }
        return documentDto;
    }

    private String createPhysicallyFileNetDocumentById(DocumentDto documentDto) throws MafSystemException {
        Integer documentNo = documentDto.getDocumentNo();
        log.info("{} için createPhysicallyFileNetDocumentById çağrıldı", documentDto.getInfoLog());

        byte[] documentContent = dysOperations.getDocumentContentByFileNetId(documentDto.getCmRef());

        if (documentContent.length > 0) {

            final String filePath = FaxRobotUtil.createFilePath(getDirSplitToPages(), documentNo,
                    FaxRobotUtil.getFileExtension(documentDto.getDocumentName()));

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(documentContent);
                fos.flush();
                fos.close();
                log.info("{} nolu doküman için {} dosyası oluşturuldu", documentNo, filePath);
                return filePath;
            } catch (Exception ex) {
                log.error("{} nolu doküman için createPhysicallyFileNetDocumentById metodunda hata oluştu !!!", documentNo, ex);
            }
        } else {
            log.info("{} nolu dokümana ait içerik DYS üzerinde bulunamadı. filenetId : {}", documentNo, documentDto.getCmRef());
        }
        return null;
    }

}
