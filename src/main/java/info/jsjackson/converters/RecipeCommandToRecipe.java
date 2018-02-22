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
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

	private final CategoryCommandToCategory categoryConverter;
	private final IngredientCommandToIngredient ingredientConverter;
	private final NotesCommandToNotes notesConverter;

	public RecipeCommandToRecipe(CategoryCommandToCategory categoryConverter,
			IngredientCommandToIngredient ingredientConverter, NotesCommandToNotes notesConverter) {
		this.categoryConverter = categoryConverter;
		this.ingredientConverter = ingredientConverter;
		this.notesConverter = notesConverter;
	}

	@Synchronized
	@Nullable
	@Override
	public Recipe convert(RecipeCommand command) {

		if (command == null) {
			return null;
		}
		
		final Recipe recipe = new Recipe();
		recipe.setId(command.getId());
		recipe.setDescription(command.getDescription());
		recipe.setPrepTime(command.getPrepTime());
		recipe.setCookTime(command.getCookTime());
		recipe.setServings(command.getServings());
		recipe.setSource(command.getSource());
		recipe.setUrl(command.getUrl());
		recipe.setDifficulty(command.getDifficulty());
		recipe.setDirections(command.getDirections());
		recipe.setNotes(notesConverter.convert(command.getNotes()));
		
		if (command.getCategories() != null && command.getCategories().size() > 0) {
			command.getCategories().forEach(category -> recipe.getCategories().add(categoryConverter.convert(category)));
		}

		if (command.getIngredients() != null && command.getIngredients().size() > 0) {
			command.getIngredients().forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
		}
		
		return recipe;
	}

}
