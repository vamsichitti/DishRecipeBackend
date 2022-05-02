package com.recipes.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipes.entity.Recipe;
import com.recipes.exception.RecipeNotFoundException;
import com.recipes.repository.IRecipeRepository;
import com.recipes.services.RecipeServices;

@RestController
@RequestMapping("/recipes")
public class RecipeBackendController {
	
	@Autowired
	RecipeServices recipeService;
	
	@Autowired
	IRecipeRepository recipeRepo;
	
	
	@PostMapping("/addRecipe")
	public ResponseEntity<?> createRecipe(@RequestBody Recipe recipe) throws RecipeNotFoundException{
		Optional<?> opt1 = recipeRepo.findById(recipe.getRecipeId());
		Optional<?> opt2 = recipeRepo.findByName(recipe.getName());
		
		if(opt1.isPresent() || opt2.isPresent()) {
			throw new RecipeNotFoundException(" Id "+recipe.getRecipeId()+" already exists");
			
		}
		else {
			recipeService.addRecipe(recipe);
			return new ResponseEntity<>("Successfully created",HttpStatus.CREATED);
		}
	}
	
	@GetMapping("/getRecipe/{id}")
	public ResponseEntity<Optional<Recipe>> getRecipeById(@PathVariable("id") int id) throws RecipeNotFoundException{
		if(recipeRepo.existsById(id)) {
			Optional<Recipe> resp=recipeService.getRecipeById(id);
			
			return new ResponseEntity<>(resp,HttpStatus.OK);
		}
		else {
			throw new RecipeNotFoundException("ID "+id+" not found ");
		}
			
	}
	@GetMapping("/{recipeName}")
	public ResponseEntity<?> getRecipeByName(@PathVariable("recipeName") String recipeName) throws RecipeNotFoundException {

		Optional<Recipe> opt = recipeRepo.findByName(recipeName);

		if (opt.isPresent()) {

			return new ResponseEntity<>( recipeService.getRecipeByName(recipeName), HttpStatus.OK);

		}

		else {

			throw new RecipeNotFoundException(" Recipe with name : " + recipeName + " is not found ");
		}

	}
	
	@PutMapping("/updateRecipe/{recipeId}")
	public ResponseEntity<?> updateRecipe(@RequestBody Recipe recipe) throws RecipeNotFoundException {

		if (recipeRepo.existsById(recipe.getRecipeId())) {

			recipeService.updateRecipe(recipe);

			return new ResponseEntity<>(" Recipe Id " + recipe.getRecipeId() + " is updated successfully ",
					HttpStatus.ACCEPTED);

		} else {
			throw new RecipeNotFoundException("Recipe Id " + recipe.getRecipeId() + " is not found ");
		}

	}
	
	@DeleteMapping("/deleteMapping/{recipeId}")
	public ResponseEntity<String> deleteRecipe(@PathVariable("recipeId") int recipeId) throws RecipeNotFoundException {

		Optional<Recipe> opt = recipeRepo.findById(recipeId);

		if (opt.isPresent()) {

			recipeService.deleteRecipe(recipeId);

			return new ResponseEntity<>("Recipe id : " + recipeId + " is deleted successfully", HttpStatus.OK);
		} else {

			throw new RecipeNotFoundException(" Recipe id : " + recipeId + " is not found ");

		}
	}
	
	@GetMapping("/allRecipes")
	public ResponseEntity<?> getAllRecipes() throws RecipeNotFoundException {

		if (!recipeRepo.findAll().isEmpty()) {

			return new ResponseEntity<>(recipeService.recipesList(), HttpStatus.OK);

		} else {

			throw new RecipeNotFoundException("No recipes in the list ");

		}
	}
	
	

}
