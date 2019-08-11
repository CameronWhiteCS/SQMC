package squeeze.theorem.ui;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.data.SessionData;
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
		
		DataManager dataManager = DataManager.getInstance();
		Player player = (Player) evt.getWhoClicked();
		PlayerData playerData = dataManager.getPlayerData(player);
		SessionData sessionData = playerData.getSessionData();
		if(customRecipe.canCraft(player, true)) {
			sessionData.setRecipe(customRecipe);
			player.sendMessage(ChatColor.GREEN + "You are now crafting " + customRecipe.getOutput().getName() + ChatColor.GREEN + ".");
			player.closeInventory();
		}
	}

	public SQMCRecipe getSQMCRecipe() {
		return customRecipe;
	}

	public void setSQMCRecipe(SQMCRecipe customRecipe) {
		this.customRecipe = customRecipe;
	}

}
