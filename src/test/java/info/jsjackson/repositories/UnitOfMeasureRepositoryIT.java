package info.jsjackson.repositories;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import info.jsjackson.bootstrap.RecipeBootstrap;
import info.jsjackson.domain.UnitOfMeasure;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureRepositoryIT {

	@Autowired
	UnitOfMeasureRepository unitOfMeasureRepository;
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Before
	public void setUp() throws Exception {
		recipeRepository.deleteAll();
		unitOfMeasureRepository.deleteAll();
		categoryRepository.deleteAll();
		
		
		RecipeBootstrap recipeBootstrap = new RecipeBootstrap(recipeRepository, categoryRepository, unitOfMeasureRepository);
		recipeBootstrap.onApplicationEvent(null);
		
	}

	@Test
	public void testFindByDescriptionTeaspoon() throws Exception {
		Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
		assertEquals("Teaspoon", uomOptional.get().getDescription());
	}

	@Test
	public void testFindByDescriptionCup() throws Exception {
		Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Cup");
		assertEquals("Cup", uomOptional.get().getDescription());
	}
	
	
}
