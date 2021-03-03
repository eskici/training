package com.avivasa.maf.service.faxrobot.processor;


import com.avivasa.logging.Logger;
import com.avivasa.logging.LoggerFactory;
import com.avivasa.maf.service.faxrobot.dto.DocumentDto;
import com.avivasa.maf.service.faxrobot.enums.DocumentStatusEnum;
import com.avivasa.maf.service.faxrobot.service.DocumentProcessService;
import com.avivasa.maf.service.faxrobot.service.FaxRobotBarcodeService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FaxRobotDocumentProcessor implements Processor {

    private Logger log = LoggerFactory.getLogger(FaxRobotDocumentProcessor.class);

    @Autowired
    private Map<String, DocumentProcessService> documentProcessServiceMap;

    @Autowired
    FaxRobotBarcodeService barcodeOperations;

    @Override
    public void process(Exchange exchange) throws Exception {

        DocumentDto documentDto = exchange.getIn().getBody(DocumentDto.class);

        if (null != documentDto
                && DocumentStatusEnum.DURUM_INSERTED_TO_TABLE.getStatusCode().equals(documentDto.getStatus())) {

            log.info("{} dosyası için processDocument süreci başlatıldı", documentDto.getInfoLog());

            documentDto = barcodeOperations.readBarcodesOnDocument(documentDto);

            for (DocumentProcessService documentProcessService : documentProcessServiceMap.values()) {
                if (documentProcessService.hasBarcode(documentDto)) {
                    documentProcessService.processDocument(documentDto);
                }
            }

            barcodeOperations.deleteImageDirectoryByDocumentNo(documentDto.getDocumentNo());
        } else {
            log.info("Dosya hatalı olduğu için işlenemedi");
        }
    }
}