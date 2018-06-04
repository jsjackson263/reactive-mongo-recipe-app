/**
 * 
 */
package info.jsjackson.repositories.reactive;

import java.util.Optional;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import info.jsjackson.domain.UnitOfMeasure;
import reactor.core.publisher.Mono;

/**
 * @author jsjackson
 *
 */
public interface UnitOfMeasureReactiveRepository extends ReactiveMongoRepository<UnitOfMeasure, String> {

	Mono<UnitOfMeasure> findByDescription(String description); 
}
