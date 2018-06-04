/**
 * 
 */
package info.jsjackson.domain;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

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
public class Ingredient {

	//ingredient is a nested list inside Recipe document, so doesn't get an id value (even if annotated). 
	private String id = UUID.randomUUID().toString();
	
	private String description;
	private BigDecimal amount;
	private UnitOfMeasure uom;
	
	 public Ingredient() {
	 }

	    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
	        this.description = description;
	        this.amount = amount;
	        this.uom = uom;
	    }

	    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) {
	        this.description = description;
	        this.amount = amount;
	        this.uom = uom;
	        //this.recipe = recipe;
	    }


	
	
	
	
}
