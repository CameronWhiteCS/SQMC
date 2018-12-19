package squeeze.theorem.skill.cooking.recipe;

import squeeze.theorem.item.CustomItem;
import squeeze.theorem.recipe.RecipeType;
import squeeze.theorem.recipe.SQMCRecipe;
import squeeze.theorem.skill.Skill;

public class RecipeCookedTurtleEgg extends SQMCRecipe {

	public RecipeCookedTurtleEgg() {
		setOutput(CustomItem.COOKED_TURTLE_EGG);
		addRequirement(Skill.cooking, 10);
		setXP(Skill.cooking, 66.0);
		addInput(CustomItem.TURTLE_EGG, 1);
		setRecipeType(RecipeType.COOKING_FIRE);
	}
	
}
