package com.spring.dev.exceptions.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.spring.dev.enums.CompanyFileErrorsEnum;
import com.spring.dev.exceptions.ElementNotFoundException;
import com.spring.dev.exceptions.JsonFileException;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@ControllerAdvice
public class RestAPIExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        String errorMessage = "Request Body Json is not formatted correctly";
        log.error(errorMessage, e);
        return buildResponseEntity(new ApiErrorResponse(HttpStatus.BAD_REQUEST, CompanyFileErrorsEnum.REQUEST_BODY_FORMAT.code,
        		CompanyFileErrorsEnum.REQUEST_BODY_FORMAT.message, e));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorMessageList = new ArrayList<>();
        for(ObjectError error : e.getBindingResult().getAllErrors()) {
            errorMessageList.add(error.getDefaultMessage());
        }

        log.error(e.getMessage(), e);
        return buildResponseEntity(new ApiErrorResponse(HttpStatus.BAD_REQUEST, CompanyFileErrorsEnum.FAILED_VALIDATION.code,
                String.join(", ",errorMessageList), e));
    }
    
    private ResponseEntity<Object> buildResponseEntity(ApiErrorResponse apiErrorResponse) {
        return new ResponseEntity<>(apiErrorResponse, apiErrorResponse.getHttpStatus());
    }

    @ExceptionHandler(ElementNotFoundException.class)
    protected ResponseEntity<Object> handleElementNotFoundException(
            ElementNotFoundException e) {
        log.error(e.getMessage(), e);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND,
                e.getCode(),
                e.getMessage());
        return buildResponseEntity(apiErrorResponse);
    }

    @ExceptionHandler(JsonFileException.class)
    protected ResponseEntity<Object> handleJsonFileException(
            JsonFileException e) {
        log.error(e.getMessage(), e);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                e.getCode(),
                e.getMessage(),
                e);
        return buildResponseEntity(apiErrorResponse);
    }
}
