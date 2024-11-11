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

/**
 * Service class used to interact with the post repository 
 */
@Service
public class PostService implements IPostService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private TagService tagService;
	
	/**
	 * Implements add post method.
	 * Adds a post to the repository and verifies all required fields are present.
	 * Also creates any new tags that don't already exists in the tag table.
	 */
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
			tags.add(tagService.addTag(post.getTags().get(i)));
		}
		post.setTags(tags);
		return postRepo.save(post);
	}

	/**
	 * Returns all posts in the repository's post table
	 */
	@Override
	public List<Post> getAllPosts() {
		return postRepo.findAll();
	}

	/**
	 * Gets all posts that equal a specific title
	 */
	@Override
	public List<Post> getPostsByTitle(String title) {
		List<Post> posts = postRepo.getPostsByTitle(title);
		if(posts == null || posts.size() == 0) {
			throw new EntityNotFoundException(Post.class, "title", title);
		}
		return posts;
	}

	/**
	 * Gets all posts that are from a specific date and validates that it exists
	 */
	@Override
	public List<Post> getPostsByDate(LocalDate localDate) {
		List<Post> posts = postRepo.getPostsByDate(localDate);
		if(posts == null || posts.size() == 0) 
			throw new EntityNotFoundException(Post.class, "publish date", localDate.toString());
		return posts;
	}

	/**
	 * Gets a post by a specific id and verifies it exists
	 */
	@Override
	public Post getPostById(String id) {
		Optional<Post> post = postRepo.findById(id);
		if(post.isEmpty())
			throw new EntityNotFoundException(Post.class, "id", id);
		return post.get();
	}

	/**
	 * Updates a post with the given id to the post. Validates the required fields exist
	 */
	@Override
	public Post editPost(String id, Post post) {
		if(!postRepo.existsById(id))
			throw new EntityNotFoundException(Post.class, "id", id);
		post.setId(id);
		if(post.getTitle() == null || post.getTitle().isBlank())
			throw new FieldBlankException(Post.class, "Title", String.class.toString());
		if(post.getContents() == null || post.getContents().isBlank())
			throw new FieldBlankException(Post.class, "Contents", String.class.toString());
		if(post.getPublishDate() == null)
			throw new FieldBlankException(Post.class, "Publish Date", LocalDate.class.toString());
		
		List<Tag> tags = new LinkedList<>();
		for(int i = 0; i < post.getTags().size(); i++) {
			tags.add(tagService.addTag(post.getTags().get(i)));
		}
		post.setTags(tags);
		Post returnedPost = postRepo.save(post);
		return returnedPost;
	}

	/**
	 * Deletes a post with the given id. It also verifies that the post with the given id exists
	 */
	@Override
	public void deletePostById(String id) {
		if(!postRepo.existsById(id))
			throw new EntityNotFoundException(Post.class, "id", id);
		postRepo.deleteById(id);
	}

	/**
	 * Retrieves all posts that contain a tag with the given value.
	 */
	@Override
	public List<Post> getPostsByTagValue(String tagValue) {
		Tag tag = tagService.getTagByValue(tagValue);
		List<Post> posts = postRepo.getPostByTag(tag);
		if(posts == null || posts.size() == 0) 
			throw new EntityNotFoundException(Post.class, "tag value", tagValue);
		return posts;
	}
}
