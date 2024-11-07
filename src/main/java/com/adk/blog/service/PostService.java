package com.adk.blog.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.adk.blog.model.Post;

@Service
public class PostService implements IPostService{

	@Override
	public Post addPost(Post post) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> getAllPosts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> getPostByTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> getPostByAuthor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> getPostByDate(LocalDate localDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post getPostById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post editPost(String id, Post post) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post deletePostById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}
