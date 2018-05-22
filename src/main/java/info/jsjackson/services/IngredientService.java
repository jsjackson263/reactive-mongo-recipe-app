/**
 * 
 */
package info.jsjackson.services;

import info.jsjackson.commands.IngredientCommand;

/**
 * @author josan 
 *
 */
public interface IngredientService {

	IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId);
	
	IngredientCommand saveIngredientCommand(IngredientCommand command);
	
	void deleteById(String recipeId, String ingredientId);
	
}
