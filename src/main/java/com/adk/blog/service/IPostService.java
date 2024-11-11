package com.adk.blog.service;

import java.time.LocalDate;
import java.util.List;

import com.adk.blog.model.Post;

/**
 * Interface used to establish methods used to interact with post database
 */
public interface IPostService {
	
	//Create
	public Post addPost(Post post);

	//Read
	public List<Post> getAllPosts();
	public List<Post> getPostsByTitle(String title);
	public List<Post> getPostsByDate(LocalDate localDate);
	public List<Post> getPostsByTagValue(String tagValue);
	public Post getPostById(String id);
	
	//Update
	public Post editPost(String id, Post post);
	
	//Delete
	public void deletePostById(String id);
	
}
