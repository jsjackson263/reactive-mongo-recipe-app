/**
 * 
 */
package info.jsjackson.converters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import info.jsjackson.commands.CategoryCommand;
import info.jsjackson.domain.Category;

/**
 * @author josan 
 *
 */
public class CategoryToCategoryCommandTest {

	static final String ID = new String("1");
	static final String DESCRIPTION = "description";
	
	CategoryToCategoryCommand converter;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		converter = new CategoryToCategoryCommand();
	}

	@Test
	public void testNullObjectConversion() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObjectConversion() throws Exception {
		assertNotNull(converter.convert(new Category()));
	}
	
	@Test
	public void testConvert() throws Exception {

		//Given
		Category category = new Category();
		category.setId(ID);
		category.setDescription(DESCRIPTION);
		
		//When
		CategoryCommand command = converter.convert(category);
		
		//Then
		assertNotNull(command);
		assertEquals(ID, command.getId());
		assertEquals(DESCRIPTION, command.getDescription());
		
		
	}

}
