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
public class CategoryCommandToCategoryTest {

	static final String ID = new String("1");
	static final String DESCRIPTION = "description"; 
	
	CategoryCommandToCategory converter;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		converter = new CategoryCommandToCategory();
	}

	@Test
	public void testNullObjectConversion() throws Exception {
		assertNull(converter.convert(null));
		
	}
	
	@Test
	public void testEmptyObjectConversion()  throws Exception {
		assertNotNull(converter.convert(new CategoryCommand()));
	}
	
	@Test
	public void testConvert() throws Exception {
		
		//Given
		CategoryCommand command = new CategoryCommand();
		command.setId(ID);
		command.setDescription(DESCRIPTION);
		
		//When
		Category category = converter.convert(command);
		
		//Then
		assertNotNull(category);
		assertEquals(ID, category.getId());
		assertEquals(DESCRIPTION, command.getDescription());
		
				
				
	}

}
