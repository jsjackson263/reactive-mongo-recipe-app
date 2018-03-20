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

	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
	
	IngredientCommand saveIngredientCommand(IngredientCommand command);
	
}
