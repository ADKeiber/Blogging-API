package com.adk.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adk.blog.model.Tag;
import com.adk.blog.service.TagService;

import io.swagger.v3.oas.annotations.Operation;

@SpringBootApplication
@RestController
@RequestMapping("/tag")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Blog Tag Controller")
public class TagController {
	
	@Autowired
	private TagService tagService;
	
	@Operation(
			description = "Endpoint to create a new Tag",
			summary = "Endpoint that takes in and saves a tag object.\r\n"
					+ "If a tag already exists with the same value or the tag value is empty/null the tag will not be saved."
			
	)
	@PostMapping("/create")
	public ResponseEntity<Object> create(@RequestBody Tag t) {
        return new ResponseEntity<>(tagService.addTag(t), HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Object> getAllTags() {
        return new ResponseEntity<>(tagService.getAllTags(), HttpStatus.OK);
	}
	
	@PostMapping("/update/{id}")
	public ResponseEntity<Object> updateById(@PathVariable String id, @RequestBody Tag t) {
        return new ResponseEntity<>(tagService.editTag(id, t), HttpStatus.OK);
	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<Object> getById(@PathVariable String id) {
        return new ResponseEntity<>(tagService.getTagById(id), HttpStatus.OK);
	}
	
	@GetMapping("/getByValue/{value}")
	public ResponseEntity<Object> getByValue(@PathVariable String value) {
        return new ResponseEntity<>(tagService.getTagByValue(value), HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable String id) {
		tagService.deleteTagById(id);
        return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteByValue/{value}")
	public ResponseEntity<Object> deleteByValue(@PathVariable String value) {
		tagService.deleteTagByValue(value);
        return new ResponseEntity<>(HttpStatus.OK);
	}
}
