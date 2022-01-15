package com.azf.dev.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import com.azf.dev.model.dto.CompanyDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CompanyFileHelper {

	@Value("classpath:items.json")
	Resource resourceFile;

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

	public List<CompanyDto> getCompanyDtoList(HttpSession session) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		List<CompanyDto> companyDtoList = new ArrayList<>(
				Arrays.asList(objectMapper.readValue(session.getAttribute("file").toString(), CompanyDto[].class)));

		return companyDtoList;

	}

	public void storeFileInSession(HttpSession session, List<CompanyDto> companyDtoList)
			throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		session.setAttribute("file", objectMapper.writeValueAsString(companyDtoList));
	}

}
