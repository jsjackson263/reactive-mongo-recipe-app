/**
 * 
 */
package info.jsjackson.converters;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import info.jsjackson.commands.IngredientCommand;
import info.jsjackson.commands.UnitOfMeasureCommand;
import info.jsjackson.domain.Ingredient;
import info.jsjackson.domain.Recipe;
import info.jsjackson.domain.UnitOfMeasure;

/**
 * @author josan
 *
 */
public class IngredientCommandToIngredientTest {

	static final String ID = new String("1");
	static final String DESCRIPTION = "description";
	static final BigDecimal AMOUNT = new BigDecimal(10.50);
	static final String UOM_ID = new String("2");
	static final Recipe RECIPE = new Recipe();

	IngredientCommandToIngredient converter;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());

	}

	@Test
	public void testNullObjectConvert() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	public void testEmptyObjectConvert() throws Exception {
		assertNotNull(converter.convert(new IngredientCommand()));
	}

	@Test
	public void testConvertWithUom() throws Exception {

		// Given
		IngredientCommand command = new IngredientCommand();
		command.setId(ID);
		command.setDescription(DESCRIPTION);
		command.setAmount(AMOUNT);

		UnitOfMeasureCommand uom = new UnitOfMeasureCommand();
		uom.setId(UOM_ID);
		uom.setDescription(DESCRIPTION);
		command.setUnitOfMeasure(uom);

		// When
		Ingredient ingredient = converter.convert(command);

		// Then
		assertNotNull(ingredient);
		assertNotNull(ingredient.getUom());
		assertEquals(ID, ingredient.getId());
		assertEquals(DESCRIPTION, ingredient.getDescription());
		assertEquals(AMOUNT, ingredient.getAmount());
		assertEquals(UOM_ID, ingredient.getUom().getId());

	}

	@Test
	public void testConvertWithNullUom() throws Exception {

		// Given
		IngredientCommand command = new IngredientCommand();
		command.setId(ID);
		command.setDescription(DESCRIPTION);
		command.setAmount(AMOUNT);

		//When
		Ingredient ingredient = converter.convert(command);
		
		
		//Then
		assertNotNull(ingredient);
		assertNull(ingredient.getUom());
		assertEquals(ID, ingredient.getId());
		assertEquals(DESCRIPTION, ingredient.getDescription());
		assertEquals(AMOUNT, ingredient.getAmount());
		
	}

}
