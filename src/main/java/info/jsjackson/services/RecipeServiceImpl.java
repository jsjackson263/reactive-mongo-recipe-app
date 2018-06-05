/**
 * 
 */
package info.jsjackson.services;

import org.springframework.stereotype.Service;

import info.jsjackson.commands.RecipeCommand;
import info.jsjackson.converters.RecipeCommandToRecipe;
import info.jsjackson.converters.RecipeToRecipeCommand;
import info.jsjackson.domain.Recipe;
import info.jsjackson.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author josan
 *
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeReactiveRepository recipeReactiveRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;
	
	public RecipeServiceImpl(RecipeReactiveRepository recipeReactiveRepository, RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		this.recipeReactiveRepository = recipeReactiveRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Flux<Recipe> getRecipes() {
		log.debug("I'm in the service");
		
		//log.debug("LOGGER - Recipe Rescription: " + recipes.iterator().next().getDescription());
		//recipeRepository.findAll().forEach(recipes:: add); 
		//recipeReactiveRepository.findAll().iterator().forEachRemaining(recipes::add);
		return recipeReactiveRepository.findAll();
	}
	

	@Override
	public Mono<Recipe> findById(String id) {
		return recipeReactiveRepository.findById(id);
		
	}

	@Override
	public Mono<RecipeCommand> findCommandById(String id) {
		
		return recipeReactiveRepository.findById(id)
				.map(recipe -> {
					RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
					
					recipeCommand.getIngredients().forEach(rc -> {
						rc.setRecipeId(recipeCommand.getId());;
					});
					
					return recipeCommand;
				});
		
		/*RecipeCommand recipeCommand = recipeToRecipeCommand.convert(findById(id).block());

        //enhance command object with id value
        if(recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0){
            recipeCommand.getIngredients().forEach(rc -> {
                rc.setRecipeId(recipeCommand.getId());
            });
        }

        return recipeCommand;*/
	}

	@Override
	public RecipeCommand saveRecipeCommand(RecipeCommand command) {

		//detached from the hibernate context
		Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
		
		Recipe savedRecipe = recipeReactiveRepository.save(detachedRecipe).block();
		log.debug("Saved RecipeId: " + savedRecipe.getId());
		return recipeToRecipeCommand.convert(savedRecipe);
	}


	@Override
	public void deleteById(String idToDelete) {
		recipeReactiveRepository.deleteById(idToDelete).block();
	}
	

}
