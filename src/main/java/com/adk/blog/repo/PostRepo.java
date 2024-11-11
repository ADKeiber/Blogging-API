package com.adk.blog.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adk.blog.model.Post;
import com.adk.blog.model.Tag;

/**
 * Post Repository interface used to interact with the post repository
 */
@Repository
public interface PostRepo extends ListCrudRepository<Post, String>{
	@Query("SELECT p FROM Post p WHERE p.title = :title")
	List<Post> getPostsByTitle(@Param("title")String title);
	
	@Query("SELECT p FROM Post p WHERE p.publishDate = :date")
	List<Post> getPostsByDate(@Param("date")LocalDate date);
	
	@Query("SELECT p FROM Post p WHERE :tag member p.tags")
	List<Post> getPostByTag(@Param("tag")Tag tag);
}
