package info.jsjackson.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import info.jsjackson.domain.Recipe;
import info.jsjackson.services.RecipeService;

public class RecipeControllerTest {

	@Mock
	RecipeService recipeService;
	
	@Mock
	Model model;
	
	RecipeController controller;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new RecipeController(recipeService); 
	}
	

	@Test
	public void testGetRecipe() throws Exception {
		//Given
		Recipe recipe = new Recipe();
		recipe.setId(2L);

		//use a Mock ServletContext
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		when(recipeService.getById(anyLong())).thenReturn(recipe);
		
		//When, Then ?
		mockMvc.perform(get("/recipe/show/2"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/show"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("recipe"))
		.andReturn();
				
		
	}

}

