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
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

	@Synchronized
	@Nullable
	@Override
	public Category convert(CategoryCommand command) {

		if (command == null) {
			return null;
		}
		
		final Category category = new Category();
		category.setId(command.getId());
		category.setDescription(command.getDescription());
		
		return  category;
	}

}
