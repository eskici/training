package com.avivasa.maf.service.faxrobot.service;

import com.avivasa.logging.Logger;
import com.avivasa.logging.LoggerFactory;
import com.avivasa.maf.service.faxrobot.dto.DocumentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Taner YILDIRIM
 */
@Service
public class WithBarcodeDocumentProcessService extends DocumentProcessService {
    private Logger log = LoggerFactory.getLogger(WithBarcodeDocumentProcessService.class);

    @Autowired
    private FaxRobotDYSService dysOperations;

    @Override
    public void processDocument(DocumentDto parentDocumentDto) {
        log.info("{} için barkodlu süreç başlatılacak", parentDocumentDto.getInfoLog());
    }

    @Override
    public boolean hasBarcode(DocumentDto documentDto) {
        return documentDto.getBarcodeMap().size() > 0;
    }
}
