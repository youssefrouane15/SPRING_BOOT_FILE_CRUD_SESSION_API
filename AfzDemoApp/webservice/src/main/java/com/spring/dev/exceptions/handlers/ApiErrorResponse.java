package com.spring.dev.exceptions.handlers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ApiErrorResponse {

	private HttpStatus httpStatus;
	private LocalDateTime timestamp;
	private String code;
	private String message;
	private String detailedMessage;
	
    private ApiErrorResponse() {
        timestamp = LocalDateTime.now();
    }

    public ApiErrorResponse(HttpStatus status,String code, String message) {
        this();
        this.httpStatus = status;
        this.code = code;
        this.message = message;
    }
    public ApiErrorResponse(HttpStatus status, String code, String message, Throwable ex) {
        this();
        this.code = code;
        this.httpStatus = status;
        this.message = message;
        this.detailedMessage = ex.getLocalizedMessage();
    }

    public HttpStatus getHttpStatus(){
        return httpStatus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDetailedMessage() {
        return detailedMessage;
    }

}
