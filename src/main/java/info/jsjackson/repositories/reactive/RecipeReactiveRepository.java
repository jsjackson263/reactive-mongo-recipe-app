/**
 * 
 */
package info.jsjackson.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import info.jsjackson.domain.Recipe;

/**
 * @author jsjackson
 *
 */
public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {

}
