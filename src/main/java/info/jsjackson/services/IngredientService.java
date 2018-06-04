/**
 * 
 */
package info.jsjackson.services;

import info.jsjackson.commands.IngredientCommand;
import reactor.core.publisher.Mono;

/**
 * @author josan 
 *
 */
public interface IngredientService {

	Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);
	
	Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command);
	
	Mono<Void> deleteById(String recipeId, String ingredientId);
	
}
