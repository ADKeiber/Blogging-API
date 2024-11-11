package com.adk.blog.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Configuration Class used to set OpenAPI Information
 */
@OpenAPIDefinition(
	info = @Info(
			contact = @Contact(
					name = "Austin Keiber",
					email = "adkeiber@gmail.com"
					),
			description = "OpenAPI Documentation for Blogging API",
			title = "OpenAPI specification - Austin Keiber",
			version = "1.0"
		),
	servers = {
			@Server(
				description = "Local ENV",
				url = "http://localhost:8080"
			)
	}
)
@Configuration
public class OpenApiConfig {
}
