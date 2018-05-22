/**
 * 
 */
package info.jsjackson.repositories;

import org.springframework.data.repository.CrudRepository;

import info.jsjackson.domain.Recipe;

/**
 * @author josan
 *
 */
public interface RecipeRepository extends CrudRepository<Recipe, String> {

}
