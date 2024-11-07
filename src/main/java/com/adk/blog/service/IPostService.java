package com.adk.blog.service;

import java.time.LocalDate;
import java.util.List;

import com.adk.blog.model.Post;

public interface IPostService {
	
	//Create
	public Post addPost(Post post);

	//Read
	public List<Post> getAllPosts();
	public List<Post> getPostByTitle();
	public List<Post> getPostByAuthor();
	public List<Post> getPostByDate(LocalDate localDate);
	public Post getPostById(String id);
	
	//Update
	public Post editPost(String id, Post post);
	
	//Delete
	public Post deletePostById(String id);
	
}
