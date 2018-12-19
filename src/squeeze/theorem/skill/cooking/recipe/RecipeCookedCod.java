package squeeze.theorem.skill.cooking.recipe;

import squeeze.theorem.item.CustomItem;
import squeeze.theorem.recipe.RecipeType;
import squeeze.theorem.recipe.SQMCRecipe;
import squeeze.theorem.skill.Skill;

public class RecipeCookedCod extends SQMCRecipe {

	public RecipeCookedCod() {
		setOutput(CustomItem.COOKED_COD);
		addRequirement(Skill.cooking, 10);
		setXP(Skill.cooking, 66.0);
		addInput(CustomItem.COD, 1);
		setRecipeType(RecipeType.COOKING_FIRE);
	}
	
}
