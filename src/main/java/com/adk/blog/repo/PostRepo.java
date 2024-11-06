package com.adk.blog.repo;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.adk.blog.model.Post;

@Repository
public interface PostRepo extends ListCrudRepository<Post, String>{}
