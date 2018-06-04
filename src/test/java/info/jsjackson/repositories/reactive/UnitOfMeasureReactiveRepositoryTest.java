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

import info.jsjackson.domain.UnitOfMeasure;

/**
 * @author jsjackson
 *
 */
@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {


	public static final String EACH = "Each";
	
	@Autowired
	UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
	
	@Before
	public void setUp() throws Exception {
		unitOfMeasureReactiveRepository.deleteAll().block();
	}

	@Test
	public void testSaveUnitOfMeasure() throws Exception {
		
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setDescription(EACH);
		
		unitOfMeasureReactiveRepository.save(unitOfMeasure).block();
		
		assertEquals(Long.valueOf(1L), unitOfMeasureReactiveRepository.count().block());
		
	
	}
	
	@Test
	public void testFindByDescription() throws Exception {
		
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setDescription(EACH);
		
		unitOfMeasureReactiveRepository.save(unitOfMeasure).block();
		
		UnitOfMeasure fetchedUnitOfMeasure = unitOfMeasureReactiveRepository.findByDescription(EACH).block();
		
		assertEquals(EACH, fetchedUnitOfMeasure.getDescription());
		
		
	}

	
}
