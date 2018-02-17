package info.jsjackson.services;

import java.util.Set;

import info.jsjackson.domain.Recipe;

public interface RecipeService extends CRUDService<Recipe> {

	Set<Recipe> getRecipes();
}
