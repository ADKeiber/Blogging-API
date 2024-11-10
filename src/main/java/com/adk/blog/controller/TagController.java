package com.adk.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adk.blog.model.Post;
import com.adk.blog.service.PostService;
import com.adk.blog.service.TagService;

@SpringBootApplication
@RestController
@RequestMapping("/tag")
public class TagController {
	
	@Autowired
	private TagService tagService;
	
	@GetMapping("/hello")
    public ResponseEntity<String> hello(@RequestParam(value = "name", defaultValue = "World") String name) {
      return new ResponseEntity<>(String.format("Hello %s!", name), HttpStatus.OK);
    }
	
	@GetMapping("/getAll")
	public ResponseEntity<Object> getAllTags() {
        return new ResponseEntity<>(tagService.getAllTags(), HttpStatus.OK);
	}
}
