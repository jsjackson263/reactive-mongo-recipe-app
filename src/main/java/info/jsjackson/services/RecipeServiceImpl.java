/**
 * 
 */
package info.jsjackson.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import info.jsjackson.domain.Recipe;
import info.jsjackson.repositories.CategoryRepository;
import info.jsjackson.repositories.RecipeRepository;
import info.jsjackson.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author josan
 *
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	
	public RecipeServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	public Set<Recipe> getRecipes() {
		Set<Recipe> recipes = new HashSet<Recipe>();
		recipeRepository.findAll().forEach(recipes:: add);
		//log.debug("LOGGER - Recipe Rescription: " + recipes.iterator().next().getDescription());
		return recipes;
	}
	

	/* (non-Javadoc)
	 * @see info.jsjackson.services.CRUDService#getById(java.lang.Long)
	 */
	@Override
	public Recipe getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see info.jsjackson.services.CRUDService#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public Recipe saveOrUpdate(Recipe domainObject) {
		return recipeRepository.save(domainObject);
	}

	/* (non-Javadoc)
	 * @see info.jsjackson.services.CRUDService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	

}
