package com.adk.blog;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.adk.blog.controller.MainController;
import com.adk.blog.model.Post;
import com.adk.blog.model.Tag;
import com.adk.blog.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(MainController.class)
public class HttpRequestTests {
	//Tests
	static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PostService service;
	
	@Test
	void shouldReturnMessageWithName() throws Exception {
		this.mockMvc.perform(get("/hello").param("name", "Austin")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello Austin")));
	}
	
	@Test
	void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/hello")).andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(containsString("Hello World")));
	}
	
	@Test
	void shouldReturnPostAddedNoID() throws Exception {
		
		List<Tag> tags = new LinkedList<>();
		tags.add(new Tag());
		tags.get(0).setValue("TEST");
		
		Post post = new Post();
		post.setContents("Test Contents");
		post.setTags(tags);
		post.setTitle("Test Title");
		post.setPublishDate(LocalDate.now());
		
		Post returnedPost = new Post();
		returnedPost.setId("1");
		returnedPost.setContents("Test Contents");
		returnedPost.setTags(tags);
		returnedPost.setTitle("Test Title");
		returnedPost.setPublishDate(LocalDate.now());
		
		ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(post);
		when(service.addPost(post)).thenReturn(returnedPost);
		
		this.mockMvc.perform(
				post("/createPost").contentType(APPLICATION_JSON_UTF8)
		        .content(requestJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(returnedPost.getId()))
				.andExpect(jsonPath("$.title").value(returnedPost.getTitle()))
				.andExpect(jsonPath("$.contents").value(returnedPost.getContents()))
				.andExpect(jsonPath("$.publishDate").value(returnedPost.getPublishDate().toString()))
				.andExpect(jsonPath("$.tags[*].id", hasItem(tags.get(0).getId())))
				.andExpect(jsonPath("$.tags[*].value", hasItem(tags.get(0).getValue())));
	}
	
	@Test
	void shouldReturnPostAddedWithIDs() throws Exception {
		
		List<Tag> tags = new LinkedList<>();
		tags.add(new Tag("1", "TEST"));
		
		Post post = new Post("1", "Test Title", "Test Contents", LocalDate.now(), tags);
		
		ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(post);
		when(service.addPost(post)).thenReturn(post);
		
		this.mockMvc.perform(
				post("/createPost").contentType(APPLICATION_JSON_UTF8)
		        .content(requestJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(post.getId()))
				.andExpect(jsonPath("$.title").value(post.getTitle()))
				.andExpect(jsonPath("$.contents").value(post.getContents()))
				.andExpect(jsonPath("$.publishDate").value(post.getPublishDate().toString()))
				.andExpect(jsonPath("$.tags[*].id", hasItem(tags.get(0).getId())))
				.andExpect(jsonPath("$.tags[*].value", hasItem(tags.get(0).getValue())));
	}
	
	@Test
	void shouldReturnWithDefaultIDAddPostMissingTitle() throws Exception {
		
		List<Tag> tags = new LinkedList<>();
		tags.add(new Tag("0", "TEST"));
		
		Post post = new Post();
		post.setContents("Test Contents");
		post.setTags(tags);
		post.setTitle("");
		post.setPublishDate(LocalDate.now());
		
		Post returnedPost = new Post();
		returnedPost.setId("0");
		returnedPost.setContents("Test Contents");
		returnedPost.setTags(tags);
		returnedPost.setTitle("");
		returnedPost.setPublishDate(LocalDate.now());
		
		ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(post);
		when(service.addPost(post)).thenReturn(returnedPost);
		
		this.mockMvc.perform(
				post("/createPost").contentType(APPLICATION_JSON_UTF8)
		        .content(requestJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(returnedPost.getId()))
				.andExpect(jsonPath("$.title").value(returnedPost.getTitle()))
				.andExpect(jsonPath("$.contents").value(returnedPost.getContents()))
				.andExpect(jsonPath("$.publishDate").value(returnedPost.getPublishDate().toString()))
				.andExpect(jsonPath("$.tags[*].id", hasItem(tags.get(0).getId())))
				.andExpect(jsonPath("$.tags[*].value", hasItem(tags.get(0).getValue())));
	}
	
	@Test
	void shouldReturnPostWithDefaultIDAddPostMissingContents() throws Exception {
		
		List<Tag> tags = new LinkedList<>();
		tags.add(new Tag("0", "TEST"));
		
		Post post = new Post();
		post.setContents("");
		post.setTags(tags);
		post.setTitle("Test Title");
		post.setPublishDate(LocalDate.now());
		
		Post returnedPost = new Post();
		returnedPost.setId("0");
		returnedPost.setContents("");
		returnedPost.setTags(tags);
		returnedPost.setTitle("Test Title");
		returnedPost.setPublishDate(LocalDate.now());
		
		ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(post);
		when(service.addPost(post)).thenReturn(returnedPost);
		
		this.mockMvc.perform(
				post("/createPost").contentType(APPLICATION_JSON_UTF8)
		        .content(requestJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(returnedPost.getId()))
				.andExpect(jsonPath("$.title").value(returnedPost.getTitle()))
				.andExpect(jsonPath("$.contents").value(returnedPost.getContents()))
				.andExpect(jsonPath("$.publishDate").value(returnedPost.getPublishDate().toString()))
				.andExpect(jsonPath("$.tags[*].id", hasItem(tags.get(0).getId())))
				.andExpect(jsonPath("$.tags[*].value", hasItem(tags.get(0).getValue())));
	}
	
	@Test
	void shouldReturnNullAddPostMissingPublishDate() throws Exception {
		
		List<Tag> tags = new LinkedList<>();
		tags.add(new Tag("0", "TEST"));
		
		Post post = new Post();
		post.setContents("Test Contents");
		post.setTags(tags);
		post.setTitle("Test Title");
		
		Post returnedPost = new Post();
		returnedPost.setId("0");
		returnedPost.setContents("Test Contents");
		returnedPost.setTags(tags);
		returnedPost.setTitle("Test Title");
		
		ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(post);
		when(service.addPost(post)).thenReturn(returnedPost);
		
		this.mockMvc.perform(
				post("/createPost").contentType(APPLICATION_JSON_UTF8)
		        .content(requestJson))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(returnedPost.getId()))
				.andExpect(jsonPath("$.title").value(returnedPost.getTitle()))
				.andExpect(jsonPath("$.contents").value(returnedPost.getContents()))
				.andExpect(jsonPath("$.publishDate").value(returnedPost.getPublishDate()))
				.andExpect(jsonPath("$.tags[*].id", hasItem(tags.get(0).getId())))
				.andExpect(jsonPath("$.tags[*].value", hasItem(tags.get(0).getValue())));
	}
}
