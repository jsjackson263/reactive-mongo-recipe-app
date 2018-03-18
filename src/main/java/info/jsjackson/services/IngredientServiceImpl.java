/**
 * 
 */
package info.jsjackson.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import info.jsjackson.commands.IngredientCommand;
import info.jsjackson.converters.IngredientToIngredientCommand;
import info.jsjackson.domain.Recipe;
import info.jsjackson.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author josan 
 *
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final RecipeRepository repository;

	public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
			RecipeRepository repository) {
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.repository = repository;
	}


	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

		Optional<Recipe> recipeOptional = repository.findById(recipeId);
		if (!recipeOptional.isPresent()) {
			//TODO impl error handling
			 log.error("recipe id not found. Id: " + recipeId);
		}
		
		Recipe recipe = recipeOptional.get(); 
		
		//TODO: review this lambda!
		Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

	
		if (!ingredientCommandOptional.isPresent()) {
			//TODO impl error handling
            log.error("recipe id not found. Id: " + recipeId);
		}
		
		IngredientCommand ingredientCommand = ingredientCommandOptional.get();
		
		return ingredientCommand;
		
	}

}
