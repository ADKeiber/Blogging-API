package com.adk.blog.service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adk.blog.errorhandling.FieldBlankException;
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
		if(post.getTitle() == null || post.getTitle().isBlank())
			throw new FieldBlankException(Post.class, "Title", String.class.toString());
		if(post.getContents() == null || post.getContents().isBlank())
			throw new FieldBlankException(Post.class, "Contents", String.class.toString());
		if(post.getPublishDate() == null)
			throw new FieldBlankException(Post.class, "Publish Date", LocalDate.class.toString());
		
		List<Tag> tags = new LinkedList<>();
		for(int i = 0; i < post.getTags().size(); i++) {
			
			Tag tag = post.getTags().get(i);
			
			if(tag.getValue() == null || tag.getValue().isBlank())
				throw new FieldBlankException(Tag.class, "Value", String.class.toString());
			
			Tag tagFromRepo = tagRepo.findTagByValue(tag.getValue());
			
			if(tagFromRepo == null) {
				tag = tagRepo.save(tag);
				tags.add(tag);
			} else {
				tags.add(tagFromRepo);
			}
				
		}
		post.setTags(tags);
		System.out.println("TAGS: " + post.getTags().toString());
		return postRepo.save(post);
	}

	@Override
	public List<Post> getAllPosts() {
		List<Post> posts = postRepo.findAll();
		System.out.println("POSTS:" + posts);
		return posts;
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
