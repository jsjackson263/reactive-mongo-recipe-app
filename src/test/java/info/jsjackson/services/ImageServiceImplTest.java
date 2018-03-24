/**
 * 
 */
package info.jsjackson.services;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import info.jsjackson.domain.Recipe;
import info.jsjackson.repositories.RecipeRepository;

/**
 * @author josan 
 *
 */
public class ImageServiceImplTest {

	@Mock
	RecipeRepository recipeRespository;
	
	ImageService imageService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		imageService = new ImageServiceImpl(recipeRespository);
		
	}

	@Test
	public void testSaveImageFile() throws Exception {

		//Given
		Long recipeId = 1L;
		MultipartFile multipartFile = new MockMultipartFile(
				"imagefile", "testing.txt", "text/plain",  "Spring Framework Guru".getBytes()) ;
		
		Recipe recipe = new Recipe();
		recipe.setId(2L);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRespository.findById(anyLong())).thenReturn(recipeOptional);
		
		ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
		
		//When
		imageService.saveImageFile(recipeId, multipartFile);
		
		
		//Then
		verify(recipeRespository, times(1)).findById(anyLong());
		verify(recipeRespository, times(1)).save(argumentCaptor.capture());
		Recipe savedRecipe = argumentCaptor.getValue();
		assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
		
		
	}

}
