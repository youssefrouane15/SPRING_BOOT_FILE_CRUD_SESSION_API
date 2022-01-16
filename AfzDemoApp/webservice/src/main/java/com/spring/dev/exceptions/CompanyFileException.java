package com.spring.dev.exceptions;

import com.spring.dev.enums.CompanyFileErrorsEnum;

public class CompanyFileException extends Exception {

	private String code;

	public CompanyFileException(String code, String message) {

		super(message);
		this.code = code;
	}
	
	public CompanyFileException(CompanyFileErrorsEnum companyFileErrorsEnum) {
		
		super(companyFileErrorsEnum.message);
		this.code = companyFileErrorsEnum.code;
	}
	
	public CompanyFileException(CompanyFileErrorsEnum companyFileErrorsEnum, Throwable cause) {
		
		super(companyFileErrorsEnum.message, cause);
		this.code = companyFileErrorsEnum.code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
}
