package squeeze.theorem.ui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import squeeze.theorem.recipe.RecipeType;
import squeeze.theorem.recipe.SQMCRecipe;

public class UserInterfaceSkillguideSmithing extends ChestInterface {

	public UserInterfaceSkillguideSmithing(String title) {
		super(title);
		for (SQMCRecipe cr : SQMCRecipe.getRecipes()) {
			if (cr.getRecipeType() == RecipeType.SMITHING_ANVIL || cr.getRecipeType() == RecipeType.SMITHING_FURNACE) {
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
