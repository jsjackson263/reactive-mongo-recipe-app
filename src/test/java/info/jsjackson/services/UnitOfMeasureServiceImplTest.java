package info.jsjackson.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import info.jsjackson.commands.UnitOfMeasureCommand;
import info.jsjackson.converters.UnitOfMeasureToUnitOfMeasureCommand;
import info.jsjackson.domain.UnitOfMeasure;
import info.jsjackson.repositories.UnitOfMeasureRepository;

public class UnitOfMeasureServiceImplTest {

	@Mock
	UnitOfMeasureRepository repository;
	
	UnitOfMeasureService unitOfMeasureService;
	UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
		unitOfMeasureService = new UnitOfMeasureServiceImpl(repository, unitOfMeasureToUnitOfMeasureCommand);
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
		
		//When
		when(repository.findAll()).thenReturn(uomList);
		
		Set<UnitOfMeasureCommand> uomCommandList = unitOfMeasureService.listAllUoms();
		
		
		//Then
		assertEquals(3, uomCommandList.size());
		verify(repository, times(1)).findAll();
		
	
	}

}
