package com.adk.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adk.blog.errorhandling.EntityNotFoundException;
import com.adk.blog.errorhandling.FieldBlankException;
import com.adk.blog.model.Post;
import com.adk.blog.model.Tag;
import com.adk.blog.repo.TagRepo;

@Service
public class TagService implements ITagService{
	
	@Autowired
	private TagRepo tagRepo;

	@Override
	public Tag addTag(Tag tag) {
		if(tag.getValue() == null || tag.getValue().isBlank())
			throw new FieldBlankException(Post.class, "value", String.class.toString());
		return tagRepo.save(tag);
	}

	@Override
	public List<Tag> getAllTags() {
		return tagRepo.findAll();
	}

	@Override
	public Tag editTag(String id, Tag tag) {
		tag.setId(id);
		Tag returnedTag = tagRepo.save(tag);
		//TODO Maybe create an exception for returned data != original data with new id
		return returnedTag;
	}

	@Override
	public Tag getTagById(String id) {
		Optional<Tag> tag = tagRepo.findById(id);
		if(tag.isEmpty())
			throw new EntityNotFoundException(Tag.class, "id", id);
		return tag.get();
	}

	@Override
	public Tag getTagByValue(String value) {
		Optional<Tag> tag = tagRepo.findByValue(value);
		if(tag.isEmpty())
			throw new EntityNotFoundException(Tag.class, "value", value);
		return tag.get();
	}

	@Override
	public void deleteTagById(String id) {
		tagRepo.deleteById(id);
		//TODO... I feel like something should be checked here
	}

	@Override
	@Transactional
	public void deleteTagByValue(String value) {
		tagRepo.deleteByValue(value);
		//TODO... I feel like something should be checked here
	}

}
