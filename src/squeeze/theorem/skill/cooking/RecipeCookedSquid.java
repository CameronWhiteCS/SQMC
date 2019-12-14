package squeeze.theorem.skill.cooking;

import squeeze.theorem.item.CustomItem;
import squeeze.theorem.recipe.RecipeType;
import squeeze.theorem.recipe.SQMCRecipe;
import squeeze.theorem.skill.Skill;

public class RecipeCookedSquid extends SQMCRecipe {

	public RecipeCookedSquid() {
		setOutput(CustomItem.COOKED_SQUID);
		addRequirement(Skill.cooking, 32);
		setXP(Skill.cooking, 100.0);
		addInput(CustomItem.SQUID, 1);
		setRecipeType(RecipeType.COOKING_FIRE);
	}
	
}
