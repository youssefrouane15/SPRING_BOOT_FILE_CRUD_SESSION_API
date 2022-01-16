package com.spring.dev.exceptions;

import com.spring.dev.enums.CompanyFileErrorsEnum;

public class JsonFileException extends CompanyFileException {

	public JsonFileException(CompanyFileErrorsEnum companyFileErrorsEnum) {
		super(companyFileErrorsEnum);
	}
	
	public JsonFileException(CompanyFileErrorsEnum companyFileErrorsEnum, Throwable cause) {
		super(companyFileErrorsEnum, cause);
	}

}
