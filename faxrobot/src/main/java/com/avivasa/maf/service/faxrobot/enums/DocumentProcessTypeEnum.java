package com.avivasa.maf.service.faxrobot.enums;

import java.util.Arrays;
import java.util.List;

public enum DocumentProcessTypeEnum {

	INDEXLENMEMIS(5, "INDEXLENMEMIS"), FAX(8, "FAX"), SCAN_EMAIL(10, "SCAN_EMAIL"), HATALI(11, "HATALI"), 	EMAIL(9, "EMAIL"), 	
	SCAN_MUHABERAT(12, "SCAN_MUHABERAT"), MASAK(14, "MASAK"), AKTARIM(17, "AKTARIM"), BESTALEP(1, "BESTalep"), HAYAT_TALEP(2, "HayatTalep"), EVRAK(16, "EVRAK");

	private Integer processId;
	private String processName;
	
	DocumentProcessTypeEnum(Integer processId, String processName) {
		this.processId = processId;
		this.processName = processName;
	}

	public String getProcessName() {
		return processName;
	}

	public Integer getProcessId() {
		return processId;
	}
	
	public static DocumentProcessTypeEnum getDocumentProcessEnumByName(String processName) {

		List<DocumentProcessTypeEnum> list = Arrays.asList(DocumentProcessTypeEnum.values());

		for (DocumentProcessTypeEnum enumDocumentProcess : list) {
			if (enumDocumentProcess.getProcessName().equals(processName)) {
				return enumDocumentProcess;
			}
		}
		return null;
	}
}
