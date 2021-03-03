package com.avivasa.maf.service.faxrobot.processor;


import com.avivasa.exception.MafSystemException;
import com.avivasa.logging.Logger;
import com.avivasa.logging.LoggerFactory;
import com.avivasa.maf.service.faxrobot.dto.DocumentDto;
import com.avivasa.maf.service.faxrobot.enums.DocumentProcessTypeEnum;
import com.avivasa.maf.service.faxrobot.service.FromDirectoryService;
import com.avivasa.maf.service.faxrobot.util.FaxRobotConstants;
import com.avivasa.maf.service.faxrobot.util.FaxRobotUtil;
import com.avivasa.maf.util.MafUtils;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FromDirectoryProcessor implements Processor {

    private Logger log = LoggerFactory.getLogger(FromDirectoryProcessor.class);

    @Autowired
    private FromDirectoryService fromDirectoryOperations;

    @Override
    public void process(Exchange exchange) throws Exception {

        File srcFile = exchange.getIn().getBody(File.class);
        String endpoint = exchange.getFromEndpoint().getEndpointKey();

        log.info("{} dosyası {} endpointi üzerinden işleniyor", srcFile.getName(), endpoint);

        if(!FaxRobotUtil.isValidDocument(srcFile)) {
            throw new MafSystemException("Hatalı dosya veya desteklenmeyen format : {}", srcFile.getName());
        }

        DocumentDto documentDto = null;
        if(!MafUtils.isEmptyOrNull(endpoint)) {

            if(endpoint.contains(FaxRobotConstants.TRANSFER_PATH)) {
                documentDto = fromDirectoryOperations.processFile(srcFile, DocumentProcessTypeEnum.AKTARIM);
            } else if(endpoint.contains(FaxRobotConstants.FAX_PATH)) {
                documentDto = fromDirectoryOperations.processFile(srcFile, DocumentProcessTypeEnum.FAX);
            }
        }

        exchange.getIn().setBody(documentDto, DocumentDto.class);
    }
}