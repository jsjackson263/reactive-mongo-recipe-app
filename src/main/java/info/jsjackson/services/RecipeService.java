package info.jsjackson.services;

import info.jsjackson.commands.RecipeCommand;
import info.jsjackson.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {

	 Flux<Recipe> getRecipes();

	 Mono<Recipe> findById(String s);
	 
	 Mono<RecipeCommand> findCommandById(String s);
	
	 RecipeCommand saveRecipeCommand(RecipeCommand command);
	 
	 void deleteById(String s);
	
}
