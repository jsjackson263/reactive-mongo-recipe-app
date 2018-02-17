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

import info.jsjackson.domain.Difficulty;
import info.jsjackson.domain.Notes;
import info.jsjackson.domain.Recipe;
import info.jsjackson.repositories.RecipeRepository;

public class RecipeServiceImplTest {

	//RecipeServiceImpl recipeService;
	RecipeService recipeService;
	
	
	@Mock
	RecipeRepository recipeRepository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository);
	}

	@Test
	public void testGetRecipes() {
		
		//Given
		Recipe recipe = new Recipe();
		Notes notes = new Notes();
		notes.setId(12l);
		notes.setRecipeNotes("recipeNotes");
		notes.setRecipe(recipe);
		recipe.setCookTime(10);
		recipe.setDescription("Description");
		recipe.setDifficulty(Difficulty.EASY);
		recipe.setId(123l);
		
		Set<Recipe> recipesData = new HashSet<Recipe>();
		recipesData.add(recipe);
		
		//when(recipeRepository.findAll()).thenReturn(recipesData);
		when(recipeService.getRecipes()).thenReturn(recipesData);
		
		//When
		Set<Recipe> recipes = recipeService.getRecipes();
		System.out.println("Recipes: " + recipes);
		
		//Then
		assertEquals(1, recipes.size());
		
		//verify that the repository is called once
		verify(recipeRepository, times(1)).findAll();
	}

	
	
}
