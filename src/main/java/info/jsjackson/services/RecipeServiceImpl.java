/**
 * 
 */
package info.jsjackson.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import info.jsjackson.commands.RecipeCommand;
import info.jsjackson.converters.RecipeCommandToRecipe;
import info.jsjackson.converters.RecipeToRecipeCommand;
import info.jsjackson.domain.Recipe;
import info.jsjackson.repositories.CategoryRepository;
import info.jsjackson.repositories.RecipeRepository;
import info.jsjackson.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author josan
 *
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;
	
	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Set<Recipe> getRecipes() {
		Set<Recipe> recipes = new HashSet<Recipe>();
		
		log.debug("I'm in the service");
		
		//log.debug("LOGGER - Recipe Rescription: " + recipes.iterator().next().getDescription());
		//recipeRepository.findAll().forEach(recipes:: add); 
		recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
		return recipes;
	}
	

	@Override
	public Recipe findById(Long id) {

		Optional<Recipe> recipeOptional = recipeRepository.findById(id);
		if (!recipeOptional.isPresent()) {
			throw new RuntimeException("Recipe Not Found");
		}
		
		return recipeOptional.get();
		
	}

	/*
	 * expand transactional scope to these methods, because we're doing conversion outside the scope
	 *  - any lazily loaded properties might create problems
	 */
	@Override
	@Transactional
	public RecipeCommand findCommandById(long l) {
		return recipeToRecipeCommand.convert(findById(l));
	}

	@Override
	@Transactional 
	public RecipeCommand saveRecipeCommand(RecipeCommand command) {

		//detached from the hibernate context
		Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
		
		Recipe savedRecipe = recipeRepository.save(detachedRecipe);
		log.debug("Saved RecipeId: " + savedRecipe.getId());
		return recipeToRecipeCommand.convert(savedRecipe);
	}

	
	

}
