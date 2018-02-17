package info.jsjackson.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import info.jsjackson.domain.Recipe;
import info.jsjackson.services.RecipeService;

public class MyIndexControllerTest {

	@Mock
	private RecipeService recipeService;

	@Mock
	private Model model;

	private IndexController controller;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new IndexController(recipeService);
	}

	@Test
	public void testMockMvc() throws Exception {
		/*
		 * Controllers are tricky to test when testing response codes, media types,
		 * request path mappings, etc as it requires bringing up a web server
		 * 
		 * Using a Mock ServletContext -capability to (unit) test Spring MVC Controllers
		 * using a mock Dispatcher Servlet -test become very lightweight - no need to
		 * bring up Spring context
		 */
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build(); // standaloneSetup - don't bring up the
																				// Spring context
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
		.andExpect(status().isOk())
		.andExpect(view().name("index"))
		.andReturn();

	}

	@Test
	public void testGetIndexPage() {

		// Given
		Set<Recipe> recipes = new HashSet<>();
		recipes.add(new Recipe());

		Recipe recipe = new Recipe();
		recipe.setId(2L);
		recipes.add(recipe);
		when(recipeService.getRecipes()).thenReturn(recipes);

		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

		// When
		String view = controller.getIndexPage(model);

		// Then
		assertEquals("index", view);
		// verify interactions
		verify(recipeService, times(1)).getRecipes();
		verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());

		Set<Recipe> setInController = argumentCaptor.getValue();
		assertEquals(2, setInController.size());
	}

}
