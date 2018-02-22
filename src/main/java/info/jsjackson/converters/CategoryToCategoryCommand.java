/**
 * 
 */
package info.jsjackson.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import info.jsjackson.commands.CategoryCommand;
import info.jsjackson.domain.Category;
import lombok.Synchronized;

/**
 * @author josan 
 *
 */
@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

	@Synchronized
	@Nullable
	@Override
	public CategoryCommand convert(Category category) {

		if (category == null) {
			return null;
		}
		
		final CategoryCommand command = new CategoryCommand();
		command.setId(category.getId());
		command.setDescription(category.getDescription());
		return command;
	}

}
