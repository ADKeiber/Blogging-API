package com.adk.blog.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adk.blog.errorhandling.ApiError;
import com.adk.blog.model.Post;
import com.adk.blog.model.Tag;
import com.adk.blog.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@SpringBootApplication
@RestController
@RequestMapping("/post")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Blog Post Controller")
public class PostController {

	@Autowired
	private PostService postService;

	@Operation(summary = "Create a new Post", description = "Creates a new post by taking in a JSON Post Object in the request body. If required fields are blank/null inside of the request body an API Error will be returned. "
			+ "Fields Required: title, contents, publishDate, tag.value", responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = Tag.class), examples = {
							@ExampleObject(value = "{\r\n" + "    \"id\": \"eee8f1c2-19d5-46c1-b0f9-6b0e4e709f95\",\r\n"
									+ "    \"title\": \"Blog Title\",\r\n"
									+ "    \"contents\": \"Blog's Contents\",\r\n"
									+ "    \"publishDate\": \"2024-11-09\",\r\n" + "    \"tags\": [\r\n"
									+ "        {\r\n"
									+ "            \"id\": \"4337e161-2655-41d9-ab6d-927f83d6634a\",\r\n"
									+ "            \"value\": \"tag example\"\r\n" + "        }\r\n" + "    ]\r\n"
									+ "}") })),
					@ApiResponse(description = "Bad Request/ Missing Required Field", responseCode = "400", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
							@ExampleObject(value = "{\r\n" + "    \"apierror\": {\r\n"
									+ "        \"status\": \"BAD_REQUEST\",\r\n"
									+ "        \"timestamp\": \"11-11-2024 02:25:54\",\r\n"
									+ "        \"message\": \"One of the Required fields was missing for the passed in entity!\",\r\n"
									+ "        \"debugMessage\": \"Post was missing value of field 'Title' which is of class java.lang.String\"\r\n"
									+ "    }\r\n" + "}") })) })
	@PostMapping("/create")
	public ResponseEntity<Object> create(@RequestBody Post p) {
		return new ResponseEntity<>(postService.addPost(p), HttpStatus.OK);
	}

	@Operation(summary = "Get all Posts", description = "Returns all Posts inside of a JSON list", responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Post.class)), examples = {
					@ExampleObject(value = "[\r\n" + "    {\r\n"
							+ "        \"id\": \"27df9a67-02eb-4217-8bbe-e3d69420d8d4\",\r\n"
							+ "        \"title\": \"Example Blog Title\",\r\n"
							+ "        \"contents\": \"Example Blog contents\",\r\n"
							+ "        \"publishDate\": \"2024-11-09\",\r\n" + "        \"tags\": [\r\n"
							+ "            {\r\n"
							+ "                \"id\": \"ae4dbcc2-7e6d-4ea5-979e-24ded04378de\",\r\n"
							+ "                \"value\": \"Example Tag\"\r\n" + "            }\r\n" + "        ]\r\n"
							+ "    },\r\n" + "    {\r\n"
							+ "        \"id\": \"3bd2bd02-f445-4302-9ed4-227bfeaa5b22\",\r\n"
							+ "        \"title\": \"Another Example Blog Title\",\r\n"
							+ "        \"contents\": \"Another Example Blog Contents\",\r\n"
							+ "        \"publishDate\": \"2024-11-09\",\r\n" + "        \"tags\": [\r\n"
							+ "            {\r\n"
							+ "                \"id\": \"ae4dbcc2-7e6d-4ea5-979e-24ded04378de\",\r\n"
							+ "                \"value\": \"Another Example Tag\"\r\n" + "            }\r\n"
							+ "        ]\r\n" + "    }\r\n" + "]") })) })
	@GetMapping("/getAll")
	public ResponseEntity<Object> getAll() {
		return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
	}

	@Operation(summary = "Get All Posts by Title", description = "Retrieves all Posts by taking in a title in the paramaters. "
			+ "If no post exists with the given title an API Error will be returned, otherwise a JSON List of Posts is returned.", responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = Tag.class), examples = {
							@ExampleObject(value = "[\r\n" + "    {\r\n"
									+ "        \"id\": \"4eac7a0f-76fe-4296-bb5f-ee79216fa94f\",\r\n"
									+ "        \"title\": \"Test Title\",\r\n"
									+ "        \"contents\": \"Test Contents\",\r\n"
									+ "        \"publishDate\": \"2024-11-09\",\r\n" + "        \"tags\": [\r\n"
									+ "            {\r\n"
									+ "                \"id\": \"7741f8dd-3242-4b6b-b62b-fce1b435794c\",\r\n"
									+ "                \"value\": \"test1\"\r\n" + "            }\r\n" + "        ]\r\n"
									+ "    }\r\n" + "]") })),
					@ApiResponse(description = "Bad Request/ No Post found", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
							@ExampleObject(value = "{\r\n" + "    \"apierror\": {\r\n"
									+ "        \"status\": \"NOT_FOUND\",\r\n"
									+ "        \"timestamp\": \"11-11-2024 02:56:56\",\r\n"
									+ "        \"message\": \"Post was not found for parameters {title=a}\",\r\n"
									+ "        \"debugMessage\": null\r\n" + "    }\r\n" + "}") })) })
	@GetMapping("/getByTitle/{title}")
	public ResponseEntity<Object> getByTitle(@PathVariable String title) {
		return new ResponseEntity<>(postService.getPostsByTitle(title), HttpStatus.OK);
	}

	@Operation(summary = "Get All Posts by Date", description = "Retrieves all Posts for a given date by taking in a date in the paramaters. "
			+ "If no post exists with given date an API Error will be returned, otherwise a JSON List of Posts is returned.", responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = Tag.class), examples = {
							@ExampleObject(value = "[\r\n" + "    {\r\n"
									+ "        \"id\": \"4eac7a0f-76fe-4296-bb5f-ee79216fa94f\",\r\n"
									+ "        \"title\": \"Test Title\",\r\n"
									+ "        \"contents\": \"Test Contents\",\r\n"
									+ "        \"publishDate\": \"2024-11-09\",\r\n" + "        \"tags\": [\r\n"
									+ "            {\r\n"
									+ "                \"id\": \"7741f8dd-3242-4b6b-b62b-fce1b435794c\",\r\n"
									+ "                \"value\": \"test1\"\r\n" + "            }\r\n" + "        ]\r\n"
									+ "    }\r\n" + "]") })),
					@ApiResponse(description = "Bad Request/ No Post found", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
							@ExampleObject(value = "{\r\n" + "    \"apierror\": {\r\n"
									+ "        \"status\": \"NOT_FOUND\",\r\n"
									+ "        \"timestamp\": \"11-11-2024 03:02:03\",\r\n"
									+ "        \"message\": \"Post was not found for parameters {publish date=2024-11-12}\",\r\n"
									+ "        \"debugMessage\": null\r\n" + "    }\r\n" + "}") })) })
	@GetMapping("/getByDate/{date}")
	public ResponseEntity<Object> getByDate(@PathVariable LocalDate date) {
		return new ResponseEntity<>(postService.getPostsByDate(date), HttpStatus.OK);
	}

	@Operation(summary = "Get Post by ID", description = "Retrieves a Post by taking in an ID in the paramaters. "
			+ "If no post exists with the given ID an API Error will be returned, otherwise a JSON of the Post is returned.", responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = Tag.class), examples = {
							@ExampleObject(value = "{\r\n" + "    \"id\": \"3bd2bd02-f445-4302-9ed4-227bfeaa5b22\",\r\n"
									+ "    \"title\": \"Unique Title1\",\r\n"
									+ "    \"contents\": \"Test contents1\",\r\n"
									+ "    \"publishDate\": \"2024-11-09\",\r\n" + "    \"tags\": [\r\n"
									+ "        {\r\n"
									+ "            \"id\": \"ae4dbcc2-7e6d-4ea5-979e-24ded04378de\",\r\n"
									+ "            \"value\": \"test\"\r\n" + "        }\r\n" + "    ]\r\n" + "}") })),
					@ApiResponse(description = "Bad Request/ No Post found", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
							@ExampleObject(value = "{\r\n" + "    \"apierror\": {\r\n"
									+ "        \"status\": \"NOT_FOUND\",\r\n"
									+ "        \"timestamp\": \"11-11-2024 03:02:03\",\r\n"
									+ "        \"message\": \"Post was not found for parameters {id=ae4dbcc2-7e6d-4ea5-979e-24ded04378de}\",\r\n"
									+ "        \"debugMessage\": null\r\n" + "    }\r\n" + "}") })) })
	@GetMapping("/getById/{id}")
	public ResponseEntity<Object> getById(@PathVariable String id) {
		return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
	}

	@Operation(summary = "Update a Post by ID", description = "Updates a Post by taking in a JSON Post Object in the request body and an ID in the paramaters. If required fields are blank/null inside of the request body or no post exists with given ID an API Error will be returned. "
			+ "Fields Required: title, contents, publishDate, tags.value", responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = Post.class), examples = {
							@ExampleObject(value = "{\r\n" + "    \"id\": \"22dc42b3-06a2-4bdf-b234-aaaea137855d\",\r\n"
									+ "    \"title\": \"Updated Title\",\r\n"
									+ "    \"contents\": \"Test contents12345\",\r\n"
									+ "    \"publishDate\": \"2024-11-09\",\r\n" + "    \"tags\": [\r\n"
									+ "        {\r\n"
									+ "            \"id\": \"a87bac41-3285-48ad-a24e-f2c48e766d4f\",\r\n"
									+ "            \"value\": \"test\"\r\n" + "        }\r\n" + "    ]\r\n" + "}") })),
					@ApiResponse(description = "Bad Request/ Missing Required Field", responseCode = "400", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
							@ExampleObject(value = "{\r\n" + "    \"apierror\": {\r\n"
									+ "        \"status\": \"BAD_REQUEST\",\r\n"
									+ "        \"timestamp\": \"11-11-2024 03:15:06\",\r\n"
									+ "        \"message\": \"One of the Required fields was missing for the passed in entity!\",\r\n"
									+ "        \"debugMessage\": \"Post was missing value of field 'Title' which is of class java.lang.String\"\r\n"
									+ "    }\r\n" + "}") })),
					@ApiResponse(description = "Bad Request/ No Post found", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
							@ExampleObject(value = "{\r\n" + "    \"apierror\": {\r\n"
									+ "        \"status\": \"NOT_FOUND\",\r\n"
									+ "        \"timestamp\": \"11-11-2024 03:20:38\",\r\n"
									+ "        \"message\": \"Post was not found for parameters {id=21c05df0-a8db-4e69-b349-706e890147e}\",\r\n"
									+ "        \"debugMessage\": null\r\n" + "    }\r\n" + "}") })) })
	@PostMapping("/update/{id}")
	public ResponseEntity<Object> updateById(@PathVariable String id, @RequestBody Post p) {
		return new ResponseEntity<>(postService.editPost(id, p), HttpStatus.OK);
	}

	@Operation(summary = "Delete a Post by ID", description = "Deletes a Post by taking in an ID in the paramaters."
			+ "If no post exists with the given ID an API Error will be returned, otherwise the post will be deleted.", responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(examples = {
							@ExampleObject(value = "") })),
					@ApiResponse(description = "Bad Request/ No Tag found", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
							@ExampleObject(value = "{\r\n" + "    \"apierror\": {\r\n"
									+ "        \"status\": \"NOT_FOUND\",\r\n"
									+ "        \"timestamp\": \"11-11-2024 03:29:50\",\r\n"
									+ "        \"message\": \"Post was not found for parameters {id=1cd6b03c-9b09-4b9c-92cb-2961f33c521}\",\r\n"
									+ "        \"debugMessage\": null\r\n" + "    }\r\n" + "}") })) })
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable String id) {
		postService.deletePostById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "Get all Posts that have the specified Tag attached", description = "Retrieves all Posts by taking in a tag's value in the paramaters. "
			+ "If no post exists with the given tag value or the tag value doesn't exist an API Error will be returned, otherwise a JSON List of the Posts is returned.", responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = Post.class), examples = {
							@ExampleObject(value = "[\r\n" + "    {\r\n"
									+ "        \"id\": \"30ba1748-6d2e-4109-a82c-b40905a90806\",\r\n"
									+ "        \"title\": \"Test Title1\",\r\n"
									+ "        \"contents\": \"Test Contents\",\r\n"
									+ "        \"publishDate\": \"2024-11-09\",\r\n" + "        \"tags\": [\r\n"
									+ "            {\r\n"
									+ "                \"id\": \"d3b1b8d1-bfda-4649-9654-42c38a90ebc3\",\r\n"
									+ "                \"value\": \"test123\"\r\n" + "            }\r\n"
									+ "        ]\r\n" + "    }\r\n" + "]") })),
					@ApiResponse(description = "Bad Request/ Tag not found", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
							@ExampleObject(value = "{\r\n" + "    \"apierror\": {\r\n"
									+ "        \"status\": \"NOT_FOUND\",\r\n"
									+ "        \"timestamp\": \"11-11-2024 03:52:19\",\r\n"
									+ "        \"message\": \"Tag was not found for parameters {value=test12334}\",\r\n"
									+ "        \"debugMessage\": null\r\n" + "    }\r\n" + "}") })),
					@ApiResponse(description = "Bad Request/ No Posts Found For Tag", responseCode = "404", content = @Content(schema = @Schema(implementation = ApiError.class), examples = {
							@ExampleObject(value = "{\r\n" + "    \"apierror\": {\r\n"
									+ "        \"status\": \"NOT_FOUND\",\r\n"
									+ "        \"timestamp\": \"11-11-2024 03:52:57\",\r\n"
									+ "        \"message\": \"Post was not found for parameters {tag value=test12341234}\",\r\n"
									+ "        \"debugMessage\": null\r\n" + "    }\r\n" + "}") })) })
	@GetMapping("/getByTagValue/{tag}")
	public ResponseEntity<Object> getByTagValue(@PathVariable String tag) {
		return new ResponseEntity<>(postService.getPostsByTagValue(tag), HttpStatus.OK);
	}
}
