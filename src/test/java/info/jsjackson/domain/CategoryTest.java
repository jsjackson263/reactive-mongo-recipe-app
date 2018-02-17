package info.jsjackson.domain;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class CategoryTest {

	Category category;
	
	@Before
	public void setUP() {
		category = new Category();
	}
	
	@Test
	public void testGetId() {
		Long idValue = 4l;
		
		category.setId(idValue);
		assertEquals(idValue, category.getId());
	}

	//@Test
	@Ignore
	public void testGetDescription() {
		fail("Not yet implemented"); // TODO
	}

	//@Test
	@Ignore
	public void testGetRecipes() {
		fail("Not yet implemented"); // TODO
	}

}
