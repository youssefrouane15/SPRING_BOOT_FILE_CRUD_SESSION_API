package com.azf.dev.service;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azf.dev.model.Item;
import com.azf.dev.utils.JsonHandler;

@Service
public class DataService {

	@Autowired
	private JsonHandler jsonHandler;

	public void add(Item item) throws IOException {
		jsonHandler.writeItem(item);

	}

	public boolean delete(Long id) throws IOException {
		return jsonHandler.removeItem(id);

	}

	public Item findOne(Object keyWord) {

		JSONObject foundItem = jsonHandler.findItem(keyWord);
		return jsonHandler.createItem(foundItem);
	}

	public List<Item> findAll() {
		return jsonHandler.createItems(jsonHandler.readItems());

	}

}
