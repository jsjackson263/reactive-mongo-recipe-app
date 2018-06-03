/**
 * 
 */
package info.jsjackson.commands;

import java.math.BigDecimal;

import info.jsjackson.domain.UnitOfMeasure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author josan
 *
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class IngredientCommand {

	private String id;
	private String recipeId;
	private String description;
	private BigDecimal amount;
	private UnitOfMeasureCommand unitOfMeasure;
	
	
}
