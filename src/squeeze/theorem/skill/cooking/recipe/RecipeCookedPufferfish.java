package squeeze.theorem.skill.cooking.recipe;

import squeeze.theorem.item.CustomItem;
import squeeze.theorem.recipe.RecipeType;
import squeeze.theorem.recipe.SQMCRecipe;
import squeeze.theorem.skill.Skill;

public class RecipeCookedPufferfish extends SQMCRecipe {

	public RecipeCookedPufferfish() {
		setOutput(CustomItem.COOKED_PUFFERFISH);
		addRequirement(Skill.cooking, 25);
		setXP(Skill.cooking, 100.0);
		addInput(CustomItem.PUFFERFISH, 1);
		setRecipeType(RecipeType.COOKING_FIRE);
	}
	
}
