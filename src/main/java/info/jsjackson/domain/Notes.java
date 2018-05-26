/**
 * 
 */
package info.jsjackson.domain;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

/**
 * @author josan
 *
 */
@Getter
@Setter
public class Notes {

	@Id
	private String id;
	
	private String recipeNotes;
	
	
}
