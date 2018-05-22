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
public class NotesCommandToNotesTest {

	static final String ID = new String("1");
	static final String RECIPE_NOTES = "recipe notes";
	
	NotesCommandToNotes converter;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		converter = new NotesCommandToNotes();
	}

	@Test
	public void testNullObjectConversion() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObjectConversion() throws Exception {
		assertNotNull(converter.convert(new NotesCommand()));
	}
	
	@Test
	public void testConvert() throws Exception {
		
		//Given
		NotesCommand command = new NotesCommand();
		command.setId(ID);
		command.setRecipeNotes(RECIPE_NOTES);
		
		
		//When
		Notes notes = converter.convert(command);
		
		//Then
		assertNotNull(notes);
		assertEquals(ID, notes.getId());
		assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
		
	}

}
