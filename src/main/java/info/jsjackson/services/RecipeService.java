package info.jsjackson.services;

import java.util.Set;

import info.jsjackson.commands.RecipeCommand;
import info.jsjackson.domain.Recipe;

public interface RecipeService {

	 Set<Recipe> getRecipes();

	 Recipe findById(Long l);
	
	RecipeCommand saveRecipeCommand(RecipeCommand command);
	
}
