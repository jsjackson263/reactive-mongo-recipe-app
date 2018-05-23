/**
 * 
 */
package info.jsjackson.services;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import info.jsjackson.commands.RecipeCommand;
import info.jsjackson.converters.RecipeCommandToRecipe;
import info.jsjackson.converters.RecipeToRecipeCommand;
import info.jsjackson.domain.Recipe;
import info.jsjackson.repositories.RecipeRepository;

/**
 * @author josan 
 *
 */
@Ignore
@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest
public class RecipeServiceIT {

	public static final String NEW_DESCRIPTION = "New Description";
	
	@Autowired
	RecipeService recipeService;
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	RecipeCommandToRecipe recipeCommandToRecipe;
	
	@Autowired
	RecipeToRecipeCommand recipeToRecipeCommand;
	
	@Transactional
	@Test
	public void testSaveOfDescription() throws Exception {
		
		//Given
		Iterable<Recipe> recipes = recipeRepository.findAll();
		Recipe testRecipe = recipes.iterator().next();
		RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);
		
		//When
		testRecipeCommand.setDescription(NEW_DESCRIPTION);
		RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);
		
		
		//Then
		assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
		assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
		assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
		assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
		
		
		
	}
}
