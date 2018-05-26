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
	public IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {

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
	public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

		Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
		if (!recipeOptional.isPresent()) {
			//recipe not found in DB
			//TODO: toss error if not found!
			log.error("Recipe not found for id: " + ingredientCommand.getRecipeId());
			return new IngredientCommand();
		} else {
			//we found a recipe in DB - get ingredient that matches the id presented
			Recipe recipe = recipeOptional.get();
			Optional<Ingredient> ingredientOptional = recipe
	                    .getIngredients()
	                    .stream()
	                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
	                    .findFirst();
			
			if (ingredientOptional.isPresent()) {
				//we're trying to add an existing ingredient - update it
				Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(ingredientCommand.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //TODO: address this
			} else {
              //no ingredient found in DB - add new Ingredient
                //recipe.addIngredient(ingredientCommandToIngredient.convert(command));
				Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
				//ingredient.setRecipe(recipe);
				recipe.addIngredient(ingredient);
			}
			 

			//save back the recipe (and it's ingredients)
			Recipe savedRecipe =  recipeRepository.save(recipe);
			
			//TODO: REVIEW below !!!!!!!
			
			 Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
	                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
	                    .findFirst();
			 
			//check by description
	         if(!savedIngredientOptional.isPresent()){
	             //not totally safe... But best guess
	             savedIngredientOptional = savedRecipe.getIngredients().stream()
	                     .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
	                     .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
	                     .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
	                     .findFirst();
	         }
	         
			//return back the saved ingredient command back to the view
			//TODO: do check for fail
	         return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
	         
		}
		
	}


	@Override
	public void deleteById(String recipeId, String ingredientId) {
		
		log.error("In Service - Deleting Ingredient: " + ingredientId + " for recipeId: " + recipeId);
		
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		if (recipeOptional.isPresent()) {
			Recipe recipe = recipeOptional.get();
			//TODO:the log statement below causes a StackOverflowError when domain objects
			//are annotated with @Data, instead of @Getter/@Setter. find out why
			log.debug("Found recipe: " + recipe.toString()); 
			
			Optional<Ingredient> ingredientOptional = 
					recipe
					.getIngredients()
					.stream()
					.filter(ingredient -> ingredient.getId().equals(ingredientId))
					.findFirst();
			
			if (ingredientOptional.isPresent()) {
				Ingredient ingredient = ingredientOptional.get();
				log.debug("Found ingredient: " + ingredient.toString());
				//ingredient.setRecipe(null);
				recipe.getIngredients().remove(recipeOptional.get());
				recipeRepository.save(recipe);
			}
		} else {
			//recipe not found in DB
			log.error("Recipe not found for id: " + recipeId);
		}
	}
	
}
