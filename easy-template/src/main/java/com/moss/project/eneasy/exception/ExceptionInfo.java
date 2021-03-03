package com.moss.project.eneasy.exception;

public class ExceptionInfo {
	private String errorCode;
	private String message;
	private String messageType;
	private String detailedMessage;
	private String errorPage;
	private String type;
	private String sourceSystem;

	public ExceptionInfo() {
	}

	public ExceptionInfo(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetailedMessage() {
		return detailedMessage;
	}

	public void setDetailedMessage(String detailedMessage) {
		this.detailedMessage = detailedMessage;
	}

	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	@Override
	public String toString() {
		return "ExceptionInfo{" +
				"errorCode='" + errorCode + '\'' +
				", message='" + message + '\'' +
				", messageType='" + messageType + '\'' +
				", detailedMessage='" + detailedMessage + '\'' +
				", errorPage='" + errorPage + '\'' +
				", type='" + type + '\'' +
				", sourceSystem='" + sourceSystem + '\'' +
				'}';
	}
}
