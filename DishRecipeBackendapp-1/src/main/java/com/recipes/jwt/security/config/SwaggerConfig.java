package com.recipes.jwt.security.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	private static final String  AUTHORIZATION_HEADER= "Authorization";
	
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo())
				.securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKeys()))
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}
     
	private List<SecurityContext> securityContexts(){
		return Arrays.asList( SecurityContext.builder().securityReferences(sf()).build());
	}
	
	private List<SecurityReference> sf(){
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); 
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
	    authorizationScopes[0] = authorizationScope; 
	    return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	}
	private ApiKey apiKeys() {
		return new ApiKey("JWT",AUTHORIZATION_HEADER,"header");
		
	}

	@SuppressWarnings("deprecation")
	private ApiInfo getInfo() {
		// TODO Auto-generated method stub
		return new ApiInfo(
			      "My REST API",
			      "Some custom description of API.",
			      "1.0",
			      "Terms of service",
			      
			      "License of API",
			      "API license URL", null);
	}

}
