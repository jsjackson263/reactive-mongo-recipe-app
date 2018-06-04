/**
 * 
 */
package info.jsjackson.domain;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document
public class Category {

	@Id
	private String id;
	
	private String description;
	
	private Set<Recipe> recipes;
	
}
