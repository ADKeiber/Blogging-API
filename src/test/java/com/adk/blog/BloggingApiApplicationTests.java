package com.adk.blog;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.adk.blog.controller.TagController;

@SpringBootTest
class BloggingApiApplicationTests {
	
	@Autowired
	private TagController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
