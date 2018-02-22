/**
 * 
 */
package info.jsjackson.converters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import info.jsjackson.commands.RecipeCommand;
import info.jsjackson.domain.Category;
import info.jsjackson.domain.Difficulty;
import info.jsjackson.domain.Ingredient;
import info.jsjackson.domain.Notes;
import info.jsjackson.domain.Recipe;

/**
 * @author josan 
 *
 */
public class RecipeToRecipeCommandTest {

	RecipeToRecipeCommand converter;
	
	static final Long ID = new Long(1L);
	static final String DESCRIPTION = "My Recipe";
	static final Integer PREP_TIME = Integer.valueOf("10");
	static final Integer COOK_TIME = Integer.valueOf("20");
	static final Integer SERVINGS = Integer.valueOf("5");
	static final String SOURCE = "source";
	static final String URL = "www.example.com";
	static final String DIRECTIONS = "Directions";
	static final Difficulty DIFFICULTY = Difficulty.EASY;
	static final Long INGRED_ID_1 = 1L;
	static final Long INGRED_ID_2 = 2L;
	static final Long INGRED_ID_3 = 3L;
	static final Long NOTES_ID = 20L;
	static final Long CAT_ID_1 = 1L;
	static final Long CAT_ID_2 = 2L;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		converter = new RecipeToRecipeCommand(
				new CategoryToCategoryCommand(),
				new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
				new NotesToNotesCommand()); 
	}

	
	@Test
	public void testNullObjectConversion() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObjectConversion() throws Exception {
		assertNotNull(converter.convert(new Recipe()));
	}
	@Test
	public void testConvert()  throws Exception{

		//Given
		Recipe recipe = new Recipe();
		recipe.setId(ID);
		recipe.setDescription(DESCRIPTION);
		recipe.setPrepTime(PREP_TIME);
		recipe.setCookTime(COOK_TIME);
		recipe.setServings(SERVINGS);
		recipe.setSource(SOURCE);
		recipe.setUrl(URL);
		recipe.setDirections(DIRECTIONS);
		recipe.setDifficulty(DIFFICULTY);
		
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(INGRED_ID_1);
		recipe.getIngredients().add(ingredient1);
		
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(INGRED_ID_2);
		recipe.getIngredients().add(ingredient2);
		
		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId(INGRED_ID_3);
		recipe.getIngredients().add(ingredient3);
		
		Notes notes = new Notes();
		notes.setId(NOTES_ID);
		recipe.setNotes(notes);
		
		Category category1 = new Category();
		category1.setId(CAT_ID_1);
		recipe.getCategories().add(category1);
		
		Category category2 = new Category();
		category2.setId(CAT_ID_2);
		recipe.getCategories().add(category2);
		
		
		//When
		RecipeCommand command = converter.convert(recipe);
		
		//Then
		assertNotNull(command);
		assertEquals(ID, command.getId());
		assertEquals(DESCRIPTION, command.getDescription());
		assertEquals(PREP_TIME, command.getPrepTime());
		assertEquals(COOK_TIME, command.getCookTime());
		assertEquals(SERVINGS, command.getServings());
		assertEquals(SOURCE, command.getSource());
		assertEquals(URL, command.getUrl());
		assertEquals(DIRECTIONS, command.getDirections());
		assertEquals(DIFFICULTY, command.getDifficulty());
		
		assertEquals(NOTES_ID, command.getNotes().getId());
		assertEquals(3, command.getIngredients().size());
		assertEquals(2, command.getCategories().size());
		
	
	}

}
