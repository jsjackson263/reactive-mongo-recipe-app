/**
 * 
 */
package info.jsjackson.domain;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author josan
 *
 */
@Getter
@Setter
@ToString
public class Notes {

	@Id
	private String id;
	
	private String recipeNotes;
	
	
}
