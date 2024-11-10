package com.adk.blog.controller;

import java.time.LocalDate;

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

import com.adk.blog.model.Post;
import com.adk.blog.service.PostService;

@SpringBootApplication
@RestController
@RequestMapping("/post")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Blog Post Controller")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@PostMapping("/create")
	public ResponseEntity<Object> create(@RequestBody Post p) {
        return new ResponseEntity<>(postService.addPost(p), HttpStatus.OK);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<Object> getAll() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
	}
	
	@GetMapping("/getByTitle/{title}")
	public ResponseEntity<Object> getByTitle(@PathVariable String title) {
        return new ResponseEntity<>(postService.getPostsByTitle(title), HttpStatus.OK);
	}
	
	@GetMapping("/getByDate/{date}")
	public ResponseEntity<Object> getByDate(@PathVariable LocalDate date) {
        return new ResponseEntity<>(postService.getPostsByDate(date), HttpStatus.OK);
	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<Object> getById(@PathVariable String id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
	}
	
	@PostMapping("/update/{id}")
	public ResponseEntity<Object> updateById(@PathVariable String id, @RequestBody Post p) {
        return new ResponseEntity<>(postService.editPost(id, p), HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable String id) {
		postService.deletePostById(id);
        return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/getByTagValue/{tag}")
	public ResponseEntity<Object> getByTagValue(@PathVariable String tag) {
        return new ResponseEntity<>(postService.getPostsByTagValue(tag), HttpStatus.OK);
	}
}
