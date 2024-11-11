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
		Optional<Tag> returnedTag = tagRepo.findByValue(tag.getValue());
		if(returnedTag.isPresent()) {
			return returnedTag.get();
		}
		return tagRepo.save(tag);
	}

	@Override
	public List<Tag> getAllTags() {
		return tagRepo.findAll();
	}

	@Override
	public Tag editTag(String id, Tag tag) {
		if(!tagRepo.existsById(id))
			throw new EntityNotFoundException(Tag.class, "id", id); 
		if(tag.getValue() == null || tag.getValue().isBlank())
			throw new FieldBlankException(Post.class, "value", String.class.toString());
		tag.setId(id);
		Tag returnedTag = tagRepo.save(tag);
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
		if(!tagRepo.existsById(id))
			throw new EntityNotFoundException(Tag.class, "id", id); 
		tagRepo.deleteById(id);
	}

	@Override
	@Transactional
	public void deleteTagByValue(String value) {
		if(!tagRepo.findByValue(value).isPresent())
			throw new EntityNotFoundException(Tag.class, "value", value); 
		tagRepo.deleteByValue(value);
	}

}
