/**
 * 
 */
package info.jsjackson.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import info.jsjackson.domain.Category;
import reactor.core.publisher.Mono;

/**
 * @author jsjackson
 *
 */
public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String> {
	
	Mono<Category> findByDescription(String description);

}
