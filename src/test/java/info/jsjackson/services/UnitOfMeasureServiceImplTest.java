package info.jsjackson.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import info.jsjackson.commands.UnitOfMeasureCommand;
import info.jsjackson.converters.UnitOfMeasureToUnitOfMeasureCommand;
import info.jsjackson.domain.UnitOfMeasure;
import info.jsjackson.repositories.reactive.UnitOfMeasureReactiveRepository;
import reactor.core.publisher.Flux;

public class UnitOfMeasureServiceImplTest {

	@Mock
	UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
	
	UnitOfMeasureService unitOfMeasureService;
	UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureReactiveRepository, unitOfMeasureToUnitOfMeasureCommand);
	}

	@Test
	public void testListAllUoms() {
		
		//Given
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId("1");
		uom1.setDescription("UOM Description1");
		
		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId("2");
		uom2.setDescription("UOM Description2");
		
		UnitOfMeasure uom3 = new UnitOfMeasure();
		uom3.setId("3");
		uom3.setDescription("UOM Description3");
		
		Set<UnitOfMeasure> uomList = new HashSet<>();
		uomList.add(uom1);
		uomList.add(uom2);
		uomList.add(uom3);
		
		when(unitOfMeasureReactiveRepository.findAll()).thenReturn(Flux.just(uom1, uom2, uom3));
		
		
		//When
		List<UnitOfMeasureCommand> uomCommandList = unitOfMeasureService.listAllUoms().collectList().block();
		
		
		//Then
		assertEquals(3, uomCommandList.size());
		verify(unitOfMeasureReactiveRepository, times(1)).findAll();
		
	
	}

}
