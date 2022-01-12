package com.azf.dev.rest.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.azf.dev.model.Item;
import com.azf.dev.service.FileService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("/azf-api/v1/items")
public class MainController {

	@Autowired
	private FileService fileService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Item>> getItems(HttpSession session) throws IOException {
        List<Item> foundItem = fileService.findAll(session);
        if (foundItem == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(foundItem);
        }
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Item> getItem(@PathVariable Long id, HttpSession session) throws IOException {
        Item foundItem = fileService.findOne(id, session);
        if (foundItem == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(foundItem);
        }	}

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> create(@RequestBody Item item, HttpSession session)
            throws  JsonProcessingException {
    	Item createdItem = fileService.add(item,session);
        if (createdItem == null) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
        }
    }

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Item> updateItem(@RequestBody Item item, HttpSession session) throws JsonProcessingException  {

        Item updatedItem = fileService.update(item.getId(), item,session);
        if (updatedItem == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updatedItem);
        }
	}

	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> deletePost(@PathVariable Long id, HttpSession session) throws IOException {

		boolean isRemoved = fileService.delete(id, session);

		if (!isRemoved) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(id, HttpStatus.OK);
	}

}
