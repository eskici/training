package com.avivasa.maf.service.faxrobot.service;

import com.avivasa.logging.Logger;
import com.avivasa.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FaxRobotDirectories {

    private Logger log = LoggerFactory.getLogger(FromDirectoryService.class);

    @Value("${faxrobot.directory.dirConvertToImage}")
    private String dirConvertToImage;

    @Value("${faxrobot.directory.dirSplitToPages}")
    private String dirSplitToPages;

    public String getDirConvertToImage() {
        return dirConvertToImage;
    }

    public String getDirSplitToPages() {
        return dirSplitToPages;
    }

}