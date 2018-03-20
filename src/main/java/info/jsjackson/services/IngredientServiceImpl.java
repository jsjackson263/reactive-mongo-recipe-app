/**
 * 
 */
package info.jsjackson.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import info.jsjackson.commands.IngredientCommand;
import info.jsjackson.converters.IngredientCommandToIngredient;
import info.jsjackson.converters.IngredientToIngredientCommand;
import info.jsjackson.domain.Ingredient;
import info.jsjackson.domain.Recipe;
import info.jsjackson.repositories.RecipeRepository;
import info.jsjackson.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author josan 
 *
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;
	
	public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
			IngredientCommandToIngredient ingredientCommandToIngredient, 
			RecipeRepository recipeRepository,
			UnitOfMeasureRepository unitOfMeasureRepository) {
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}


	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
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


	@Override
	public IngredientCommand saveIngredientCommand(IngredientCommand command) {

		Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
		if (!recipeOptional.isPresent()) {
			//recipe not found in DB
			//TODO: toss error if not found!
			log.error("Recipe not found for id: " + command.getRecipeId());
			return new IngredientCommand();
		} else {
			//we found a recipe in DB - get ingredient that matches the id presented
			Recipe recipe = recipeOptional.get();
			Optional<Ingredient> ingredientOptional = recipe
	                    .getIngredients()
	                    .stream()
	                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
	                    .findFirst();
			
			if (ingredientOptional.isPresent()) {
				//we're trying to add an existing ingredient - update it
				Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(command.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //TODO: address this
			} else {
              //no ingredient found in DB - add new Ingredient
                recipe.addIngredient(ingredientCommandToIngredient.convert(command));
			}
			 

			//save back the recipe (and it's ingredients)
			Recipe savedRecipe =  recipeRepository.save(recipe);
			
			//return back the saved ingredient command back to the view
			//TODO: do check for fail
            return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst()
                    .get());
            
		}
		
	}

}
