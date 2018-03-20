/**
 * 
 */
package info.jsjackson.services;

import java.util.Set;

import info.jsjackson.commands.UnitOfMeasureCommand;

/**
 * @author josan 
 *
 */
public interface UnitOfMeasureService {

	Set<UnitOfMeasureCommand> listAllUoms();
}
