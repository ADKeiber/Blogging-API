package com.adk.blog.service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adk.blog.errorhandling.EntityNotFoundException;
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
	
	@Autowired
	private TagService tagService;
	
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
			
			Tag tagFromRepo = tagService.getTagByValue(tag.getValue());
			
			if(tagFromRepo == null) {
				tag = tagRepo.save(tag);
				tags.add(tag);
			} else {
				tags.add(tagFromRepo);
			}
				
		}
		post.setTags(tags);
		return postRepo.save(post);
	}

	@Override
	public List<Post> getAllPosts() {
		return postRepo.findAll();
	}

	@Override
	public List<Post> getPostsByTitle(String title) {
		List<Post> posts = postRepo.getPostsByTitle(title);
		if(posts == null || posts.size() == 0) {
			throw new EntityNotFoundException(Post.class, "title", title);
		}
		return posts;
	}

	@Override
	public List<Post> getPostsByDate(LocalDate localDate) {
		List<Post> posts = postRepo.getPostsByDate(localDate);
		if(posts == null || posts.size() == 0) 
			throw new EntityNotFoundException(Post.class, "publish date", localDate.toString());
		return posts;
	}

	@Override
	public Post getPostById(String id) {
		Optional<Post> post = postRepo.findById(id);
		if(post.isEmpty())
			throw new EntityNotFoundException(Post.class, "id", id);
		return post.get();
	}

	@Override
	public Post editPost(String id, Post post) {
		post.setId(id);
		Post returnedPost = postRepo.save(post);
		//TODO Maybe create an exception for returned data != original data with new id
		return returnedPost;
	}

	@Override
	public void deletePostById(String id) {
		postRepo.deleteById(id);
		//TODO... I feel like something should be checked here
	}

	@Override
	public List<Post> getPostsByTagValue(String tagValue) {
		Tag tag = tagService.getTagByValue(tagValue);
		//TODO DO a check for getting this tag
		List<Post> posts = postRepo.getPostByTag(tag);
		if(posts == null || posts.size() == 0) 
			throw new EntityNotFoundException(Post.class, "tag value", tagValue);
		return posts;
	}
}
