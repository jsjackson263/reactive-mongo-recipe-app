/**
 * 
 */
package info.jsjackson.converters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import info.jsjackson.commands.UnitOfMeasureCommand;
import info.jsjackson.domain.UnitOfMeasure;

/**
 * @author josan 
 *
 */
public class UnitOfMeasureToUnitOfMeasureCommandTest {

	static final String LONG_VALUE = new String("2");
	static final String DESCRIPTION = "uom description";
	
	UnitOfMeasureToUnitOfMeasureCommand converter;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		converter = new UnitOfMeasureToUnitOfMeasureCommand();
	}

	@Test
	public void testNullObjectConversion() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObjectConversion() {
		assertNotNull(converter.convert(new UnitOfMeasure()));
	}
	
	@Test
	public void testConvert() throws Exception {
		
		//Given
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(LONG_VALUE);
		uom.setDescription(DESCRIPTION);
		
		//When
		UnitOfMeasureCommand uomc = converter.convert(uom);
		
		//Then
		assertNotNull(uomc);
		assertEquals(LONG_VALUE, uomc.getId());
		assertEquals(DESCRIPTION, uomc.getDescription());

	}

}
