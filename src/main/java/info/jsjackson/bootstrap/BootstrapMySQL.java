package info.jsjackson.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import info.jsjackson.domain.Category;
import info.jsjackson.domain.UnitOfMeasure;
import info.jsjackson.repositories.CategoryRepository;
import info.jsjackson.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile({"dev", "prod"})
public class BootstrapMySQL implements ApplicationListener<ContextRefreshedEvent> {

	private final CategoryRepository categoryRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;
	
	public BootstrapMySQL(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
		super();
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

		if (categoryRepository.count() == 0L) {
			log.debug("Loading Categories");
			loadCategories();
		}
		if (unitOfMeasureRepository.count() == 0L) {
			log.debug("Loading UOMs");
			loadUom();
		}
		
	}
	
	
	private void loadCategories() {
		Category american = new Category();
		american.setDescription("American");
		categoryRepository.save(american);
		
		Category italian = new Category();
		italian.setDescription("Italian");
		categoryRepository.save(italian);
		
		Category mexican = new Category();
		mexican.setDescription("Mexican");
		categoryRepository.save(mexican);
		
		Category fastFood = new Category();
		fastFood.setDescription("Fast Food");
		categoryRepository.save(fastFood);
		
	}
	
	private void loadUom() {
		UnitOfMeasure teaspoon = new UnitOfMeasure();
		teaspoon.setDescription("Teaspoon");
		unitOfMeasureRepository.save(teaspoon);
		
		UnitOfMeasure tablespoon = new UnitOfMeasure();
		tablespoon.setDescription("Teaspoon");
		unitOfMeasureRepository.save(tablespoon);
		
		UnitOfMeasure cup = new UnitOfMeasure();
		cup.setDescription("Teaspoon");
		unitOfMeasureRepository.save(cup);
		
		UnitOfMeasure pinch = new UnitOfMeasure();
		pinch.setDescription("Teaspoon");
		unitOfMeasureRepository.save(pinch);
		
		UnitOfMeasure ounce = new UnitOfMeasure();
		ounce.setDescription("Teaspoon");
		unitOfMeasureRepository.save(ounce);
		
		UnitOfMeasure each = new UnitOfMeasure();
		each.setDescription("Teaspoon");
		unitOfMeasureRepository.save(each);
		
		UnitOfMeasure pint = new UnitOfMeasure();
		pint.setDescription("Teaspoon");
		unitOfMeasureRepository.save(pint);
		
		UnitOfMeasure dash = new UnitOfMeasure();
		dash.setDescription("Teaspoon");
		unitOfMeasureRepository.save(dash);
		
		
		
	}

}
