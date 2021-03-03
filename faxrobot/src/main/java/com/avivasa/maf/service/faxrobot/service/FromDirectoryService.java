package com.avivasa.maf.service.faxrobot.service;

import com.avivasa.logging.Logger;
import com.avivasa.logging.LoggerFactory;
import com.avivasa.maf.service.faxrobot.dto.DocumentDto;
import com.avivasa.maf.service.faxrobot.enums.DocumentProcessTypeEnum;
import com.avivasa.maf.service.faxrobot.enums.DocumentStatusEnum;
import com.avivasa.maf.service.faxrobot.util.FaxRobotUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Locale;

@Service
public class FromDirectoryService {

	private Logger log = LoggerFactory.getLogger(FromDirectoryService.class);

	@Autowired
	private FaxRobotDBService dbOperations;

	public DocumentDto processFile(File srcFile, DocumentProcessTypeEnum processType) {
		String fileName = srcFile.getName().toLowerCase(new Locale("en", "US"));

		fileName= FaxRobotUtil.checkFileNameLength(fileName);
		fileName = FaxRobotUtil.replaceTurkishCharacters(fileName);

		DocumentDto documentDto = new DocumentDto();
		documentDto.setDocumentPath(srcFile.getAbsolutePath());
		documentDto.setDocumentName(fileName);
		documentDto.setProcessType(processType.getProcessName());
		documentDto.setDocumentSize(srcFile.length());
		documentDto.setStatus(DocumentStatusEnum.DURUM_INSERTED_TO_TABLE.getStatusCode());
		documentDto.setNewDocumentName(srcFile.getPath());
		documentDto.setNewDocumentPath(srcFile.getAbsolutePath());
		documentDto = dbOperations.insertDocument(documentDto);

		log.info("{} için DocumentDto objesi oluşturuldu : {}", srcFile.getName(), documentDto.toString());
		return documentDto;
	}
}