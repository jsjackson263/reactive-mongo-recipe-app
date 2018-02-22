/**
 * 
 */
package info.jsjackson.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import info.jsjackson.commands.UnitOfMeasureCommand;
import info.jsjackson.domain.UnitOfMeasure;
import lombok.Synchronized;

/**
 * @author josan 
 *
 */
@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

	@Synchronized
	@Nullable
	@Override
	public UnitOfMeasureCommand convert(UnitOfMeasure uom) {

		if (uom == null) {
			return null;
		}
		
		final UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();
		uomc.setId(uom.getId());
		uomc.setDescription(uom.getDescription());
		return uomc;
		
	}

}
