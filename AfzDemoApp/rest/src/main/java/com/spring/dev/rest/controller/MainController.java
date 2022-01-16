package com.spring.dev.rest.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dev.exceptions.ElementNotFoundException;
import com.spring.dev.exceptions.JsonFileException;
import com.spring.dev.model.dto.CompanyDto;
import com.spring.dev.model.dto.request.CompanyRequest;
import com.spring.dev.service.FileService;

@RestController
@RequestMapping("/azf-api/v1/items")
public class MainController {

	@Autowired
	private FileService fileService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CompanyDto>> getItems(HttpSession session) throws JsonFileException {

		List<CompanyDto> foundItems = fileService.findAll(session);

		return ResponseEntity.ok(foundItems);
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CompanyDto> getItem(@PathVariable Long id, HttpSession session)
			throws JsonFileException, ElementNotFoundException {

		CompanyDto foundItem = fileService.findOne(id, session);

		return ResponseEntity.ok(foundItem);
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CompanyDto> create(@RequestBody CompanyRequest companyRequest, HttpSession session)
			throws JsonFileException {

		CompanyDto createdItem = fileService.add(companyRequest, session);

		return ResponseEntity.ok(createdItem);

	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CompanyDto> update(@RequestBody CompanyRequest companyRequest, HttpSession session)
			throws JsonFileException, ElementNotFoundException {

		CompanyDto updatedItem = fileService.update(companyRequest.getId(), companyRequest, session);

		return ResponseEntity.ok(updatedItem);
	}

	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> deletePost(@PathVariable Long id, HttpSession session)
			throws JsonFileException, ElementNotFoundException {

		fileService.delete(id, session);

		return ResponseEntity.ok().build();
	}

}
