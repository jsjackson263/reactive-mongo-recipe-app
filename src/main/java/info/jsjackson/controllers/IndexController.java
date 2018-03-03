/**
 * 
 */
package info.jsjackson.controllers;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import info.jsjackson.domain.Category;
import info.jsjackson.domain.Recipe;
import info.jsjackson.domain.UnitOfMeasure;
import info.jsjackson.repositories.CategoryRepository;
import info.jsjackson.repositories.UnitOfMeasureRepository;
import info.jsjackson.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author josan
 *
 */
@Slf4j
@Controller
public class IndexController {

	private RecipeService recipeService;
	
	public IndexController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}


	@RequestMapping({"/", "", "/index"})
	public String getIndexPage(Model model) {

		log.debug("Getting index page");
		
		Set<Recipe> recipes = recipeService.getRecipes();
		model.addAttribute("recipes", recipes);
		
		
		return "index";
	}
	
	//XXX: write a unit test for IndexController!
}
