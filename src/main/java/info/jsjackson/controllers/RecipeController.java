/**
 * 
 */
package info.jsjackson.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.thymeleaf.exceptions.TemplateInputException;

import info.jsjackson.commands.RecipeCommand;
import info.jsjackson.domain.Recipe;
import info.jsjackson.exceptions.NotFoundException;
import info.jsjackson.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author josan
 *
 */
@Slf4j
@Controller
public class RecipeController {
	
	private final RecipeService recipeService;
	private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";

	private WebDataBinder webDataBinder;
	
	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		this.webDataBinder = webDataBinder;
	}
	
	
	@GetMapping("/recipe/{id}/show")
	public String showById(@PathVariable String id, Model model) {
		Mono<Recipe> recipe = recipeService.findById(id);
		model.addAttribute("recipe", recipe);
		
		//return view name
		return "recipe/show";
	}
	
	@GetMapping("recipe/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return RECIPE_RECIPEFORM_URL;
		//TODO: fix input tag for recipeForm
	}

	@GetMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		//keep the controllers clean - let the service convert the objects
		model.addAttribute("recipe", recipeService.findCommandById(id).block());
		return RECIPE_RECIPEFORM_URL;
	}
	
	/*@RequestMapping(name = "recipe", method = RequestMethod.POST)  - alternatively
	  @ModelAttribute tells Spring to bind the form post parameters to RecipeCommand object */
	@PostMapping("recipe")
	public String saveOrUpdate(@ModelAttribute("recipe") RecipeCommand command) {
		
		webDataBinder.validate();
		BindingResult bindingResult = webDataBinder.getBindingResult();
		
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(objectError -> {
				log.debug(objectError.toString());
			});
			
			return RECIPE_RECIPEFORM_URL; 
		}
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

		//tell SpringMVC to redirect to a specific url
		return "redirect:/recipe/" + savedCommand.getId() + "/show"; 
		
	}
	
	@GetMapping("recipe/{id}/delete")
	public String deleteRecipeById(@PathVariable String id) {
		
		log.debug("Deleting id: " + id);
		
		recipeService.deleteById(id);
		return "redirect:/";
		
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({NotFoundException.class, TemplateInputException.class})
	public String handleNotFound(Exception exception, Model model) {
		
		log.error("Handling Not Found Exception");
		log.error(exception.getMessage());
		
		model.addAttribute("exception", exception);
		
		return "404error";
		
	}
	
}
