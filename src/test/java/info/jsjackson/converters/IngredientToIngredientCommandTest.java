/**
 * 
 */
package info.jsjackson.converters;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import info.jsjackson.commands.IngredientCommand;
import info.jsjackson.domain.Ingredient;
import info.jsjackson.domain.Recipe;
import info.jsjackson.domain.UnitOfMeasure;

/**
 * @author josan
 *
 */
public class IngredientToIngredientCommandTest {

	static final String ID = new String("1");
	static final String DESCRIPTION = "description";
	static final BigDecimal AMOUNT = new BigDecimal(10.50);
	static final String UOM_ID = new String("2");
	static final Recipe RECIPE = new Recipe();

	IngredientToIngredientCommand converter;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
	}

	@Test
	public void testNullObjectConversion() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	public void testEmptyObjectConversion() throws Exception {
		assertNotNull(converter.convert(new Ingredient()));
	}

	@Test
	public void testConvertWithUom() throws Exception {

		// Given
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ID);
		ingredient.setDescription(DESCRIPTION);
		ingredient.setAmount(AMOUNT);
		ingredient.setRecipe(RECIPE);

		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(UOM_ID);
		uom.setDescription(DESCRIPTION);
		ingredient.setUom(uom);

		// When
		IngredientCommand command = converter.convert(ingredient);

		// Then
		assertNotNull(command);
		assertEquals(ID, command.getId());
		assertEquals(DESCRIPTION, command.getDescription());
		assertEquals(AMOUNT, command.getAmount());
		assertEquals(UOM_ID, command.getUnitOfMeasure().getId());

	}

	@Test
	public void testWithNullUom() throws Exception {
		// Given
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ID);
		ingredient.setDescription(DESCRIPTION);
		ingredient.setAmount(AMOUNT);
		ingredient.setRecipe(RECIPE);

		//When
		IngredientCommand command = converter.convert(ingredient);
		
		//Then
		assertNotNull(command);
		assertNull(command.getUnitOfMeasure());
		assertEquals(ID, command.getId());
		assertEquals(DESCRIPTION, command.getDescription());
		assertEquals(AMOUNT, command.getAmount());
		
	}

}
