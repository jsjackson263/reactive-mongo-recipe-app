/**
 * 
 */
package info.jsjackson.services;

import info.jsjackson.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

/**
 * @author josan 
 *
 */
public interface UnitOfMeasureService {

	Flux<UnitOfMeasureCommand> listAllUoms();
}
