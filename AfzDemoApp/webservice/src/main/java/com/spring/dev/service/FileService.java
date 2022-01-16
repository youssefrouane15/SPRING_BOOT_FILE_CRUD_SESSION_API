package com.spring.dev.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.spring.dev.exceptions.ElementNotFoundException;
import com.spring.dev.exceptions.JsonFileException;
import com.spring.dev.model.dto.CompanyDto;
import com.spring.dev.model.dto.request.CompanyRequest;


public interface FileService {
	
	public CompanyDto add(CompanyRequest companyRequest, HttpSession session) throws JsonFileException;

	public CompanyDto update(Long id, CompanyRequest companyRequest, HttpSession session) throws JsonFileException, ElementNotFoundException;

	public void delete(Long id, HttpSession session) throws JsonFileException, ElementNotFoundException;

	public CompanyDto findOne(Long id, HttpSession session) throws JsonFileException, ElementNotFoundException;

	public List<CompanyDto> findAll(HttpSession session) throws JsonFileException;

}
