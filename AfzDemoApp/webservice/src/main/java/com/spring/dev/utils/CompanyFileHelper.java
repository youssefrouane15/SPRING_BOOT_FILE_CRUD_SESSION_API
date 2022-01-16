package com.spring.dev.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.dev.enums.CompanyFileErrorsEnum;
import com.spring.dev.exceptions.JsonFileException;
import com.spring.dev.model.dto.CompanyDto;

@Component
public class CompanyFileHelper {

	@Value("classpath:items.json")
	Resource resourceFile;


	public List<CompanyDto> getCompanyDtoListFromFile(HttpSession session) throws JsonFileException {

		ObjectMapper objectMapper = new ObjectMapper();
		List<CompanyDto> companyDtoList;
		try {
			companyDtoList = new ArrayList<>(
					Arrays.asList(objectMapper.readValue(session.getAttribute("file").toString(), CompanyDto[].class)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new JsonFileException(CompanyFileErrorsEnum.JSON_READ_FILE_EXCEPTION, e);
		}
		return companyDtoList;
	}

	public void storeFileInSession(HttpSession session, List<CompanyDto> companyDtoList) throws JsonFileException {

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			session.setAttribute("file", objectMapper.writeValueAsString(companyDtoList));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new JsonFileException(CompanyFileErrorsEnum.JSON_READ_FILE_EXCEPTION, e);
		}
	}

	public void appendToFile(List<CompanyDto> companyDtoList) {

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			Path path = Paths.get(resourceFile.getFile().getPath());
			System.out.println(objectMapper.writeValueAsString(companyDtoList));
			System.out.println(
					objectMapper.writeValueAsString(companyDtoList).getBytes(StandardCharsets.ISO_8859_1).toString());
			Files.write(path, objectMapper.writeValueAsString(companyDtoList).getBytes(),
					StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void isFileEmpty(HttpSession session) {
		
		if (session.getAttribute("file") == null) {
			try {
				session.setAttribute("file", fileToString(resourceFile.getFile().getPath()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	public static String fileToString(String path) throws IOException {
		return FileUtils.readFileToString(ResourceUtils.getFile(path), StandardCharsets.UTF_8);
	}

	
	public long generateNextID(List<CompanyDto> companyDtoList) {
		if (companyDtoList.size() == 0) {
			return 0;
		}
		return companyDtoList.stream().mapToLong(c -> c.getId()).max().getAsLong() + 1;
	}

}
