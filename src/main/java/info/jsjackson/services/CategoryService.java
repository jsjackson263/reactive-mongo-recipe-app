/**
 * 
 */
package info.jsjackson.services;

import java.util.Optional;

import info.jsjackson.domain.Category;

/**
 * @author josan
 *
 */
public interface CategoryService extends CRUDService<Category> {

	Optional<Category> findByDescription(String description);
}
