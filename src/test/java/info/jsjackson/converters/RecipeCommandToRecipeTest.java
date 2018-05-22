/**
 * 
 */
package info.jsjackson.converters;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import info.jsjackson.commands.CategoryCommand;
import info.jsjackson.commands.IngredientCommand;
import info.jsjackson.commands.NotesCommand;
import info.jsjackson.commands.RecipeCommand;
import info.jsjackson.domain.Difficulty;
import info.jsjackson.domain.Ingredient;
import info.jsjackson.domain.Recipe;

/**
 * @author josan 
 *
 */
public class RecipeCommandToRecipeTest {

	RecipeCommandToRecipe converter;
	
	static final String ID = new String("1");
	static final String DESCRIPTION = "My Recipe";
	static final Integer PREP_TIME = Integer.valueOf("10");
	static final Integer COOK_TIME = Integer.valueOf("20");
	static final Integer SERVINGS = Integer.valueOf("5");
	static final String SOURCE = "source";
	static final String URL = "www.example.com";
	static final String DIRECTIONS = "Directions";
	static final Difficulty DIFFICULTY = Difficulty.EASY;
	static final String INGRED_ID_1 = "1";
	static final String INGRED_ID_2 = "2";
	static final String INGRED_ID_3 = "3";
	static final String NOTES_ID = "20";
	static final String CAT_ID_1 = "1";
	static final String CAT_ID_2 = "2";
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		converter = new RecipeCommandToRecipe(
				new CategoryCommandToCategory(),
				new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
				new NotesCommandToNotes());
	}

	@Test
	public void testNullObjectConversion() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObjectConversion() throws Exception {
		assertNotNull(converter.convert(new RecipeCommand()));
	}
	
	@Test
	public void testConvertWithNonNullObjects() throws Exception {
		
		//Given
		RecipeCommand command = new RecipeCommand();
		command.setId(ID);
		command.setDescription(DESCRIPTION);
		command.setPrepTime(PREP_TIME);
		command.setCookTime(COOK_TIME);
		command.setServings(SERVINGS);
		command.setSource(SOURCE);
		command.setUrl(URL);
		command.setDirections(DIRECTIONS);
		
		IngredientCommand ingredient1 = new IngredientCommand();
		ingredient1.setId(INGRED_ID_1);
		command.getIngredients().add(ingredient1);
		
		IngredientCommand ingredient2 = new IngredientCommand();
		ingredient2.setId(INGRED_ID_2);
		command.getIngredients().add(ingredient2);
		
		IngredientCommand ingredient3 = new IngredientCommand();
		ingredient3.setId(INGRED_ID_3);
		command.getIngredients().add(ingredient3);
		
		command.setDifficulty(DIFFICULTY);
		
		NotesCommand notes = new NotesCommand();
		notes.setId(NOTES_ID);
		command.setNotes(notes);
		
		CategoryCommand category1 = new CategoryCommand();
		category1.setId(CAT_ID_1);
		command.getCategories().add(category1);
		
		CategoryCommand category2 = new CategoryCommand();
		category2.setId(CAT_ID_2);
		command.getCategories().add(category2);
		

		//When
		Recipe recipe = converter.convert(command);
		
		//Then
		assertNotNull(recipe);
		//asertNotNull(recipe.getIngredients());
		assertNotNull(recipe.getCategories());
		assertEquals(ID, recipe.getId());
		assertEquals(DESCRIPTION, recipe.getDescription());
		assertEquals(PREP_TIME, recipe.getPrepTime());
		assertEquals(COOK_TIME, recipe.getCookTime());
		assertEquals(SERVINGS, recipe.getServings());
		assertEquals(SOURCE, recipe.getSource());
		assertEquals(URL, recipe.getUrl());
		assertEquals(DIRECTIONS, recipe.getDirections());
		assertEquals(DIFFICULTY, recipe.getDifficulty());
		assertEquals(NOTES_ID, recipe.getNotes().getId());
		assertEquals(3, recipe.getIngredients().size());
		assertEquals(2, recipe.getCategories().size());
		

	
	}

}
