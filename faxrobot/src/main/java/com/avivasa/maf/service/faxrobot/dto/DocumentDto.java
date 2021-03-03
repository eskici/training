package com.avivasa.maf.service.faxrobot.dto;

import com.avivasa.maf.service.faxrobot.enums.FaxRobotMimeTypeEnum;
import com.avivasa.maf.service.faxrobot.util.FaxRobotConstants;
import com.avivasa.maf.service.faxrobot.util.FaxRobotUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@ToString(includeFieldNames = true)
public class DocumentDto {

	StringBuilder infoLog = new StringBuilder();

	public DocumentDto() {
		this.documentDate = FaxRobotUtil.todayTimestamp();
		this.completed = FaxRobotConstants.NOT_COMPLETED;
		this.entityType = FaxRobotConstants.INDEXLENMEMIS;
		this.documentUser = FaxRobotConstants.FAXROBOT_USER;
	}

	private Integer documentNo;

	private String documentName;

	private String documentPath;

	private String documentUser;

	private Long documentSize;

	private Timestamp documentDate;

	private String cmRef;

	private String newDocumentName;

	private String newDocumentPath;

	private String documentChannel;

	private String processType;

	private String entityType;

	private Integer parentDocumentNo;

	private Integer status;

	private Integer flowNo;

	private String packageCode;

	private String policyNo;

	private String customerNo;

	private String lastCmRef;

	private String lastEntityType;

	private Long category;

	private Long tck;

	private String completed;

	private String barcodeId;

	private Integer pageNumber;

	private boolean isChildDocument;

	private FaxRobotMimeTypeEnum mimeType;

	private Map<Integer, String> barcodeMap = new HashMap<>();

	public String getInfoLog() {

		if(infoLog.length() > 0) {
			return infoLog.toString();
		}

		infoLog.append(getDocumentNo());

		if(getParentDocumentNo() != null) {
			infoLog.append(" (");
			infoLog.append(getParentDocumentNo());
			infoLog.append(") ");
		}

		return infoLog.append(" nolu ").append(getDocumentName()).toString();
	}
}