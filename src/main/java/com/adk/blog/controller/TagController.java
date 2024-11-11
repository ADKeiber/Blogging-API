package com.adk.blog.controller;

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
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.ArraySchema;

import com.adk.blog.errorhandling.ApiError;
import com.adk.blog.model.Tag;
import com.adk.blog.service.TagService;

import io.swagger.v3.oas.annotations.Operation;

@SpringBootApplication
@RestController
@RequestMapping("/tag")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Blog Tag Controller")
public class TagController {
	
	@Autowired
	private TagService tagService;
	
	@Operation(
			summary = "Create a new Tag",
			description = "Creates a new tag by taking in a JSON Tag Object in the request body. If required fields are blank/null inside of the request body an API Error will be returned. Fields Required: value",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = @Content(
								schema = @Schema(implementation = Tag.class),
								examples = {
							            @ExampleObject(
							                value = "{\n" +
							                    "	\"id\":\"1024c237-0938-43bc-9c4e-f533ae5a22e6\",\n" +
							                    "	\"value\":\"Example Blog Tag\"\n" +
							                    "}"
							            )
							        }
							)
					),
					@ApiResponse(
							description = "Bad Request/ Missing Required Field",
							responseCode = "400",
							content = @Content(
								schema = @Schema(implementation = ApiError.class),
								examples = {
							            @ExampleObject(
							                value = "{\n" +
							                	"	\"apierror\":\n"+
							                	"	{\n" +
							                    "		\"status\":\"BAD_REQUEST\",\n" +
							                    "		\"timestamp\":\"11-11-2024 10:19:28\",\n" +
							                    "		\"message\":\"One of the Required fields was missing for the passed in entity!\",\n" +
							                    "		\"debugMessage\":\"Tag was missing value of field 'value' which is of class java.lang.String\",\n" +
							                    "	}\n"+
							                    "}"
							            )
								}
							)
					)
			})
	@PostMapping("/create")
	public ResponseEntity<Object> create(@RequestBody Tag t) {
        return new ResponseEntity<>(tagService.addTag(t), HttpStatus.OK);
	}
	
	@Operation(
			summary = "Get all tags",
			description = "Returns all Tags inside of a JSON list",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = @Content(
								array = @ArraySchema( schema = @Schema(implementation = Tag.class)),
								examples = {
							            @ExampleObject(
							                value = "[\n" +
								                	"	{\n" +
								                    "		\"id\":\"1024c237-0938-43bc-9c4e-f533ae5a22e6\",\n" +
								                    "		\"value\":\"Test tag12345\",\n" +
								                    "	},\n"+
								                    "	{\n" +
								                    "		\"id\":\"1024c237-0938-43bc-9c4e-f533ae5a22e7\",\n" +
								                    "		\"value\": \"Test tag1234\"\n"+
								                    "	}\n"+
								                    "]"
							            )
							        }
							)
					)
				}
	)
	@GetMapping("/getAll")
	public ResponseEntity<Object> getAllTags() {
        return new ResponseEntity<>(tagService.getAllTags(), HttpStatus.OK);
	}
	
	@Operation(
			summary = "Update a Tag by ID",
			description = "Updates a Tag by taking in a JSON Tag Object in the request body and an ID in the paramaters. If required fields are blank/null inside of the request body or no tag exists with given ID an API Error will be returned. Fields Required: value",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = @Content(
								schema = @Schema(implementation = Tag.class),
								examples = {
							            @ExampleObject(
							                value = "{\n" +
							                    "	\"id\":\"1024c237-0938-43bc-9c4e-f533ae5a22e6\",\n" +
							                    "	\"value\":\"Example Blog Tag\"\n" +
							                    "}"
							            )
							        }
							)
					),
					@ApiResponse(
							description = "Bad Request/ Missing Required Field",
							responseCode = "400",
							content = @Content(
								schema = @Schema(implementation = ApiError.class),
								examples = {
							            @ExampleObject(
							                value = "{\n" +
							                	"	\"apierror\":\n"+
							                	"	{\n" +
							                    "		\"status\":\"BAD_REQUEST\",\n" +
							                    "		\"timestamp\":\"11-11-2024 10:19:28\",\n" +
							                    "		\"message\":\"One of the Required fields was missing for the passed in entity!\",\n" +
							                    "		\"debugMessage\":\"Post was missing value of field 'value' which is of class java.lang.String\",\n" +
							                    "	}\n"+
							                    "}"
							            )
								}
							)
					),
					@ApiResponse(
							description = "Bad Request/ No Tag found",
							responseCode = "404",
							content = @Content(
								schema = @Schema(implementation = ApiError.class),
								examples = {
							            @ExampleObject(
							                value = "{\n" +
							                	"	\"apierror\":\n"+
							                	"	{\n" +
							                    "		\"status\":\"NOT_FOUND\",\n" +
							                    "		\"timestamp\":\"11-11-2024 10:19:28\",\n" +
							                    "		\"message\":\"Tag was not found for parameters {id=a3cffc8b-bcfa-4143-8f75-79639efde58}\",\n" +
							                    "		\"debugMessage\": null,\n" +
							                    "	}\n"+
							                    "}"
							            )
								}
							)
					)
			})
	@PostMapping("/update/{id}")
	public ResponseEntity<Object> updateById(@PathVariable String id, @RequestBody Tag t) {
        return new ResponseEntity<>(tagService.editTag(id, t), HttpStatus.OK);
	}
	
	@Operation(
			summary = "Get a Tag by ID",
			description = "Retrieves a Tag by taking in an ID in the paramaters."
					+ "If no tag exists with the given ID an API Error will be returned, otherwise a JSON Tag is returned.",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = @Content(
								schema = @Schema(implementation = Tag.class),
								examples = {
							            @ExampleObject(
							                value = "{\n" +
							                    "	\"id\":\"1024c237-0938-43bc-9c4e-f533ae5a22e6\",\n" +
							                    "	\"value\":\"Example Blog Tag\"\n" +
							                    "}"
							            )
							        }
							)
					),
					@ApiResponse(
							description = "Bad Request/ No Tag found",
							responseCode = "404",
							content = @Content(
								schema = @Schema(implementation = ApiError.class),
								examples = {
							            @ExampleObject(
							                value = "{\n" +
							                	"	\"apierror\":\n"+
							                	"	{\n" +
							                    "		\"status\":\"NOT_FOUND\",\n" +
							                    "		\"timestamp\":\"11-11-2024 10:19:28\",\n" +
							                    "		\"message\":\"Tag was not found for parameters {id=a3cffc8b-bcfa-4143-8f75-79639efde58}\",\n" +
							                    "		\"debugMessage\": null,\n" +
							                    "	}\n"+
							                    "}"
							            )
								}
							)
					)
			})
	@GetMapping("/getById/{id}")
	public ResponseEntity<Object> getById(@PathVariable String id) {
        return new ResponseEntity<>(tagService.getTagById(id), HttpStatus.OK);
	}
	
	@Operation(
			summary = "Get a Tag by Value",
			description = "Retrieves a Post by taking in an ID in the paramaters. "
					+ "If no tag exists with given ID an API Error will be returned, otherwise a JSON Tag is returned.",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = @Content(
								schema = @Schema(implementation = Tag.class),
								examples = {
							            @ExampleObject(
							                value = "{\n" +
							                    "	\"id\":\"1024c237-0938-43bc-9c4e-f533ae5a22e6\",\n" +
							                    "	\"value\":\"Example Blog Tag\"\n" +
							                    "}"
							            )
							        }
							)
					),
					@ApiResponse(
							description = "Bad Request/ No Tag found",
							responseCode = "404",
							content = @Content(
								schema = @Schema(implementation = ApiError.class),
								examples = {
							            @ExampleObject(
							                value = "{\n" +
							                	"	\"apierror\":\n"+
							                	"	{\n" +
							                    "		\"status\":\"NOT_FOUND\",\n" +
							                    "		\"timestamp\":\"11-11-2024 10:19:28\",\n" +
							                    "		\"message\":\"Tag was not found for parameters {value=Example Blog Tag}\",\n" +
							                    "		\"debugMessage\": null,\n" +
							                    "	}\n"+
							                    "}"
							            )
								}
							)
					)
			})
	@GetMapping("/getByValue/{value}")
	public ResponseEntity<Object> getByValue(@PathVariable String value) {
        return new ResponseEntity<>(tagService.getTagByValue(value), HttpStatus.OK);
	}
	
	@Operation(
			summary = "Delete a Tag by ID",
			description = "Deletes a Tag by taking in an ID in the paramaters."
					+ "If no tag exists with the given ID an API Error will be returned, otherwise the tag will be deleted.",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = @Content(
								examples = {
							            @ExampleObject(
							                value = ""
							            )
							        }
							)
					),
					@ApiResponse(
							description = "Bad Request/ No Tag found",
							responseCode = "404",
							content = @Content(
								schema = @Schema(implementation = ApiError.class),
								examples = {
							            @ExampleObject(
							                value = "{\n" +
							                	"	\"apierror\":\n"+
							                	"	{\n" +
							                    "		\"status\":\"NOT_FOUND\",\n" +
							                    "		\"timestamp\":\"11-11-2024 10:19:28\",\n" +
							                    "		\"message\":\"Tag was not found for parameters {id=a3cffc8b-bcfa-4143-8f75-79639efde58}\",\n" +
							                    "		\"debugMessage\": null,\n" +
							                    "	}\n"+
							                    "}"
							            )
								}
							)
					)
			})
	@DeleteMapping("/deleteById/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable String id) {
		tagService.deleteTagById(id);
        return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@Operation(
			summary = "Delete a Tag by Value",
			description = "Deletes a Post by taking in an ID in the paramaters."
					+ "If no tag exists with given value an API Error will be returned, otherwise the tag will be deleted.",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = @Content(
								examples = {
							            @ExampleObject(
							                value = ""
							            )
							        }
							)
					),
					@ApiResponse(
							description = "Bad Request/ No Tag found",
							responseCode = "404",
							content = @Content(
								schema = @Schema(implementation = ApiError.class),
								examples = {
							            @ExampleObject(
							                value = "{\n" +
							                	"	\"apierror\":\n"+
							                	"	{\n" +
							                    "		\"status\":\"NOT_FOUND\",\n" +
							                    "		\"timestamp\":\"11-11-2024 10:19:28\",\n" +
							                    "		\"message\":\"Tag was not found for parameters {value=Example Blog Tag}\",\n" +
							                    "		\"debugMessage\": null,\n" +
							                    "	}\n"+
							                    "}"
							            )
								}
							)
					)
			})
	@DeleteMapping("/deleteByValue/{value}")
	public ResponseEntity<Object> deleteByValue(@PathVariable String value) {
		tagService.deleteTagByValue(value);
        return new ResponseEntity<>(HttpStatus.OK);
	}
}
