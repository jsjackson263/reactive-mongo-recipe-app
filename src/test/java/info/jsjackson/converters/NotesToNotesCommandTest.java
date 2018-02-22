/**
 * 
 */
package info.jsjackson.converters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import info.jsjackson.commands.NotesCommand;
import info.jsjackson.domain.Notes;

/**
 * @author josan 
 *
 */
public class NotesToNotesCommandTest {

	static final Long ID = new Long(1L);
	static final String  RECIPE_NOTES = "recipe notes";
	
	NotesToNotesCommand converter;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		converter = new NotesToNotesCommand();
	}

	@Test
	public void testNullObjectConversion() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObjectConversion() {
		assertNotNull(converter.convert(new Notes()));
	}
	
	@Test
	public void testConvert() throws Exception {

		//Given
		Notes notes = new Notes();
		notes.setId(ID);
		notes.setRecipeNotes(RECIPE_NOTES);
		
		
		//When
		NotesCommand command = converter.convert(notes);
		
		
		//Then
		assertNotNull(command);
		assertEquals(ID, command.getId());
		assertEquals(RECIPE_NOTES, command.getRecipeNotes());
		
		
	}

}
