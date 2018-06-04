/**
 * 
 */
package info.jsjackson.repositories.reactive;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import info.jsjackson.domain.Category;

/**
 * @author jsjackson
 *
 */
@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryIT {

	@Autowired
	CategoryReactiveRepository categoryReactiveRepository;

	
	@Before
	public void setUp() throws Exception {
		categoryReactiveRepository.deleteAll().block();
	}

	@Test
	public void testSaveCategory() {
		Category category = new Category();
		category.setDescription("Foo");

		categoryReactiveRepository.save(category).block();
		
		Long count = categoryReactiveRepository.count().block();
		
		assertEquals(Long.valueOf(1L), count);
	
	
	}
	
	@Test
	public void testFindByDescription() {
		Category category = new Category();
		category.setDescription("Foo");

		categoryReactiveRepository.save(category).then().block();
		
		Category fetchedCategory = categoryReactiveRepository.findByDescription("Foo").block();
		
		assertEquals("Foo", fetchedCategory.getDescription());
		assertNotNull(fetchedCategory.getId());
	
	
	}

}
