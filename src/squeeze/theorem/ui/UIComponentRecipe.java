package squeeze.theorem.ui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import squeeze.theorem.recipe.SQMCRecipe;

public class UIComponentRecipe implements UIComponent {

	private SQMCRecipe customRecipe;
	
	public UIComponentRecipe(SQMCRecipe cr) {
		setSQMCRecipe(cr);
	}

	@Override
	public ItemStack getItemStack(Player player) {
		return customRecipe.getUIItemStack(player);
	}

	@Override
	public void onClick(InventoryClickEvent evt) {

		SQMCRecipe r = getSQMCRecipe();
		r.onRecipeClick(evt);
		

	}

	public SQMCRecipe getSQMCRecipe() {
		return customRecipe;
	}

	public void setSQMCRecipe(SQMCRecipe customRecipe) {
		this.customRecipe = customRecipe;
	}

}
