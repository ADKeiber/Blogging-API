package com.adk.blog.model;


import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Object that represents a Blog's Post
 */
@Entity
@Table(name="Post")
@Data @NoArgsConstructor @AllArgsConstructor
public class Post {
	
	@Id
	@UuidGenerator
	private String id;
	
	private String title;
	
	private String contents;
	
	
	private LocalDate publishDate;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name = "post_tag",
			joinColumns = @JoinColumn(name = "post_id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private List<Tag> tags;
}
