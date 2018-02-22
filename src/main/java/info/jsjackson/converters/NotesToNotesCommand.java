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
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

	@Synchronized
	@Nullable
	@Override
	public NotesCommand convert(Notes notes) {

		if (notes == null) {
			return null;
		}
		
		final NotesCommand command = new NotesCommand();
		command.setId(notes.getId());
		command.setRecipeNotes(notes.getRecipeNotes());
		
		return command;
	}

}
