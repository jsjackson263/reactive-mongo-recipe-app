/**
 * 
 */
package info.jsjackson.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import info.jsjackson.commands.NotesCommand;
import info.jsjackson.domain.Notes;
import lombok.Synchronized;

/**
 * @author josan 
 *
 */
@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

	@Synchronized
	@Nullable
	@Override
	public Notes convert(NotesCommand command) {
		if (command == null) {
			return null;
		}
		
		final Notes notes = new Notes();
		notes.setId(command.getId());
		notes.setRecipeNotes(command.getRecipeNotes());
		
		return notes;
	}

}
