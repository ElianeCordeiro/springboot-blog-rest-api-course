package com.springboot.blog.springbootblogrestapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Blog App REST APIs",
				description = "Spring Boot Blog App REST APIs Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Eliane",
						email = "eliane@gmail.com",
						url = "https://www.javaguides.net"
						
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.javaguides.net"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog App Documentation",
				url = "https://github.com/RameshMF/springboot-blog-rest-api"
		)
		
) 
public class SpringbootBlogRestApiApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	} 
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}

}


 