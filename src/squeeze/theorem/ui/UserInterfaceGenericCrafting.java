package squeeze.theorem.ui;

import java.util.ArrayList;

import squeeze.theorem.recipe.RecipeType;
import squeeze.theorem.recipe.SQMCRecipe;

public class UserInterfaceGenericCrafting extends MultiPageInterface {
	
	ArrayList<RecipeType> recipeTypes = new ArrayList<RecipeType>();
	
	public UserInterfaceGenericCrafting(String title, int size, RecipeType... recipeTypes) {
		super(title, size);
		
		for(RecipeType r: recipeTypes) {
			this.recipeTypes.add(r);
		}
		
		for(SQMCRecipe r: SQMCRecipe.getRecipes()) {
			UIComponentRecipe uic = new UIComponentRecipe(r);
			if(this.recipeTypes.contains(r.getRecipeType())) this.addComponent(uic);
		}
	}

}
