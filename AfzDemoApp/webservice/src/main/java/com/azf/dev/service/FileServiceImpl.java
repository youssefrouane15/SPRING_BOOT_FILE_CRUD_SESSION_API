package com.azf.dev.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.azf.dev.model.dto.CompanyDto;
import com.azf.dev.model.dto.request.CompanyRequest;
import com.azf.dev.utils.CompanyFileHelper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class FileServiceImpl implements FileService {

	@Autowired
	private CompanyFileHelper companyFileHelper;
	
	@Override
	public CompanyDto add(CompanyRequest companyRequest, HttpSession session) throws JsonProcessingException {

		companyFileHelper.isFileEmpty(session);
		CompanyDto companyDto = new CompanyDto();
		List<CompanyDto> companyDtoList = companyFileHelper.getCompanyDtoList(session);

		companyDto.setId(companyFileHelper.generateNextID(companyDtoList));
		companyDto.setLabel(companyRequest.getLabel());
		companyDto.setDate(companyRequest.getDate());

		companyDtoList.add(companyDto);

		companyFileHelper.storeFileInSession(session, companyDtoList);
		return companyDto;
	}

	@Override
	public CompanyDto update(Long id, CompanyRequest companyRequest, HttpSession session)
			throws JsonProcessingException {

		companyFileHelper.isFileEmpty(session);

		List<CompanyDto> companyDtoList = companyFileHelper.getCompanyDtoList(session);
		Optional<CompanyDto> companyDto = companyDtoList.stream().filter(l -> l.getId().equals(id)).findFirst();

		if (companyDto.isPresent()) {
			companyDto.get().setLabel(companyRequest.getLabel());
			companyDto.get().setDate(companyRequest.getDate());
		}

		companyFileHelper.storeFileInSession(session, companyDtoList);

		return companyDto.get();
	}

	@Override
	public boolean delete(Long id, HttpSession session) throws IOException {

		companyFileHelper.isFileEmpty(session);
		List<CompanyDto> companyDtoList = companyFileHelper.getCompanyDtoList(session);

		if (companyDtoList.removeIf(l -> l.getId().equals(id))) {
			companyFileHelper.storeFileInSession(session, companyDtoList);
			return true;
		} else {
			companyFileHelper.storeFileInSession(session, companyDtoList);
			return false;
		}
	}

	@Override
	public CompanyDto findOne(Long id, HttpSession session) throws IOException {

		companyFileHelper.isFileEmpty(session);
		List<CompanyDto> companyDtoList = companyFileHelper.getCompanyDtoList(session);

		return companyDtoList.stream().filter(l -> l.getId().equals(id)).findFirst().orElse(null);
	}

	@Override
	public List<CompanyDto> findAll(HttpSession session) throws IOException {

		companyFileHelper.isFileEmpty(session);
		
		return companyFileHelper.getCompanyDtoList(session);

	}

}
