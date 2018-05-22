/**
 * 
 */
package info.jsjackson.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import info.jsjackson.domain.Category;

/**
 * @author josan
 *
 */
public interface CategoryRepository extends CrudRepository<Category, String> {

	Optional<Category> findByDescription(String description);
}
