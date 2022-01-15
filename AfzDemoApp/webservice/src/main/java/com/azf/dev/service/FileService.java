package com.azf.dev.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.azf.dev.model.dto.CompanyDto;
import com.azf.dev.model.dto.request.CompanyRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public interface FileService {
	
	public CompanyDto add(CompanyRequest companyRequest, HttpSession session) throws JsonProcessingException;

	public CompanyDto update(Long id, CompanyRequest companyRequest, HttpSession session) throws JsonProcessingException;

	public boolean delete(Long id, HttpSession session) throws IOException;

	public CompanyDto findOne(Long id, HttpSession session) throws IOException;

	public List<CompanyDto> findAll(HttpSession session) throws IOException;

}
