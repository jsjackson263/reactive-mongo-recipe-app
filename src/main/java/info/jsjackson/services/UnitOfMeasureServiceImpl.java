/**
 * 
 */
package info.jsjackson.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import info.jsjackson.commands.UnitOfMeasureCommand;
import info.jsjackson.converters.UnitOfMeasureToUnitOfMeasureCommand;
import info.jsjackson.domain.UnitOfMeasure;
import info.jsjackson.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author josan 
 *
 */
@Slf4j
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	private final UnitOfMeasureRepository unitOfMeasureRepository;
	private final UnitOfMeasureToUnitOfMeasureCommand  unitOfMeasureToUnitOfMeasureCommand;

	public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
			UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
	}


	@Override
	public Set<UnitOfMeasureCommand> listAllUoms() {

		Set<UnitOfMeasureCommand> uomCommandList = new HashSet<>();
		
		Iterable<UnitOfMeasure> uomSet = unitOfMeasureRepository.findAll();
		uomSet.iterator().forEachRemaining((uom) -> uomCommandList.add(unitOfMeasureToUnitOfMeasureCommand.convert(uom)));  //TODO: Hurray!!
		
		
		/*
		 * John's implementation - too crisp for me
		 * 
		 * return StreamSupport.stream(unitOfMeasureRepository.findAll()
                .spliterator(), false)
                .map(unitOfMeasureToUnitOfMeasureCommand::convert)
                .collect(Collectors.toSet());
                
          StreamSupport - a convenient way to swap an iterator to something we can stream against.
          spliterator - gives us a java stream
          map function - converts the domain objects to command objects
          collect - collects the objects into a set
          
		 */
		
		return uomCommandList;
	}

}
