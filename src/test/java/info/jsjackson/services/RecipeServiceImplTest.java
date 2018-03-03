package info.jsjackson.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import info.jsjackson.commands.RecipeCommand;
import info.jsjackson.converters.RecipeCommandToRecipe;
import info.jsjackson.converters.RecipeToRecipeCommand;
import info.jsjackson.domain.Difficulty;
import info.jsjackson.domain.Notes;
import info.jsjackson.domain.Recipe;
import info.jsjackson.repositories.RecipeRepository;

public class RecipeServiceImplTest {

	//RecipeServiceImpl recipeService;
	RecipeService recipeService;
	
	
	@Mock
	RecipeRepository recipeRepository;
	
	@Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;
    
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
	}

	@Test
	public void getRecipeByIdTest() throws Exception {
		//Given
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		//When
		Recipe recipeReturned = recipeService.findById(1L);
		
		//Then
		assertNotNull("Null Recipe Returned", recipeReturned);
		
		//verify interactions
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, never()).findAll();
		
		
	}
	
	@Test
	public void testGetRecipes() throws Exception {
		
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
		//System.out.println("Recipes: " + recipes);
		
		//Then
		assertEquals(1, recipes.size());
		
		//verify that the repository is called once
		verify(recipeRepository, times(1)).findAll();
	}
	
	@Test
	public void testSaveARecipeCommand() throws Exception {
		
		//Given
		Recipe savedRecipe = new Recipe();
		savedRecipe.setId(2L);
		when(recipeRepository.save(any())).thenReturn(savedRecipe);
		
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(2L);
		when(recipeToRecipeCommand.convert(savedRecipe)).thenReturn(recipeCommand);
		
		//When
		RecipeCommand returnedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
		
		//Then
		assertNotNull("saved recipe not null", returnedRecipeCommand);
		assertEquals(Long.valueOf(2L), returnedRecipeCommand.getId());
		
		
		//Testing updateService will be the same as this test
	}

	
	
	
}
