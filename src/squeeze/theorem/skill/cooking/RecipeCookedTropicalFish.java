package squeeze.theorem.skill.cooking;

import squeeze.theorem.item.CustomItem;
import squeeze.theorem.recipe.RecipeType;
import squeeze.theorem.recipe.SQMCRecipe;
import squeeze.theorem.skill.Skill;

public class RecipeCookedTropicalFish extends SQMCRecipe {

	public RecipeCookedTropicalFish() {
		setOutput(CustomItem.COOKED_TROPICAL_FISH);
		addRequirement(Skill.cooking, 15);
		setXP(Skill.cooking, 100.0);
		addInput(CustomItem.TROPICAL_FISH, 1);
		setRecipeType(RecipeType.COOKING_FIRE);
	}
	
}
