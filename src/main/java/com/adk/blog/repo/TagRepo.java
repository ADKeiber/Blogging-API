package com.adk.blog.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adk.blog.model.Tag;

@Repository
public interface TagRepo extends ListCrudRepository<Tag, String>{
	@Query("SELECT t FROM Tag t WHERE t.value = :value")
	Tag findTagByValue(@Param("value")String value);
}