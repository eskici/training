package com.moss.project.eneasy.web.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter

public class ValidationInfo {

	private String type;
	private String message;

	@Override
	public String toString() {
		return "ValidationInfo{" +
				"type='" + type + '\'' +
				", message='" + message + '\'' +
				'}';
	}
}
