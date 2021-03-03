package com.avivasa.maf.service.faxrobot.service;

import com.avivasa.logging.Logger;
import com.avivasa.logging.LoggerFactory;
import com.avivasa.maf.service.faxrobot.dto.DocumentDto;
import com.avivasa.maf.service.faxrobot.enums.DocumentProcessTypeEnum;
import com.avivasa.maf.service.faxrobot.enums.DocumentStatusEnum;
import com.avivasa.maf.service.faxrobot.util.FaxRobotUtil;
import com.avivasa.maf.util.MafUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Taner YILDIRIM
 */
@Service
public class WOBarcodeDocumentProcessService extends DocumentProcessService  {

    private Logger log = LoggerFactory.getLogger(WOBarcodeDocumentProcessService.class);

    @Override
    public void processDocument(DocumentDto parentDocumentDto) {

        log.info("{} dosyası için barkodsuz süreç başlatılacak", parentDocumentDto.getInfoLog());
        List<DocumentDto> processDocuments = createDocumentsWOBarcode(parentDocumentDto);
        processDocumentWOBarcode(processDocuments);

        if(processDocuments.size() > 1) {
            dbOperations.completeParentDocumentByStatus(parentDocumentDto, DocumentStatusEnum.DURUM_SPLITTED);
        }
    }

    @Override
    public boolean hasBarcode(DocumentDto documentDto) {
        return documentDto.getBarcodeMap().size() == 0;
    }

    private void processDocumentWOBarcode(List<DocumentDto> splittedDocuments) {

        for (final DocumentDto documentDto : splittedDocuments) {

            try {
                if (!documentDto.isChildDocument()) {
                    processParentDocument(documentDto);
                } else {
                    processChildDocument(documentDto);
                }

                dbOperations.indexStationKaydet(documentDto, DocumentProcessTypeEnum.INDEXLENMEMIS, 100037);
            } catch (Exception ex) {
                updateDocumentByStatus(documentDto, DocumentStatusEnum.DURUM_HATALI_DOKUMAN);
            }
        }
    }


    private void processChildDocument(DocumentDto documentDto) throws Exception {
        documentDto = dbOperations.insertDocument(documentDto);

        addDocumentToFilenetAndUpdateDb(documentDto);
        updateDocumentByStatus(documentDto, DocumentStatusEnum.DURUM_PROCESSED);
    }

    private void processParentDocument(DocumentDto documentDto) throws Exception {

        if (MafUtils.isEmpty(documentDto.getCmRef())) {
            addDocumentToFilenetAndUpdateDb(documentDto);
        }
        updateDocumentByStatus(documentDto, DocumentStatusEnum.DURUM_PROCESSED);
    }

    /**
     * @param documentDto
     */
    private void updateDocumentByStatus(final DocumentDto documentDto, DocumentStatusEnum status) {
        documentDto.setStatus(status.getStatusCode());
        dbOperations.updateDocument(documentDto);
        log.info("{} dosyasının statüsü {} olarak güncellenmiştir", documentDto.getInfoLog(), status.getStatusCode());
    }

    /**
     * @param parentDocumentDto
     * @return
     */
    private List<DocumentDto> createDocumentsWOBarcode(DocumentDto parentDocumentDto) {

        log.info("{} dosyası için createDocumentsWOBarcode süreci başlatıldı", parentDocumentDto.getInfoLog());
        boolean wantsToBeSplitted = FaxRobotUtil.wantsToBeSplitted(parentDocumentDto.getDocumentName());

        List<DocumentDto> processDocuments = new ArrayList<>();

        if (wantsToBeSplitted) {
            processDocuments = splitIntoChildDocuments(parentDocumentDto);
        } else {
            parentDocumentDto.setChildDocument(false);
            processDocuments.add(parentDocumentDto);
        }
        return processDocuments;
    }
}
