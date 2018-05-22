package info.jsjackson.services;

import java.util.Set;

import info.jsjackson.commands.RecipeCommand;
import info.jsjackson.domain.Recipe;

public interface RecipeService {

	 Set<Recipe> getRecipes();

	 Recipe findById(String s);
	 
	 RecipeCommand findCommandById(String s);
	
	 RecipeCommand saveRecipeCommand(RecipeCommand command);
	 
	 void deleteById(String s);
	
}
