package com.adk.blog.service;

import java.util.List;

import com.adk.blog.model.Tag;

public interface ITagService {
	
	//Create
	public Tag addTag(Tag tag);
	
	//Read
	public List<Tag> getAllTags();
	public Tag getTagById(String id);
	public Tag getTagByValue(String value);
	
	//Update
	public Tag editTag(String id, Tag tag);
	
	//Delete
	public void deleteTagById(String id);
	public void deleteTagByValue(String value);
	
}
