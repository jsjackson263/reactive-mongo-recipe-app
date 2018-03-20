package info.jsjackson.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import info.jsjackson.commands.IngredientCommand;
import info.jsjackson.commands.RecipeCommand;
import info.jsjackson.services.IngredientService;
import info.jsjackson.services.RecipeService;
import info.jsjackson.services.UnitOfMeasureService;

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
		recipeCommand.setId(1L);
		
		//When
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		
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
		command.setId(1L);
		
		//When
		when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(command);
		
		
		//Then
		mockMvc.perform(get("/recipe/1/ingredient/2/show"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/ingredient/show"))
		.andReturn();
		
	}
	
	@Test
	public void testUpdateIngredientForm() throws Exception {
		
		//Given
		IngredientCommand command = new IngredientCommand();
		command.setId(3L);
		command.setAmount(new BigDecimal(1.0));
		command.setDescription("Description");
		command.setRecipeId(1L);
		//command.setUnitOfMeasure(new UnitOfMeasure());
		
		//When
		when(ingredientService.findByRecipeIdAndIngredientId(anyLong(),  anyLong())).thenReturn(command);
		when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());
		
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
	        command.setId(3L);
	        command.setRecipeId(2L);

	        //when
	        when(ingredientService.saveIngredientCommand(any())).thenReturn(command);

	        //then
	        mockMvc.perform(post("/recipe/2/ingredient")
	                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                .param("id", "")
	                .param("description", "some string")
	        )
	                .andExpect(status().is3xxRedirection())
	                .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));

	    }

}
