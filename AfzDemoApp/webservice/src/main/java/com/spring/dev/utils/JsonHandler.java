package com.spring.dev.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import com.spring.dev.model.dto.CompanyDto;

@Component
public class JsonHandler {
	private static String separator = System.getProperty("file.separator");
	private final static String FILENAME = "src" + separator + "main" + separator + "resources" + separator
			+ "items.json";

	@SuppressWarnings("unchecked")
	public JSONObject createJSONObject(CompanyDto companyDto) {

		JSONObject itemDetails = new JSONObject();
		itemDetails.put("id", companyDto.getId());
		itemDetails.put("label", companyDto.getLabel());
		itemDetails.put("date", companyDto.getDate());

		return itemDetails;
	}

	public CompanyDto createItem(JSONObject jsonObject) {

		CompanyDto companyDto = new CompanyDto();
		companyDto.setId((Long) jsonObject.get("id"));
		companyDto.setLabel((String) jsonObject.get("label"));
		companyDto.setDate((Date) jsonObject.get("date"));

		return companyDto;
	}

	public List<CompanyDto> createItems(JSONArray jsonArray) {

		List<CompanyDto> companyDtos = new ArrayList<>();
		for (Object obj : jsonArray) {
			JSONObject jsonObject = (JSONObject) obj;
			companyDtos.add(createItem(jsonObject));
		}

		return companyDtos;
	}

	@SuppressWarnings("unchecked")
	public void writeItem(CompanyDto companyDto) throws IOException {

		// convert the model object to Json Object
		JSONArray array = readItems();
		JSONObject jsonObject = createJSONObject(companyDto);
		JSONObject foundItem = findItem(companyDto.getId());
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
