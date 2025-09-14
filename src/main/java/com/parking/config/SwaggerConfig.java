package com.parking.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public Docket api() {
	return new Docket(DocumentationType.SWAGGER_2).select()
		.apis(RequestHandlerSelectors.basePackage("com.parking.controller")).paths(PathSelectors.any()).build()
		.apiInfo(apiInfo()).securitySchemes(securitySchemes()).securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
	return new ApiInfoBuilder().title("Parking Lot System API")
		.description("API Documentation for the Parking Lot Management System").version("1.0")
		.contact(new Contact("Your Name", "http://yourwebsite.com", "your.email@example.com"))
		.license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html").build();
    }

    private List<SecurityScheme> securitySchemes() {
	// Use a List implementation compatible with Java 8
	return Arrays.asList(new ApiKey("JWT", AUTHORIZATION_HEADER, "header"));
    }

    private List<SecurityContext> securityContexts() {
	return Arrays.asList(
		SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build());
    }

    private List<SecurityReference> defaultAuth() {
	AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
	AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	authorizationScopes[0] = authorizationScope;
	return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
}