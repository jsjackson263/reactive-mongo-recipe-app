package info.jsjackson.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import info.jsjackson.domain.Recipe;
import info.jsjackson.services.RecipeServiceImpl;
import reactor.core.publisher.Flux;

public class IndexControllerTest {

	IndexController controller;
	
	@Mock
	RecipeServiceImpl recipeService;
	
	@Mock
	Model model;
	
	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new IndexController(recipeService);
		
	}
	
	@Test
	public void testMockMVC() throws Exception {
		
		//Given
		
		when(recipeService.getRecipes()).thenReturn(Flux.empty());
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		mockMvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(view().name("index"));
		
		//When
		
	}

	
	@Test
	public void testGetIndexPage() {
		//Given
		Set<Recipe> recipes = new HashSet<Recipe>();
		recipes.add(new Recipe());
		
		Recipe recipe2 = new Recipe();
		recipe2.setId("123");
		recipes.add(recipe2);
		
		when(recipeService.getRecipes()).thenReturn(Flux.fromIterable(recipes));
		
		ArgumentCaptor<List<Recipe>> argumentCaptor = ArgumentCaptor.forClass(List.class);
		
		//When
		String viewName = controller.getIndexPage(model);
		
		//Then
		assertEquals("index", viewName);
		
		//verify interactions
		verify(recipeService, times(1)).getRecipes();
		verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
		
		List<Recipe> listInController = argumentCaptor.getValue();
		assertEquals(2, listInController.size());
		
	}


}
