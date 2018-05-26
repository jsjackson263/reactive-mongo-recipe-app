/**
 * 
 */
package info.jsjackson.bootstrap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import info.jsjackson.domain.Category;
import info.jsjackson.domain.Difficulty;
import info.jsjackson.domain.Ingredient;
import info.jsjackson.domain.Notes;
import info.jsjackson.domain.Recipe;
import info.jsjackson.domain.UnitOfMeasure;
import info.jsjackson.repositories.CategoryRepository;
import info.jsjackson.repositories.RecipeRepository;
import info.jsjackson.repositories.UnitOfMeasureRepository;
import info.jsjackson.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author josan
 *
 */
@Slf4j
@Component
@Profile("default")
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

	private RecipeRepository recipeRepository;
	private CategoryRepository categoryRepository;
	private UnitOfMeasureRepository unitOfMeasureRepository;

	public RecipeBootstrap(RecipeRepository recipeRepository, CategoryRepository categoryRepository,
			UnitOfMeasureRepository unitOfMeasureRepository) {
		this.recipeRepository = recipeRepository;
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	//in the event of lazy initialization, direct the SpringFramework to create a txn around
	//this method, so that everything runs within the same transactional boundaries 
	@Transactional  
	@Override
	public void onApplicationEvent(ContextRefreshedEvent paramE) {
		 loadCategories();
		 loadUom();
		
		log.debug("Event Source: " + paramE.getSource().toString());
		List<Recipe> recipes = getRecipes();
		log.debug("Loading Bootstrap Data");
		recipeRepository.saveAll(recipes);
	}
	
	private void loadCategories() {
		Category american = new Category();
		american.setDescription("American");
		categoryRepository.save(american);
		
		Category italian = new Category();
		italian.setDescription("Italian");
		categoryRepository.save(italian);
		
		Category mexican = new Category();
		mexican.setDescription("Mexican");
		categoryRepository.save(mexican);
		
		Category fastFood = new Category();
		fastFood.setDescription("Fast Food");
		categoryRepository.save(fastFood);
		
	}
	
	private void loadUom(){
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setDescription("Teaspoon");
        unitOfMeasureRepository.save(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setDescription("Tablespoon");
        unitOfMeasureRepository.save(uom2);

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setDescription("Cup");
        unitOfMeasureRepository.save(uom3);

        UnitOfMeasure uom4 = new UnitOfMeasure();
        uom4.setDescription("Pinch");
        unitOfMeasureRepository.save(uom4);

        UnitOfMeasure uom5 = new UnitOfMeasure();
        uom5.setDescription("Ounce");
        unitOfMeasureRepository.save(uom5);

        UnitOfMeasure uom6 = new UnitOfMeasure();
        uom6.setDescription("Each");
        unitOfMeasureRepository.save(uom6);

        UnitOfMeasure uom7 = new UnitOfMeasure();
        uom7.setDescription("Pint");
        unitOfMeasureRepository.save(uom7);

        UnitOfMeasure uom8 = new UnitOfMeasure();
        uom8.setDescription("Dash");
        unitOfMeasureRepository.save(uom8);
	}

	private List<Recipe> getRecipes() {
		List<Recipe> recipes = new ArrayList<Recipe>();
		
		//get Unit of Measures
		Optional<UnitOfMeasure> eachUOMOptional = unitOfMeasureRepository.findByDescription("Each");
		if (!eachUOMOptional.isPresent()) {
			throw new RuntimeException("Expected UOM 'Each' Not found");
		}
		
		Optional<UnitOfMeasure> tableSpoonUOMOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
		if (!tableSpoonUOMOptional.isPresent()) {
			throw new RuntimeException("Expected UOM 'TableSpoon' Not found");
		}
		
		Optional<UnitOfMeasure> teaSpoonUOMOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
		if (!teaSpoonUOMOptional.isPresent()) {
			throw new RuntimeException("Expected UOM 'TableSpoon' Not found");
		}
		
		Optional<UnitOfMeasure> dashUOMOptional = unitOfMeasureRepository.findByDescription("Dash");
		if (!dashUOMOptional.isPresent()) {
			throw new RuntimeException("Expected UOM 'Dash' Not found");
		}
		
		Optional<UnitOfMeasure> pintUOMOptional = unitOfMeasureRepository.findByDescription("Pint");
		if (!pintUOMOptional.isPresent()) {
			throw new RuntimeException("Expected UOM 'Pint' Not found");
		}
		
		Optional<UnitOfMeasure> cupUOMOptional = unitOfMeasureRepository.findByDescription("Cup");
		if (!cupUOMOptional.isPresent()) {
			throw new RuntimeException("Expected UOM 'Cup' Not found");
		}
		
		Optional<UnitOfMeasure> pinchUOMOptional = unitOfMeasureRepository.findByDescription("Pinch");
		if (!pinchUOMOptional.isPresent()) {
			throw new RuntimeException("Expected UOM 'Pinch' Not found");
		}
		
		Optional<UnitOfMeasure> ounceUOMOptional = unitOfMeasureRepository.findByDescription("Ounce");
		if (!ounceUOMOptional.isPresent()) {
			throw new RuntimeException("Expected UOM 'Ounce' Not found");
		}
		
		//get the Optionals
		UnitOfMeasure eachUOM = eachUOMOptional.get();
		UnitOfMeasure tablespoonUOM = tableSpoonUOMOptional.get();
		UnitOfMeasure teaspoonUOM = teaSpoonUOMOptional.get();
		UnitOfMeasure dashUOM = dashUOMOptional.get();
		UnitOfMeasure pintUOM = pintUOMOptional.get();
		UnitOfMeasure cupUOM = cupUOMOptional.get();
		UnitOfMeasure pinchUOM = pinchUOMOptional.get();
		UnitOfMeasure ounceUOM = ounceUOMOptional.get();
		
		//get the Categories
		Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");
		if (!americanCategoryOptional.isPresent()) {
			throw new RuntimeException("Expected Category Not Found");
		}
		
		Optional<Category> italianCategoryOptional = categoryRepository.findByDescription("Italian");
		if (!italianCategoryOptional.isPresent()) {
			throw new RuntimeException("Expected Category Not Found");
		}
		
		Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");
		if (!mexicanCategoryOptional.isPresent()) {
			throw new RuntimeException("Expected Category Not Found");
		}
		
		Optional<Category> fastFoodCategoryOptional = categoryRepository.findByDescription("Fast Food");
		if (!fastFoodCategoryOptional.isPresent()) {
			throw new RuntimeException("Expected Category Not Found");
		}
		
		//get the Category Optionals
		Category americanCategory = americanCategoryOptional.get();
		Category italianCategory = italianCategoryOptional.get();
		Category mexicanCategory = mexicanCategoryOptional.get();
		Category fastFoodCategory = fastFoodCategoryOptional.get();
		
		
		//Holly Guacamole!
		Recipe yummyGuacRecipe = new Recipe();
		yummyGuacRecipe.setDescription("How to Make Perfect Guacamole");
		yummyGuacRecipe.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/");
		yummyGuacRecipe.setServings(4);
		yummyGuacRecipe.setSource("Simply Recipes");
		
		yummyGuacRecipe.setPrepTime(10);
		yummyGuacRecipe.setCookTime(0);
		yummyGuacRecipe.setDifficulty(Difficulty.EASY);
		yummyGuacRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl." +
				"\n" +
				"\n" +
				"2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
				"\n" +
				"\n" +
				"3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown." + "\n" +
				"Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness." +  "\n" +
				"Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste." +
				"\n" +
				"\n" +
				"4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve." + "\n" +
				"Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving." +
				"\n" +
				"\n" +
				"Read More: https://www.simplyrecipes.com/recipes/perfect_guacamole/"
				);
		Notes guacNotes = new Notes();
		guacNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados." + "\n" +
		"Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries (see our Strawberry Guacamole)" + "\n" +
				"The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole." + "\n" +
		"To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great." + "\n" +
				"\n" +
				"\n" +
				 "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws"
				);
		//guacNotes.setRecipe(yummyGuacRecipe);
		yummyGuacRecipe.setNotes(guacNotes);
		
		//redundant - could add helper method, and make this simpler
		yummyGuacRecipe.addIngredient(new Ingredient("ripe avocadoes", new BigDecimal(2), eachUOM));
		yummyGuacRecipe.addIngredient(new Ingredient("kosher Salt", new BigDecimal(0.5), teaspoonUOM));
		yummyGuacRecipe.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), teaspoonUOM));
		yummyGuacRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), teaspoonUOM));
		yummyGuacRecipe.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachUOM));
		yummyGuacRecipe.addIngredient(new Ingredient("Cilantro", new BigDecimal(2), tablespoonUOM));
		yummyGuacRecipe.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), dashUOM));
		yummyGuacRecipe.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(0.5), dashUOM));
		
		yummyGuacRecipe.getCategories().add(americanCategory);
		yummyGuacRecipe.getCategories().add(mexicanCategory);
		
		//add to return list
		recipes.add(yummyGuacRecipe);
		
		//************************************************************************************************
		
		//Yummy Tacos
		Recipe tacosRecipe = new Recipe();
		tacosRecipe.setDescription("Spicy Grilled Chicken Tacos Recipe");
		tacosRecipe.setUrl("http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
		tacosRecipe.setServings(6);
		tacosRecipe.setSource("Simply Recipes");
		tacosRecipe.setPrepTime(20);
		tacosRecipe.setCookTime(15);
		tacosRecipe.setDifficulty(Difficulty.MODERATE);
		tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat." + "\n" +
				"\n" +
				"\n" +
				"2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. \n Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over." +
				"Set aside to marinate while the grill heats and you prepare the rest of the toppings" +
				"\n" +
				"\n" +
				"3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. \n Transfer to a plate and rest for 5 minutes." +
				"4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat.\n As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side. \n" +
				"Wrap warmed tortillas in a tea towel to keep them warm until serving." +
				"\n" +
				"\n" +
				"5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. \n Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. \n Drizzle with the thinned sour cream. Serve with lime wedges." 
				);
		
		Notes tacoNotes = new Notes();
		//tacoNotes.setRecipe(tacosRecipe);
		tacoNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\r\n" + 
				"\r\n" + 
				"Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\r\n" + 
				"\r\n" + 
				"Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\r\n" + 
				"\r\n" + 
				"First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\r\n" + 
				"\r\n" + 
				"Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!" +
				"\n" +
				"\n" +
				"Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ"
				);
		
		tacosRecipe.setNotes(tacoNotes);
		
		//redundant - could add helper method, and make this simpler
		tacosRecipe.addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tablespoonUOM));
		tacosRecipe.addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tablespoonUOM));
		tacosRecipe.addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), teaspoonUOM));
        tacosRecipe.addIngredient(new Ingredient("Dried Cumin", new BigDecimal(1), teaspoonUOM));
        tacosRecipe.addIngredient(new Ingredient("Sugar", new BigDecimal(1), teaspoonUOM));
        tacosRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(".5"), teaspoonUOM));
        tacosRecipe.addIngredient(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), eachUOM));
        tacosRecipe.addIngredient(new Ingredient("finely grated orange zestr", new BigDecimal(1), tablespoonUOM));
        tacosRecipe.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tablespoonUOM));
        tacosRecipe.addIngredient(new Ingredient("Olive Oil", new BigDecimal(2), tablespoonUOM));
        tacosRecipe.addIngredient(new Ingredient("boneless chicken thighs", new BigDecimal(4), tablespoonUOM));
        tacosRecipe.addIngredient(new Ingredient("small corn tortillasr", new BigDecimal(8), eachUOM));
        tacosRecipe.addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), cupUOM));
        tacosRecipe.addIngredient(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), eachUOM));
        tacosRecipe.addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4), eachUOM));
        tacosRecipe.addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pintUOM));
        tacosRecipe.addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), eachUOM));
        tacosRecipe.addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), eachUOM));
        tacosRecipe.addIngredient(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupUOM));
        tacosRecipe.addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(4), eachUOM));
        
        tacosRecipe.getCategories().add(mexicanCategory);
        tacosRecipe.getCategories().add(americanCategory);
        //add to return list
      	recipes.add(tacosRecipe);
		
		return recipes;
		
		
	}
	
	//XXX: When using MySQL DB, data load being duplicated - fit it  
}
