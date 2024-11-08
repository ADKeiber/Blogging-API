package com.adk.blog.model;


import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	
	@OneToMany(mappedBy="id")
	private List<Tag> tags;
}
