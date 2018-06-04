/**
 * 
 */
package info.jsjackson.services;

import org.springframework.stereotype.Service;

import info.jsjackson.commands.UnitOfMeasureCommand;
import info.jsjackson.converters.UnitOfMeasureToUnitOfMeasureCommand;
import info.jsjackson.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * @author josan 
 *
 */
@Slf4j
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
	private final UnitOfMeasureToUnitOfMeasureCommand  unitOfMeasureToUnitOfMeasureCommand;

	public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository,
			UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
		this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
		this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
	}


	@Override
	public Flux<UnitOfMeasureCommand> listAllUoms() {

		Flux<UnitOfMeasureCommand> uomCommandList = unitOfMeasureReactiveRepository
				.findAll()
				.map(unitOfMeasureToUnitOfMeasureCommand::convert);
		
		
		/*
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
