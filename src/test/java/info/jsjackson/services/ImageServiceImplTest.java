/**
 * 
 */
package info.jsjackson.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import info.jsjackson.domain.Recipe;
import info.jsjackson.repositories.reactive.RecipeReactiveRepository;
import reactor.core.publisher.Mono;

/**
 * @author josan 
 *
 */
public class ImageServiceImplTest {

	@Mock
	RecipeReactiveRepository recipeReactiveRepository;
	
	ImageService imageService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		imageService = new ImageServiceImpl(recipeReactiveRepository);
		
	}

	@Test
	public void testSaveImageFile() throws Exception {

		//Given
		String recipeId = "1";
		MultipartFile multipartFile = new MockMultipartFile(
				"imagefile", "testing.txt", "text/plain",  "Spring Framework Guru".getBytes()) ;
		
		Recipe recipe = new Recipe();
		recipe.setId("2");
		
		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
		when(recipeReactiveRepository.save(any(Recipe.class))).thenReturn(Mono.just(recipe));
		
		ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
		
		//When
		imageService.saveImageFile(recipeId, multipartFile).block();
		
		
		//Then
		verify(recipeReactiveRepository, times(1)).findById(anyString());
		verify(recipeReactiveRepository, times(1)).save(argumentCaptor.capture());
		Recipe savedRecipe = argumentCaptor.getValue();
		assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
		
		
	}

}
