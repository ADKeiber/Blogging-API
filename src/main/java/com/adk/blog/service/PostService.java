package com.adk.blog.service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adk.blog.model.Post;
import com.adk.blog.model.Tag;
import com.adk.blog.repo.PostRepo;
import com.adk.blog.repo.TagRepo;

@Service
public class PostService implements IPostService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private TagRepo tagRepo;
	
	@Override
	public Post addPost(Post post) {
		//Checks certain fields that are required
		if(post.getTitle().isBlank() || post.getTitle() == null)
			return post;
		if(post.getContents().isBlank() || post.getContents() == null)
			return post;
		if(post.getPublishDate() == null)
			return post;
		
		List<Tag> tags = new LinkedList<>();
		for(int i = 0; i < post.getTags().size(); i++) {
			Tag tag = post.getTags().get(i);
			Tag tagFromRepo = tagRepo.findTagByValue(tag.getValue());
			if(tagFromRepo == null) {
				tag = tagRepo.save(tag);
				tags.add(tag);
			} else {
				tags.add(tagFromRepo);
			}
				
		}
		post.setTags(tags);
		Post p = postRepo.save(post);
		
		return p;
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

	@Override
	public List<Post> getPostByTag(Tag tag) {
		// TODO Auto-generated method stub
		return null;
	}
}
