package tacos.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import tacos.Taco;
import tacos.Design;
import tacos.Ingredient;
import tacos.Ingredient.Type;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {
	
	@GetMapping()
	public String showDesignForm(Model model) {
		
		
		List<Ingredient> ingredients = Arrays.asList(
				new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
				new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
				new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
				new Ingredient("CARN", "Carnitas", Type.PROTEIN),
				new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
				new Ingredient("LETC", "Lettuce", Type.VEGGIES),
				new Ingredient("CHED", "Cheddar", Type.CHEESE),
				new Ingredient("JACK", "Jack", Type.CHEESE),
				new Ingredient("SLSA", "Salsa", Type.SAUCE),
				new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
				);
		
		
		Type [] types = Type.values();
		for(Type type : types) {
			List<Ingredient> filteredTypes = filterByType(ingredients, type);
			model.addAttribute(type.toString().toLowerCase(), filteredTypes);
		}
		
		model.addAttribute("design", new Taco());
		
		return "design-a-taco";
	}
	
	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		List<Ingredient> IngredientsOfType = new ArrayList<>();
		
		for(Ingredient ingredient : ingredients) {
			if(ingredient.getType() == type)
				IngredientsOfType.add(ingredient);
		}
		return IngredientsOfType;
	}
	
	@PostMapping
	public String processDesign(@Valid Design design, Errors errors) {
		if(errors.hasErrors()) {
			return "design";
		}
		
		// Save the taco design...
		log.info("Processing design: " + design);
		
		return "redirect:/orders/current";
	}
}
