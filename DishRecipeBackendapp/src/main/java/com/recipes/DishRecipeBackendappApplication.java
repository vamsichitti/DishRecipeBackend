package com.recipes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.recipes.jwt.entity.User;
import com.recipes.jwt.repository.UserRepository;

//import io.swagger.annotations.Info;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@EnableSwagger2
//@OpenAPIDefinition(info = @Info(title = "Employees API", version = "2.0", description = "Employees Information"))
@SecurityScheme(name = "recipesapi", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
@ComponentScan(basePackages = "com.recipes")
public class DishRecipeBackendappApplication {

	public static void main(String[] args) {
		SpringApplication.run(DishRecipeBackendappApplication.class, args);
	}
	@Autowired
	private UserRepository repository;
	

	@PostConstruct
	public void initUsers()
	{
		   List<User> users = Stream.of(
	                new User(101, "xadmin", "xadminpassword", "xadmin@gmail.com"),
	                new User(102, "vamsi", "vamsipassword", "vamsi@gmail.com"),
	                new User(103, "gurpreet", "gurpreetpassword", "gurpreet@gmail.com"),
	                new User(104, "mohit", "mohitpassword", "mohit@gmail.com")
	        ).collect(Collectors.toList());
	        repository.saveAll(users);
	}

}
