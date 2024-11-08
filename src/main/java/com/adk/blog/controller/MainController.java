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

@SpringBootApplication
@RestController
public class MainController {
	
	@Autowired
	private PostService postService;
	
	@PostMapping("/createPost")
	public ResponseEntity<Object> createPost(@RequestBody Post p) {
		Post returnedPost = postService.addPost(p);
		if(returnedPost.getTitle() == null || returnedPost.getTitle().isBlank()) {
			return new ResponseEntity<>("Post was missing title... Did not save into database.", HttpStatus.OK);
		}
		if(returnedPost.getContents() == null || returnedPost.getContents().isBlank()) {
			return new ResponseEntity<>("Post was missing contents... Did not save into database.", HttpStatus.OK);
		}
		if(returnedPost.getPublishDate() == null) {
			return new ResponseEntity<>("Post was missing publish date... Did not save into database.", HttpStatus.OK);
		}
        return new ResponseEntity<>(postService.addPost(p), HttpStatus.OK);
	}
	
	
	@GetMapping("/hello")
    public ResponseEntity<String> hello(@RequestParam(value = "name", defaultValue = "World") String name) {
      return new ResponseEntity<>(String.format("Hello %s!", name), HttpStatus.OK);
    }

}
