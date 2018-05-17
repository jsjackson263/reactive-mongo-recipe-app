/**
 * 
 */
package info.jsjackson.domain;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * @author josan
 *
 */
@Getter
@Setter
public class Category {

	private String id;
	
	private String description;
	
	private Set<Recipe> recipes;
	
	
	
	
	
	
}
