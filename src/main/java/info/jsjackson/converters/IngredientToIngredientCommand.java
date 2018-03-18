/**
 * 
 */
package info.jsjackson.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import info.jsjackson.commands.IngredientCommand;
import info.jsjackson.domain.Ingredient;
import info.jsjackson.domain.UnitOfMeasure;
import lombok.Synchronized;

/**
 * @author josan
 *
 */
@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

	private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

	public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
		this.uomConverter = uomConverter;
	}

	
	@Synchronized
	@Nullable
	@Override
	public IngredientCommand convert(Ingredient ingredient) {

		if (ingredient == null) {
			return null;
		}
		
		final IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(ingredient.getId());
		if (ingredient.getRecipe() != null) {
			ingredientCommand.setRecipeId(ingredient.getRecipe().getId());
		}
		ingredientCommand.setDescription(ingredient.getDescription());
		ingredientCommand.setAmount(ingredient.getAmount());
		ingredientCommand.setUnitOfMeasure(uomConverter.convert(ingredient.getUom()));
		
		return ingredientCommand;
	}

}
