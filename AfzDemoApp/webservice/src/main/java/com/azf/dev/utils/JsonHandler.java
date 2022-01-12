package com.azf.dev.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import com.azf.dev.model.Item;

@Component
public class JsonHandler {
	private static String separator = System.getProperty("file.separator");
	private final static String FILENAME = "src" + separator + "main" + separator + "resources" + separator
			+ "items.json";

	@SuppressWarnings("unchecked")
	public JSONObject createJSONObject(Item item) {

		JSONObject itemDetails = new JSONObject();
		itemDetails.put("id", item.getId());
		itemDetails.put("label", item.getLabel());
		itemDetails.put("date", item.getDate());

		return itemDetails;
	}

	public Item createItem(JSONObject jsonObject) {

		Item item = new Item();
		item.setId((Long) jsonObject.get("id"));
		item.setLabel((String) jsonObject.get("label"));
		item.setDate((String) jsonObject.get("date"));

		return item;
	}

	public List<Item> createItems(JSONArray jsonArray) {

		List<Item> items = new ArrayList<>();
		for (Object obj : jsonArray) {
			JSONObject jsonObject = (JSONObject) obj;
			items.add(createItem(jsonObject));
		}

		return items;
	}

	@SuppressWarnings("unchecked")
	public void writeItem(Item item) throws IOException {

		// convert the model object to Json Object
		JSONArray array = readItems();
		JSONObject jsonObject = createJSONObject(item);
		JSONObject foundItem = findItem(item.getId());
		// if an item is found ---> proceed to update the object ----> 
		// we replace the existing entry with the new one
		
		if (foundItem != null) {
			int index = array.indexOf(foundItem);
			array.remove(foundItem);
			array.add(index, jsonObject);

		} else {
			// if no entry was found, we add a new one
			array.add(jsonObject);
		}

		// Write in the JSON file
		appendToFile(array.toJSONString());
	}

	public boolean removeItem(Long id) throws IOException {

		JSONArray array = readItems();
		JSONObject foundItem = findItem(id);
		if (!foundItem.isEmpty()) {
			array.remove(foundItem);

			// Write in the JSON file
			appendToFile(array.toJSONString());

			return true;
		} else {
			return false;
		}

	}

	public JSONObject findItem(Long id) {

		JSONArray items = readItems();
		JSONObject foundItem = null;

		for (Object item : items) {
			JSONObject jsonObject = (JSONObject) item;
			if (jsonObject.containsValue(id)) {
				foundItem = jsonObject;
			}

		}
		return foundItem;
	}

	public JSONArray readItems() {

		// JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();

		JSONArray items = null;

		try (FileReader reader = new FileReader(FILENAME)) {
			// Read JSON file
			Object obj = jsonParser.parse(reader);

			items = (JSONArray) obj;
			System.out.println(items);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			items = new JSONArray();
			e.printStackTrace();
		}
		return items;
	}

	private static void appendToFile(String content) throws IOException {

		Path path = Paths.get(FILENAME);
		System.out.println(content);
		System.out.println(content.getBytes(StandardCharsets.ISO_8859_1).toString());
		try {
			Files.write(path, content.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException ex) {
			ex.printStackTrace();

		}

	}
}
