package com.avivasa.maf.service.faxrobot.splitters;

import com.avivasa.exception.MafSystemException;
import com.avivasa.maf.service.faxrobot.dto.DocumentDto;
import com.avivasa.maf.service.faxrobot.enums.FaxRobotMimeTypeEnum;

/**
 * @author Taner YILDIRIM
 */
public interface FileSplitter {

    DocumentDto splitFileIntoPages(DocumentDto documentDto) throws Exception;

    boolean matches(FaxRobotMimeTypeEnum mimeType);
}
