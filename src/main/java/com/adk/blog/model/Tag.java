package com.adk.blog.model;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Object that represents a Blog Post's tag
 */
@Entity
@Table(name="Tag")
@Data @NoArgsConstructor @AllArgsConstructor
public class Tag {

	@Id
	@UuidGenerator
	private String id;
	
	private String value;
}
