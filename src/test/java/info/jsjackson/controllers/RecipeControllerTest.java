package info.jsjackson.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import info.jsjackson.commands.RecipeCommand;
import info.jsjackson.domain.Recipe;
import info.jsjackson.services.RecipeService;

public class RecipeControllerTest {

	//use a Mock ServletContext
	MockMvc mockMvc;
	
	@Mock
	RecipeService recipeService;
	
	@Mock
	Model model;
	
	RecipeController controller;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new RecipeController(recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		 
	}
	

	@Test
	public void testGetRecipe() throws Exception {
		//Given
		Recipe recipe = new Recipe();
		recipe.setId(2L);

		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		when(recipeService.findById(anyLong())).thenReturn(recipe);
		
		//When, Then ?
		mockMvc.perform(get("/recipe/2/show"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/show"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("recipe"))
		.andReturn();
				
	}
	
	@Test
	public void testGetNewRecipeForm()  throws Exception {
		
		//Given
		RecipeCommand command = new RecipeCommand();
		
		//When
		mockMvc.perform(get("/recipe/new/"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/recipeform"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("recipe"))
		.andReturn();
		
		//Then
		
	}
	
	@Test
	public void testPostNewRecipeForm() throws Exception {
		
		//Given
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);
		
		//When/Then
		when(recipeService.saveRecipeCommand(any())).thenReturn(command);
		
		mockMvc.perform(post("/recipe")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("description", "some description")
		)
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/recipe/2/show"))
		.andReturn();
		
	}
	
	@Test
	public void testGetUpdateView() throws Exception {
		
		RecipeCommand command = new RecipeCommand();
		command.setId(5L);
		
		when(recipeService.findCommandById(anyLong())).thenReturn(command);
		
		mockMvc.perform(get("/recipe/1/update"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/recipeform"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("recipe"))
		.andReturn();
		
		//verify interactions - additional testing
		 verify(recipeService, times(1)).findCommandById(anyLong());
		 verifyNoMoreInteractions(recipeService);
	
		
		
		
	}

}

