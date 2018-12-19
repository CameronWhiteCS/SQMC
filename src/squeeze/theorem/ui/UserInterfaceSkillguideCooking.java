package squeeze.theorem.ui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import squeeze.theorem.recipe.RecipeType;
import squeeze.theorem.recipe.SQMCRecipe;

public class UserInterfaceSkillguideCooking extends MultiPageInterface {

	public UserInterfaceSkillguideCooking(String title, int size) {
		super(title, size);
		for (SQMCRecipe cr : SQMCRecipe.getRecipes()) {
			if (cr.getRecipeType().equals(RecipeType.COOKING_FIRE)) {
				addComponent(new UIComponent() {

					@Override
					public ItemStack getItemStack(Player player) {
						return cr.getSkillGuideItemStack(player);
					}

					@Override
					public void onClick(InventoryClickEvent evt) {}
					
				});
			}
		}
	}



}
