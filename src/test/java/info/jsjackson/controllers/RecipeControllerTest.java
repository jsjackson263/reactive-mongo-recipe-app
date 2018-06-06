package info.jsjackson.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Ignore;
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
import info.jsjackson.exceptions.NotFoundException;
import info.jsjackson.services.RecipeService;
import reactor.core.publisher.Mono;

@Ignore
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
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new ControllerExceptionHandler())
				.build();
	}
	

	@Test
	public void testGetRecipe() throws Exception {
		//Given
		Recipe recipe = new Recipe();
		recipe.setId("2");

		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		when(recipeService.findById(anyString())).thenReturn(Mono.just(recipe));
		
		//When, Then ?
		mockMvc.perform(get("/recipe/2/show"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/show"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("recipe"))
		.andReturn();
				
	}
	
	@Test
	public void testGetRecipeNotFound() throws Exception {
		
		when(recipeService.findById(anyString())).thenThrow(NotFoundException.class);
		
		mockMvc.perform(get("/recipe/1/show"))
		.andExpect(status().isNotFound())
		.andExpect(view().name("404error"))
		.andReturn();
	}
	
	//@Test
	public void testGetRecipeNumberFormatException() throws Exception {
	
		//No need to mock the service operation here - we never get to the service 
		mockMvc.perform(get("/recipe/xxx/show"))
		.andExpect(status().isBadRequest())
		.andExpect(view().name("400error"))
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
		command.setId("2");
		
		//When/Then
		when(recipeService.saveRecipeCommand(any())).thenReturn(command);
		
		mockMvc.perform(post("/recipe")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("description", "some description")
				.param("directions", "some directions")
		)
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/recipe/2/show"))
		.andReturn();
		
	}
	
	@Test
	public void testPostNewRecipeFormValidationFail() throws Exception {
		
		//Given
		RecipeCommand command = new RecipeCommand();
		command.setId("2");
		
		//When/Then
		when(recipeService.saveRecipeCommand(any())).thenReturn(command);
		
		mockMvc.perform(post("/recipe")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("cookTime", "3000")  //value out of range
		)
		.andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.model().attributeExists("recipe"))
		.andExpect(view().name("recipe/recipeform"))
		.andReturn();
		
	}
	
	
	
	@Test
	public void testGetUpdateView() throws Exception {
		
		RecipeCommand command = new RecipeCommand();
		command.setId("5");
		
		when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(command));
		
		mockMvc.perform(get("/recipe/1/update"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/recipeform"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("recipe"))
		.andReturn();
		
		//verify interactions - additional testing
		 verify(recipeService, times(1)).findCommandById(anyString());
		 verifyNoMoreInteractions(recipeService);
	
	}
	
	@Test
	public void testDeleteRecipeAction() throws Exception {
		
		//no when, since method doesn't return anything - just verify the it's been invoked
		
		//we're using "get" here because we cannot do a delete method within http unless we're using JavaScript.
		//for a Restful interface we'd be doing the delete http method - here' we're limited to HTML
		mockMvc.perform(get("/recipe/1/delete"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/"))
		.andReturn();
		
		verify(recipeService, times(1)).deleteById(anyString());
		
	}
	
}

