/**
 * 
 */
package info.jsjackson.services;

import java.util.List;
import java.util.Set;

/**
 * @author josan
 *
 */
public interface CRUDService<T> {
	
	T getById(Long id);
	
	T saveOrUpdate(T domainObject);
	
	void delete(Long id);

}
