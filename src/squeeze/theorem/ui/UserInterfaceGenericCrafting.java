package squeeze.theorem.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import squeeze.theorem.recipe.RecipeType;
import squeeze.theorem.recipe.SQMCRecipe;

public class UserInterfaceGenericCrafting extends ChestInterface {
	
	List<RecipeType> recipeTypes = new ArrayList<RecipeType>();
	
	public UserInterfaceGenericCrafting(String title, RecipeType...recipeTypes) {
		super(title);
		this.recipeTypes = Arrays.asList(recipeTypes);
		for(SQMCRecipe r: SQMCRecipe.getRecipes()) {
			if(this.recipeTypes.contains(r.getRecipeType())) {
				UIComponentRecipe uic = new UIComponentRecipe(r);
				this.addComponent(uic);
			}
		}
	}

}
