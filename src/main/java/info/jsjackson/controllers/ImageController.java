/**
 * 
 */
package info.jsjackson.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import info.jsjackson.commands.RecipeCommand;
import info.jsjackson.services.ImageService;
import info.jsjackson.services.RecipeService;

/**
 * @author josan 
 *
 */
@Controller
public class ImageController {
	
	private final ImageService imageService;
	private final RecipeService recipeService;

	public ImageController(ImageService imageService, RecipeService recipeService) {
		this.imageService = imageService;
		this.recipeService = recipeService;
	}
	
	//the @GetMapping argument is an example of a URI template, &  recipeId is a URI template variable.  
	//@PathVariable annotation binds to the value of URI template variable 
	//see https://docs.spring.io/spring/docs/3.1.x/spring-framework-reference/htmlsingle/spring-framework-reference.html#mvc-ann-requestmapping-uri-templates
	@GetMapping("/recipe/{recipeId}/image")  
	public String getImageForm(@PathVariable String recipeId, Model model) {
		
		RecipeCommand recipeCommand = recipeService.findCommandById(recipeId).block();
		model.addAttribute("recipe", recipeCommand);
		
		
		return "recipe/imageuploadform";
		
	}

	@PostMapping("/recipe/{recipeId}/image")
	public String handleImagePost(@PathVariable String recipeId, 
			@RequestParam("imagefile") MultipartFile file) {
		
		imageService.saveImageFile(recipeId, file).block();
		
		return "redirect:/recipe/" + recipeId + "/show";
	}
	
	@GetMapping("/recipe/{recipeId}/recipeimage")
	public void renderImageFromDB(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
		RecipeCommand recipeCommand = recipeService.findCommandById(recipeId).block();
		
		if (recipeCommand.getImage() != null) {
			byte[] byteArray = new byte[recipeCommand.getImage().length];
			
			int i = 0;
			
			//convert from wrapper to primitive
			for (Byte wrappedByte : recipeCommand.getImage()) {
				byteArray[i++] = wrappedByte; //auto unboxing
			}
			
			response.setContentType("/image/jpeg");
			InputStream is = new ByteArrayInputStream(byteArray);
			IOUtils.copy(is, response.getOutputStream());
		}
		
	}
}
