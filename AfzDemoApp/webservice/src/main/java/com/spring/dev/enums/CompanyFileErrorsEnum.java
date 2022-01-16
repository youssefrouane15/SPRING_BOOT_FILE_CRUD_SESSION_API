package com.spring.dev.enums;

public enum CompanyFileErrorsEnum {

	ELEMENT_NOT_FOUND_EXCEPTION("ELEMENT_NOT_FOUND_EXCEPTION", "Element with id {0} is not found in the Json file"),
	JSON_READ_FILE_EXCEPTION("JSON_READ_FILE_EXCEPTION", "Error while reading from the Json file"),
	JSON_SAVE_FILE_EXCEPTION("JSON_SAVE_FILE_EXCEPTION", "Error while saving in the Json file"),
	REQUEST_BODY_FORMAT("REQUEST_BODY_FORMAT", "Request Body Json is not formatted correctly"),
	FAILED_VALIDATION("FAILED_VALIDATION", "validation failed");

	public final String code;
	public final String message;

	private CompanyFileErrorsEnum(String code, String message) {

		this.code = code;
		this.message = message;
	}

}
