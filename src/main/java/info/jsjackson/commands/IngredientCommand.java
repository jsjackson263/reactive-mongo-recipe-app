/**
 * 
 */
package info.jsjackson.commands;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
	
	@NotBlank
	private String description;
	
	@NotNull
	@Min(1)
	private BigDecimal amount;
	
	@NotNull
	private UnitOfMeasureCommand unitOfMeasure;
	
	
}
