package com.avivasa.maf.service.faxrobot.splitters;

import com.avivasa.logging.Logger;
import com.avivasa.logging.LoggerFactory;
import com.avivasa.maf.service.faxrobot.service.FaxRobotDirectories;
import com.avivasa.maf.service.faxrobot.dto.DocumentDto;
import com.avivasa.maf.service.faxrobot.enums.FaxRobotMimeTypeEnum;
import com.avivasa.maf.service.faxrobot.util.FaxRobotUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * @author Taner YILDIRIM
 */
@Service
public class OtherFileSplitter extends FaxRobotDirectories implements FileSplitter {

    private Logger log = LoggerFactory.getLogger(OtherFileSplitter.class);

    @Override
    public DocumentDto splitFileIntoPages(DocumentDto parentDocumentDto) {
        File srcFile = new File(parentDocumentDto.getNewDocumentPath());
        File destFile = new File(getDirConvertToImage() + parentDocumentDto.getDocumentNo() + File.separator + parentDocumentDto.getDocumentName());
        try {
            FaxRobotUtil.copyFile(srcFile, destFile);
        } catch (IOException ioex) {
            log.error("splitFileIntoPages has an error", ioex);
        }
        parentDocumentDto.setPageNumber(1);
        return parentDocumentDto;
    }

    @Override
    public boolean matches(FaxRobotMimeTypeEnum mimeType) {
        return !(FaxRobotUtil.isPDF(mimeType) || FaxRobotUtil.isTIF(mimeType));
    }
}
