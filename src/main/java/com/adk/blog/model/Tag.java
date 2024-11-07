package com.adk.blog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Tag")
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	private String value;
}
