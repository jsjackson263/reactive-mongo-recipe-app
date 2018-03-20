/**
 * 
 */
package info.jsjackson.services;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import info.jsjackson.commands.IngredientCommand;
import info.jsjackson.converters.IngredientCommandToIngredient;
import info.jsjackson.converters.IngredientToIngredientCommand;
import info.jsjackson.converters.UnitOfMeasureCommandToUnitOfMeasure;
import info.jsjackson.converters.UnitOfMeasureToUnitOfMeasureCommand;
import info.jsjackson.domain.Ingredient;
import info.jsjackson.domain.Recipe;
import info.jsjackson.repositories.RecipeRepository;
import info.jsjackson.repositories.UnitOfMeasureRepository;

/**
 * @author josan 
 *
 */
public class IngredientServiceImplTest {

	@Mock
	RecipeRepository recipeRepository;
	
	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;
	
	IngredientToIngredientCommand ingredientToIngredientCommand;
	IngredientCommandToIngredient ingredientCommandToIngredient;
	
	IngredientService ingredientService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		//instantiate converters
		ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
		ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
		
		ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, 
				ingredientCommandToIngredient, recipeRepository, unitOfMeasureRepository);
		
	}

	@Test
	public void testFindByRecipeIdAndIngredientIdHappyPath() throws Exception{
		
		//Given
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(1L);
		ingredient1.setDescription("ingredient 1");
		
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(2L);
		ingredient2.setDescription("ingredient 2");
		
		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId(3L);
		ingredient3.setDescription("ingredient 3");
		
		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);
		
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		//When
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L,  3L);
		
		//Then
		assertEquals(Long.valueOf(3L), ingredientCommand.getId());
		assertEquals("ingredient 3", ingredientCommand.getDescription());
		assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
		
		verify(recipeRepository, times(1)).findById(1L);
		
	}
	
	@Test
	public void testSaveIngredientCommand() throws Exception {
		
		//Given
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(3L);
		ingredientCommand.setDescription("ingredient 3");
		ingredientCommand.setRecipeId(2L);
		
		Optional<Recipe> recipeOptional = Optional.of(new Recipe());
		
		Recipe savedRecipe = new Recipe();
		savedRecipe.setId(2L);
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId(3L);
		savedRecipe.getIngredients().iterator().next().setDescription("ingredient 3");
		savedRecipe.getIngredients().iterator().next().setAmount(new BigDecimal(2.2));
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		when(recipeRepository.save(any())).thenReturn(savedRecipe);
		
		//When
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);
		
		
		//Then
		assertEquals(Long.valueOf(3L), savedCommand.getId());
		assertEquals("ingredient 3", savedCommand.getDescription());
		assertEquals(new BigDecimal(2.2), savedCommand.getAmount());
		assertEquals(Long.valueOf(2L), savedCommand.getRecipeId());
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, times(1)).save(any(Recipe.class));
		
		
		
	}

}
