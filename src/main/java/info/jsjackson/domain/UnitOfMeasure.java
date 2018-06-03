/**
 * 
 */
package info.jsjackson.domain;

import org.springframework.data.annotation.Id;
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
public class UnitOfMeasure {

	@Id
	private String id;
	
	private String description;

	
}
