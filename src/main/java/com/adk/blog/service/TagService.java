package com.adk.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adk.blog.model.Post;
import com.adk.blog.model.Tag;
import com.adk.blog.repo.TagRepo;

@Service
public class TagService implements ITagService{
	
	@Autowired
	private TagRepo tagRepo;

	@Override
	public Tag addTag(Tag tag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tag> getAllTags() {
		return tagRepo.findAll();
	}

	@Override
	public List<Tag> getAllTagsForAPost(Post post) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tag editTag(String id, Tag tag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tag removeTag(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
