package info.jsjackson.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import info.jsjackson.commands.IngredientCommand;
import info.jsjackson.commands.RecipeCommand;
import info.jsjackson.commands.UnitOfMeasureCommand;
import info.jsjackson.services.IngredientService;
import info.jsjackson.services.RecipeService;
import info.jsjackson.services.UnitOfMeasureService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class IngredientControllerTest {

	@Mock
	RecipeService recipeService;
	
	@Mock
	UnitOfMeasureService unitOfMeasureService;
	
	@Mock
	IngredientService ingredientService;
	
	IngredientController controller;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new IngredientController(
				recipeService, ingredientService, unitOfMeasureService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testListIngredients() throws Exception {

		//Given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId("1");
		
		//When
		when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(recipeCommand));
		
		//Then
		mockMvc.perform(get("/recipe/1/ingredients"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/ingredient/list"))
		.andExpect(model().attributeExists("recipe"))
		.andReturn();
		
	}
	
	@Test
	public void testShowIngredient() throws Exception {
		
		//Given
		IngredientCommand command = new IngredientCommand();
		command.setId("1");
		
		//When
		when(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString())).thenReturn(Mono.just(command));
		
		
		//Then
		mockMvc.perform(get("/recipe/1/ingredient/2/show"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/ingredient/show"))
		.andReturn();
		
	}
	
	@Test
	public void testNewIngredientForm() throws Exception {
		
		//Given
		RecipeCommand command = new RecipeCommand();
		command.setId("1");
		
		//When
		when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(command));
		when(unitOfMeasureService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));
		
		//Then
		mockMvc.perform(get("/recipe/1/ingredient/new"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/ingredient/ingredientform"))
		.andExpect(model().attributeExists("ingredient"))
		.andExpect(model().attributeExists("uomList"))
		.andReturn();
				
		verify(recipeService, times(1)).findCommandById(anyString());
		
		
	}
	
	
	@Test
	public void testUpdateIngredientForm() throws Exception {
		
		//Given
		IngredientCommand command = new IngredientCommand();
		command.setId("3");
		command.setAmount(new BigDecimal(1.0));
		command.setDescription("Description");
		command.setRecipeId("1");
		//command.setUnitOfMeasure(new UnitOfMeasure());
		
		//When
		when(ingredientService.findByRecipeIdAndIngredientId(anyString(),  anyString())).thenReturn(Mono.just(command));
		when(unitOfMeasureService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));
		
		//Then
		mockMvc.perform(get("/recipe/1/ingredient/3/update"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/ingredient/ingredientform"))
		.andExpect(model().attributeExists("ingredient"))
		.andExpect(model().attributeExists("uomList"))
		.andReturn();
		
	}
	
	 @Test
	    public void testSaveOrUpdate() throws Exception {
	        //given
	        IngredientCommand command = new IngredientCommand();
	        command.setId("3");
	        command.setRecipeId("2");

	        //when
	        when(ingredientService.saveIngredientCommand(any())).thenReturn(Mono.just(command));

	        //then
	        mockMvc.perform(post("/recipe/2/ingredient")
	                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                .param("id", "")
	                .param("description", "some string")
	        )
	                .andExpect(status().is3xxRedirection())
	                .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));

	    }
	 
	 @Test
	 public void testDeleteIngredient() throws Exception {
			
		//When
		 when(ingredientService.deleteById(anyString(),  anyString())).thenReturn(Mono.empty());
		 
		//Then
		mockMvc.perform(get("/recipe/2/ingredient/3/delete"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/recipe/2/ingredients"))
		.andReturn();

		verify(ingredientService, times(1)).deleteById(anyString(), anyString());
	}

}
