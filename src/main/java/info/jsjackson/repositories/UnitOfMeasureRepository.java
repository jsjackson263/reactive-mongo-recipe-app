/**
 * 
 */
package info.jsjackson.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import info.jsjackson.domain.UnitOfMeasure;

/**
 * @author josan
 *
 */
public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {
	Optional<UnitOfMeasure> findByDescription(String description); 
}
