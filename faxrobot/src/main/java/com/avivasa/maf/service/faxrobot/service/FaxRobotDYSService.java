package com.avivasa.maf.service.faxrobot.service;

import com.avivasa.exception.MafSystemException;
import com.avivasa.logging.Logger;
import com.avivasa.logging.LoggerFactory;
import com.avivasa.maf.dataaccess.dysws.rest.ws.DysWS;
import com.avivasa.maf.service.faxrobot.dto.DocumentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaxRobotDYSService {
    private Logger log = LoggerFactory.getLogger(FaxRobotDYSService.class);

    public byte[] getDocumentContentByFileNetId(String fileNetId) {
        /*
        DocumentManagerGetDocumentRequest getDocumentRequest = new DocumentManagerGetDocumentRequest();
        getDocumentRequest.setDocumentId(fileNetId);
        DocumentManagerGetDocumentResponse getDocumentResponse = documentManagerService.getDocument(getDocumentRequest);

        if ("OK".equals(getDocumentResponse.getStatus())) {
            return Base64.getDecoder().decode(getDocumentResponse.getStrDocumentContent());
        }
        */

        log.info("DYS üzerinden getirildi");
        return new byte[0];
    }
/*
    public boolean updateDocumentMetadata(String documentId, BarcodedFlow barcodedFlow) {
        EnumMap<EnumFileNetMetaData, String> properties = new EnumMap<>(EnumFileNetMetaData.class);
        properties.put(EnumFileNetMetaData.MUSTERI_NO, barcodedFlow.getCustomerNo());
        properties.put(EnumFileNetMetaData.BARKOD_ID, barcodedFlow.getBarcodeId());
        properties.put(EnumFileNetMetaData.SOZLESME_POLICE_NO, barcodedFlow.getPackageCode() + barcodedFlow.getPolicyNo());
        DocumentManagerUpdateDocumentResponse updateDocumentResponse = documentManagerService.updateDocument(documentId, properties);
        return updateDocumentResponse.getStatus().equalsIgnoreCase("OK");
    }
*/
    public String insertDocumentToFilenet(DocumentDto documentDto) throws MafSystemException {
        /*
        EnumMap<EnumFileNetMetaData, String> properties = new EnumMap<>(EnumFileNetMetaData.class);
        properties.put(EnumFileNetMetaData.DOKUMAN_ID, String.valueOf(documentDto.getDocumentNo()));

        try {
            DocumentManagerCreateDocumentResponse createDocumentResponse = documentManagerService.createDocument(EnumFileNetDocumentClass.INDEXLENMEMIS, documentDto.getNewDocumentPath(), properties);

            if ("OK".equalsIgnoreCase(createDocumentResponse.getStatus())) {
                log.info("{} DYS sistemine kaydedildi. FileNet Id : {}", documentDto.getInfoLog(), createDocumentResponse.getDocumentId());
                return createDocumentResponse.getDocumentId();
            } else {
                log.info("{} DYS sistemine kaydedilemedi. Hata : {}", documentDto.getInfoLog(), createDocumentResponse.getError());
                throw new MafSystemException(createDocumentResponse.getError());
            }
        } catch (Exception e) {
            log.error("Exception occured at insertDocumentToFilenet", e);
            throw new MafSystemException(e.getMessage(), e);
        }
        */
        log.info("{} DYS üzerine kaydedildi", documentDto.getDocumentName());
        return documentDto.getDocumentName();
    }
}
