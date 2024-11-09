package com.adk.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adk.blog.model.Post;
import com.adk.blog.service.PostService;
import com.adk.blog.service.TagService;

@SpringBootApplication
@RestController
public class MainController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private TagService tagService;
	
	@PostMapping("/createPost")
	public ResponseEntity<Object> createPost(@RequestBody Post p) {
        return new ResponseEntity<>(postService.addPost(p), HttpStatus.OK);
	}
	
	@GetMapping("/getAllPosts")
	public ResponseEntity<Object> getAllPosts() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
	}
	
	@GetMapping("/hello")
    public ResponseEntity<String> hello(@RequestParam(value = "name", defaultValue = "World") String name) {
      return new ResponseEntity<>(String.format("Hello %s!", name), HttpStatus.OK);
    }
	
	@GetMapping("/getAllTags")
	public ResponseEntity<Object> getAllTags() {
        return new ResponseEntity<>(tagService.getAllTags(), HttpStatus.OK);
	}

}
