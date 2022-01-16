package com.spring.dev.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dev.enums.CompanyFileErrorsEnum;
import com.spring.dev.exceptions.ElementNotFoundException;
import com.spring.dev.exceptions.JsonFileException;
import com.spring.dev.model.dto.CompanyDto;
import com.spring.dev.model.dto.request.CompanyRequest;
import com.spring.dev.utils.CompanyFileHelper;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private CompanyFileHelper companyFileHelper;

	@Override
	public CompanyDto add(CompanyRequest companyRequest, HttpSession session) throws JsonFileException {

		companyFileHelper.isFileEmpty(session);
		CompanyDto companyDto = new CompanyDto();
		List<CompanyDto> companyDtoList = companyFileHelper.getCompanyDtoListFromFile(session);

		companyDto.setId(companyFileHelper.generateNextID(companyDtoList));
		companyDto.setLabel(companyRequest.getLabel());
		companyDto.setDate(companyRequest.getDate());

		companyDtoList.add(companyDto);

		companyFileHelper.storeFileInSession(session, companyDtoList);
		companyFileHelper.appendToFile(companyDtoList);
		return companyDto;
	}

	@Override
	public CompanyDto update(Long id, CompanyRequest companyRequest, HttpSession session)
			throws JsonFileException, ElementNotFoundException {

		companyFileHelper.isFileEmpty(session);
		List<CompanyDto> companyDtoList = companyFileHelper.getCompanyDtoListFromFile(session);

		CompanyDto companyDto = companyDtoList.stream().filter(c -> c.getId().equals(companyRequest.getId())).map(c -> {
			c.setLabel(companyRequest.getLabel());
			c.setDate(companyRequest.getDate());
			return c;
		}).findFirst().orElseThrow(
				() -> new ElementNotFoundException(CompanyFileErrorsEnum.ELEMENT_NOT_FOUND_EXCEPTION.code, MessageFormat
						.format(CompanyFileErrorsEnum.ELEMENT_NOT_FOUND_EXCEPTION.message, companyRequest.getId())));

		companyFileHelper.storeFileInSession(session, companyDtoList);
		companyFileHelper.appendToFile(companyDtoList);

		return companyDto;
	}

	@Override
	public void delete(Long id, HttpSession session) throws JsonFileException, ElementNotFoundException {

		companyFileHelper.isFileEmpty(session);
		List<CompanyDto> companyDtoList = companyFileHelper.getCompanyDtoListFromFile(session);

		Boolean removed = companyDtoList.removeIf(companyDto -> companyDto.getId().equals(id));
		if (!removed) {
			throw new ElementNotFoundException(CompanyFileErrorsEnum.ELEMENT_NOT_FOUND_EXCEPTION.code,
					MessageFormat.format(CompanyFileErrorsEnum.ELEMENT_NOT_FOUND_EXCEPTION.message, id));
		}
		companyFileHelper.storeFileInSession(session, companyDtoList);
	}

	@Override
	public CompanyDto findOne(Long id, HttpSession session) throws JsonFileException, ElementNotFoundException {

		companyFileHelper.isFileEmpty(session);
		List<CompanyDto> companyDtoList = companyFileHelper.getCompanyDtoListFromFile(session);

		return companyDtoList.stream().filter(l -> l.getId().equals(id)).findFirst()
				.orElseThrow(() -> new ElementNotFoundException(CompanyFileErrorsEnum.ELEMENT_NOT_FOUND_EXCEPTION.code,
						MessageFormat.format(CompanyFileErrorsEnum.ELEMENT_NOT_FOUND_EXCEPTION.message, id)));
	}

	@Override
	public List<CompanyDto> findAll(HttpSession session) throws JsonFileException {

		companyFileHelper.isFileEmpty(session);

		return companyFileHelper.getCompanyDtoListFromFile(session);

	}

	public List<CompanyDto> getCompanyDtoListFromFile(HttpSession session) throws JsonFileException {

		return companyFileHelper.getCompanyDtoListFromFile(session);
	}

}
