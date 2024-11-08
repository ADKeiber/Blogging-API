package com.adk.blog.service;

import java.util.List;

import com.adk.blog.model.Post;
import com.adk.blog.model.Tag;

public interface ITagService {
	
	//Create
	public Tag addTag(Tag tag);
	
	//Read
	public List<Tag> getAllTags();
	public List<Tag> getAllTagsForAPost(Post post);
	
	//Update
	public Tag editTag(String id, Tag tag);
	
	//Delete
	public Tag removeTag(String id);
	
}
