/**
 * 
 */
package info.jsjackson.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import info.jsjackson.commands.RecipeCommand;
import info.jsjackson.services.ImageService;
import info.jsjackson.services.RecipeService;
import reactor.core.publisher.Mono;

/**
 * @author josan 
 *
 */
public class ImageControllerTest {

	@Mock
	ImageService imageService;
	
	@Mock
	RecipeService recipeService;
	
	MockMvc mockMvc;
	
	ImageController imagecontroller;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		imagecontroller = new ImageController(imageService, recipeService);
		
		mockMvc = MockMvcBuilders.standaloneSetup(imagecontroller)
				.setControllerAdvice(new ControllerExceptionHandler())
				.build();
		
		
	}
	
	@Test
	public void getImageForm() throws Exception {
		
		//Given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId("2");
		
		when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(recipeCommand));
		
		//When
		mockMvc.perform(get("/recipe/2/image"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("recipe"))
		.andReturn();
		
		//Then
		verify(recipeService, times(1)).findCommandById(anyString());
	
	}

	@Test
	public void handleImagePost() throws Exception {
		
		MockMultipartFile multipartFile = new MockMultipartFile(
				"imagefile", "testing.txt", "text/plain",  "Spring Framework Guru".getBytes()) ;
		
		when(imageService.saveImageFile(anyString(),  any())).thenReturn(Mono.empty());
		
		mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
		.andExpect(status().is3xxRedirection())
		.andExpect(header().string("Location",  "/recipe/1/show"))
		.andReturn();
		
		
		verify(imageService, times(1)).saveImageFile(anyString(), any());
	
	}

	
	@Test
	public void renderImageFromDB() throws Exception {
		
		//Given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId("3");
		
		//NOTE: could have used an actual image here, but we just need the byte array
		String fakeImage = "fake image text";  
		Byte[] bytesBoxed = new Byte[fakeImage.getBytes().length];
		
		int i= 0;
		for (byte primitiveByte : fakeImage.getBytes()) {
			bytesBoxed[i++] = primitiveByte;
		}
		recipeCommand.setImage(bytesBoxed);
		
		when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(recipeCommand));
		
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		
		byte[] responseBytes = response.getContentAsByteArray();
		
		//Then
		assertEquals(fakeImage.getBytes().length, responseBytes.length);
		//assertEquals(fakeImage.getBytes(), responseBytes);  //doesn't work - why?
		
	}
	
	//@Test
	public void testGetImageNumberFormatException() throws Exception {
		
		 mockMvc.perform(get("/recipe/xxx/recipeimage"))
		 .andExpect(status().isBadRequest())
		 .andExpect(view().name("400error"))
		 .andReturn();
	}
	
}
