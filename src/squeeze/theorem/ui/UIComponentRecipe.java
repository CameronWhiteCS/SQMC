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

	private SQMCRecipe sqmcRecipe;
	
	public UIComponentRecipe(SQMCRecipe sqmcRecipe) {
		this.sqmcRecipe = sqmcRecipe;
	}

	@Override
	public ItemStack getItemStack(Player player) {
		return sqmcRecipe.getUIItemStack(player);
	}
	
	@Override
	public void onClick(InventoryClickEvent evt) {
		DataManager dataManager = DataManager.getInstance();
		Player player = (Player) evt.getWhoClicked();
		PlayerData playerData = dataManager.getPlayerData(player);
		SessionData sessionData = playerData.getSessionData();
		if(sqmcRecipe.canCraft(player, true)) {
			sessionData.setRecipe(sqmcRecipe);
			player.sendMessage(ChatColor.GREEN + "You are now crafting " + sqmcRecipe.getOutput().getName() + ChatColor.GREEN + ".");
			player.closeInventory();
		}
	}

	public SQMCRecipe getSQMCRecipe() {
		return sqmcRecipe;
	}

	public void setSQMCRecipe(SQMCRecipe customRecipe) {
		this.sqmcRecipe = customRecipe;
	}

}
