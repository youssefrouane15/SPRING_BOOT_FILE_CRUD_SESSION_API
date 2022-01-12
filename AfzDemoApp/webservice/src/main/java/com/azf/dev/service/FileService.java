package com.azf.dev.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.azf.dev.model.Item;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface FileService {
	
	public Item add(Item item, HttpSession session) throws JsonProcessingException;

	public Item update(Long id, Item item, HttpSession session) throws JsonProcessingException;

	public boolean delete(Long id, HttpSession session) throws IOException;

	public Item findOne(Long id, HttpSession session) throws IOException;

	public List<Item> findAll(HttpSession session) throws IOException;

}
