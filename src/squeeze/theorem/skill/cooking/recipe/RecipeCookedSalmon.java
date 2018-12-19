package squeeze.theorem.skill.cooking.recipe;

import squeeze.theorem.item.CustomItem;
import squeeze.theorem.recipe.RecipeType;
import squeeze.theorem.recipe.SQMCRecipe;
import squeeze.theorem.skill.Skill;

public class RecipeCookedSalmon extends SQMCRecipe {

	public RecipeCookedSalmon() {
		setOutput(CustomItem.COOKED_SALMON);
		addRequirement(Skill.cooking, 1);
		setXP(Skill.cooking, 33.0);
		addInput(CustomItem.SALMON, 1);
		setRecipeType(RecipeType.COOKING_FIRE);

		
	}
	
}
