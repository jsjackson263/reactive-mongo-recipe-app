package info.jsjackson.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import info.jsjackson.commands.RecipeCommand;
import info.jsjackson.converters.RecipeCommandToRecipe;
import info.jsjackson.converters.RecipeToRecipeCommand;
import info.jsjackson.domain.Difficulty;
import info.jsjackson.domain.Recipe;
import info.jsjackson.repositories.reactive.RecipeReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RecipeServiceImplTest {

	RecipeService recipeService;
	
	@Mock
	RecipeReactiveRepository recipeReactiveRepository;
	
	@Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImpl(recipeReactiveRepository, recipeCommandToRecipe, recipeToRecipeCommand);
	}

	@Test
	public void getRecipeByIdTest() throws Exception {
		//Given
		Recipe recipe = new Recipe();
		recipe.setId("1");
		
		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
		
		//When
		Recipe recipeReturned = recipeService.findById("1").block();
		
		//Then
		assertNotNull("Null Recipe Returned", recipeReturned);
		
		//verify interactions
		verify(recipeReactiveRepository, times(1)).findById(anyString());
		verify(recipeReactiveRepository, never()).findAll();
		
		
	}
	

	@Test
	public void getRecipeCommandByIdTest() throws Exception {
		
		//Given
		Recipe recipe = new Recipe();
		recipe.setId("2");
		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
		
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId("2");
		when(recipeToRecipeCommand.convert(recipe)).thenReturn(recipeCommand);
		
		//When
		RecipeCommand returnedRecipeCommand = recipeService.findCommandById("2").block();
		
		//Then
		assertNotNull("null RecipeCommand Returned", returnedRecipeCommand);
		assertEquals("2", returnedRecipeCommand.getId());
		
		verify(recipeReactiveRepository, times(1)).findById(anyString());
		verify(recipeReactiveRepository, never()).findAll();
		verify(recipeReactiveRepository, never()).deleteById(anyString());
		verify(recipeReactiveRepository, never()).save(any());
	}
	
	@Test 
	public void deleteRecipeById() throws Exception {
		
		//Given
		String idToDelete = "1";
		
		when(recipeReactiveRepository.deleteById(anyString())).thenReturn(Mono.empty());
		
		//When
		recipeService.deleteById(idToDelete);
		
		//Then
		verify(recipeReactiveRepository, times(1)).deleteById(idToDelete);
		
	}
	
	@Test
	public void getRecipesTest() throws Exception {
		
		//Given
		Recipe recipe = new Recipe();
		recipe.setCookTime(10);
		recipe.setDescription("Description");
		recipe.setDifficulty(Difficulty.EASY);
		recipe.setId("123");
		
		Set<Recipe> recipesData = new HashSet<Recipe>();
		recipesData.add(recipe);
		
		//when(recipeRepository.findAll()).thenReturn(recipesData);
		when(recipeService.getRecipes()).thenReturn(Flux.just(recipe));
		
		//When
		List<Recipe> recipes = recipeService.getRecipes().collectList().block();
		//System.out.println("Recipes: " + recipes);
		
		//Then
		assertEquals(1, recipes.size());
		
		//verify that the repository is called once
		verify(recipeReactiveRepository, times(1)).findAll();
	}
	
	@Test
	public void testSaveARecipeCommand() throws Exception {
		
		//Given
		Recipe savedRecipe = new Recipe();
		savedRecipe.setId("2");
		when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(savedRecipe));
		
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId("2");
		when(recipeToRecipeCommand.convert(savedRecipe)).thenReturn(recipeCommand);
		
		//When
		RecipeCommand returnedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
		
		//Then
		assertNotNull("saved recipe not null", returnedRecipeCommand);
		assertEquals("2", returnedRecipeCommand.getId());
		
		
		//Testing updateService will be the same as this test
	}

	
	
	
}
