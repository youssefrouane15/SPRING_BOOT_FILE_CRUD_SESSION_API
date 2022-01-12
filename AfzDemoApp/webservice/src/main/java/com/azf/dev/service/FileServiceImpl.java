package com.azf.dev.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.azf.dev.model.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FileServiceImpl implements FileService {

	@Value("classpath:items.json")
	Resource resourceFile;

	public Item add(Item item, HttpSession session) throws JsonProcessingException {
		isFileEmpty(session);
		ObjectMapper objectMapper = new ObjectMapper();
		List<Item> itemList = new ArrayList<>(
				Arrays.asList(objectMapper.readValue(session.getAttribute("file").toString(), Item[].class)));
		if (item.getId() == null) {
			itemList.sort(Comparator.comparing(Item::getId));
			Long lastId = itemList.get(itemList.size() - 1).getId();
			lastId++;
			item.setId(lastId);
		}
		itemList.add(item);
		session.setAttribute("file", objectMapper.writeValueAsString(itemList));
		return item;
	}

	@Override
	public Item findOne(Long id, HttpSession session) throws IOException {
		isFileEmpty(session);
		ObjectMapper objectMapper = new ObjectMapper();
		List<Item> itemList = Arrays
				.asList(objectMapper.readValue(session.getAttribute("file").toString(), Item[].class));
		return itemList.stream().filter(l -> l.getId().equals(id)).findFirst().orElse(null);
	}

	public List<Item> findAll(HttpSession session) throws IOException {
		isFileEmpty(session);
		ObjectMapper objectMapper = new ObjectMapper();
		return Arrays.asList(objectMapper.readValue(session.getAttribute("file").toString(), Item[].class));
	}

	public Item update(Long id, Item item, HttpSession session) throws JsonProcessingException {
		isFileEmpty(session);
		ObjectMapper objectMapper = new ObjectMapper();
		List<Item> itemList = Arrays
				.asList(objectMapper.readValue(session.getAttribute("file").toString(), Item[].class));
		itemList.stream().filter(l -> l.getId().equals(id)).findFirst().ifPresent(l -> l.setLabel(item.getLabel()));
		itemList.stream().filter(l -> l.getId().equals(id)).findFirst().ifPresent(l -> l.setDate(item.getDate()));
		session.setAttribute("file", objectMapper.writeValueAsString(itemList));
		return itemList.stream().filter(l -> l.getId().equals(id)).findFirst().orElse(null);
	}

	@Override
	public boolean delete(Long id, HttpSession session) throws JsonProcessingException {
		isFileEmpty(session);
		ObjectMapper objectMapper = new ObjectMapper();
		List<Item> itemList = new ArrayList<>(
				Arrays.asList(objectMapper.readValue(session.getAttribute("file").toString(), Item[].class)));
		if (itemList.removeIf(l -> l.getId().equals(id))) {
			session.setAttribute("file", objectMapper.writeValueAsString(itemList));
			return true;
		}
		else {
			session.setAttribute("file", objectMapper.writeValueAsString(itemList));
			return false;
		}
	}

	private void isFileEmpty(HttpSession session) {
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

}
