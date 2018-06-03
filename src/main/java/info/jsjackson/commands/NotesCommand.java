/**
 * 
 */
package info.jsjackson.commands;

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
public class NotesCommand {

	private String id;
	private String recipeNotes;
	
}
