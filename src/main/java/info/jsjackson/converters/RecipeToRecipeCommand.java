/**
 * 
 */
package info.jsjackson.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import info.jsjackson.commands.RecipeCommand;
import info.jsjackson.domain.Recipe;
import lombok.Synchronized;

/**
 * @author josan 
 *
 */
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

	private final CategoryToCategoryCommand categoryConverter;
	private final IngredientToIngredientCommand ingredientConverter;
	private final NotesToNotesCommand notesConverter;
	
	public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConverter,
			IngredientToIngredientCommand ingredientConverter, NotesToNotesCommand notesConverter) {
		this.categoryConverter = categoryConverter;
		this.ingredientConverter = ingredientConverter;
		this.notesConverter = notesConverter;
	}


	@Synchronized
	@Nullable
	@Override
	public RecipeCommand convert(Recipe recipe) {

		if (recipe == null) {
			return null;
		}
		
		final RecipeCommand command = new RecipeCommand();
		command.setId(recipe.getId());
		command.setDescription(recipe.getDescription());
		command.setPrepTime(recipe.getPrepTime());
		command.setCookTime(recipe.getCookTime());
		command.setServings(recipe.getServings());
		command.setSource(recipe.getSource());
		command.setUrl(recipe.getUrl());
		command.setDirections(recipe.getDirections());
		command.setDifficulty(recipe.getDifficulty());
		
		command.setNotes(notesConverter.convert(recipe.getNotes()));

		if (recipe.getIngredients() != null && recipe.getIngredients().size() > 0) {
			recipe.getIngredients().forEach(ingredient -> command.getIngredients().add(ingredientConverter.convert(ingredient)));
		}
		
		if (recipe.getCategories() != null && recipe.getCategories().size() > 0) {
			recipe.getCategories().forEach(category -> command.getCategories().add(categoryConverter.convert(category)));
		}
		
		return command;
	}

}
