package com.moss.project.eneasy.web.response;


import com.moss.project.eneasy.exception.ExceptionInfo;

import java.util.List;

public class BaseResponse<T> {

	private T data;
	private boolean success = true;
	private ExceptionInfo error;
	private List<ValidationInfo> validations;
	
	public BaseResponse(T data){
		this.data = data;
	}

	public BaseResponse(T data, boolean success){
		this.data = data;
		this.success = success;
	}
	
	public BaseResponse(){
	}

	public BaseResponse(boolean success, ExceptionInfo error) {
		super();
		this.success = success;
		this.error = error;
	}
	
	public BaseResponse(boolean success, ExceptionInfo error, T data) {
		super();
		this.data = data;
		this.success = success;
		this.error = error;
	}
	
	public ExceptionInfo getError() {
		return error;
	}

	public void setError(ExceptionInfo error) {
		this.error = error;
	}

	public List<ValidationInfo> getValidations() {
		return validations;
	}

	public void setValidations(List<ValidationInfo> validations) {
		this.validations = validations;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "BaseResponse{" +
				"data=" + data +
				", success=" + success +
				", error=" + error +
				", validations=" + validations +
				'}';
	}
}
