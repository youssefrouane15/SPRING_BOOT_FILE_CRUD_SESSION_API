package com.azf.dev.rest.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.azf.dev.model.Item;
import com.azf.dev.service.DataService;  

@RestController
public class MainController {
	
	@Autowired
	private DataService dataService;
	
	@GetMapping("/items")
	public ResponseEntity<List<Item>> getItems() {
        return ok().body(dataService.findAll());
	}
	
	@GetMapping("/items/{id}")
	public ResponseEntity<Item> getItem(@PathVariable Long id) {
        return ok().body(dataService.findOne(id));
	}
	
	@PostMapping("/items")
	public ResponseEntity<Long> addItem(@RequestBody Item item) throws IOException {
		
		dataService.add(item);
		return new ResponseEntity<>(item.getId(), HttpStatus.OK);
	}
	
	@PutMapping("/items")
	public ResponseEntity<Long> updateItem(@RequestBody Item item) throws IOException {
		
		dataService.add(item);
		return new ResponseEntity<>(item.getId(), HttpStatus.OK);
	}
	
    @DeleteMapping(value = "/items/{id}")
    public ResponseEntity<Long> deletePost(@PathVariable Long id) throws IOException{

        boolean isRemoved = dataService.delete(id);

        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }	

}
