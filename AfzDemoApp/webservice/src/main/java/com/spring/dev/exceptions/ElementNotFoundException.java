package com.spring.dev.exceptions;

import com.spring.dev.enums.CompanyFileErrorsEnum;

public class ElementNotFoundException extends CompanyFileException {
	
    public  ElementNotFoundException(String code, String message){
        super(code, message);
    }

    public ElementNotFoundException(CompanyFileErrorsEnum companyFileErrorsEnum) {
        super(companyFileErrorsEnum);
    }

    public ElementNotFoundException(CompanyFileErrorsEnum companyFileErrorsEnum, Throwable cause) {
        super(companyFileErrorsEnum, cause);
    }

}
