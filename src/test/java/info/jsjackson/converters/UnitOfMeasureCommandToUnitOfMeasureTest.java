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
public class UnitOfMeasureCommandToUnitOfMeasureTest {

	static final String LONG_VALUE = new String("1");
	static final String DESCRIPTION = "description";
	
	UnitOfMeasureCommandToUnitOfMeasure converter;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		converter = new UnitOfMeasureCommandToUnitOfMeasure();
	}

	@Test
	public void testNullObjectConversion() throws Exception {
		assertNull(converter.convert(null));
		
	}
	
	@Test
	public void testEmptyObjectConversion() throws Exception {
		UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();
		UnitOfMeasure uom = converter.convert(uomc);
		assertNotNull(uom);
		
	}
	
	@Test
	public void testConvert() throws Exception {
		
		//Given
		UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();
		uomc.setId(LONG_VALUE);
		uomc.setDescription(DESCRIPTION);
		
		
		//When
		UnitOfMeasure uom = converter.convert(uomc);
		
		//Then
		assertNotNull(uom);
		assertEquals(LONG_VALUE, uom.getId());
		assertEquals(DESCRIPTION, uom.getDescription());
		
		
	}

}
