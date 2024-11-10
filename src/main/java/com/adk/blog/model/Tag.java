package com.adk.blog.model;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Tag")
@Data @NoArgsConstructor @AllArgsConstructor
public class Tag {

	@Id
	@UuidGenerator
	private String id;
	
	private String value;
}
