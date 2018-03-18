package info.jsjackson.controllers;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import info.jsjackson.commands.IngredientCommand;
import info.jsjackson.commands.RecipeCommand;
import info.jsjackson.services.IngredientService;
import info.jsjackson.services.RecipeService;

public class IngredientControllerTest {

	@Mock
	RecipeService recipeService;
	
	@Mock
	IngredientService ingredientService;
	
	IngredientController controller;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new IngredientController(recipeService, ingredientService);
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

}
