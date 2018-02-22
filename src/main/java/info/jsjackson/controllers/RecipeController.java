/**
 * 
 */
package info.jsjackson.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import info.jsjackson.domain.Recipe;
import info.jsjackson.services.RecipeService;

/**
 * @author josan
 *
 */
@Controller
public class RecipeController {
	
	RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	
	@RequestMapping("/recipe/show/{id}")
	public String showById(@PathVariable String id, Model model) {
		
		Recipe recipe = recipeService.findById(new Long(id));
		model.addAttribute("recipe", recipe);
		
		//return view name
		return "recipe/show";
	}
	

}
