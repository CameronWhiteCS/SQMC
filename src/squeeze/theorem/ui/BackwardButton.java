package squeeze.theorem.ui;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.data.SessionData;

public class BackwardButton implements UIComponent {

	@Override
	public ItemStack getItemStack(Player player) {
		ItemStack output = new ItemStack(Material.RED_WOOL);
		ItemMeta meta = output.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		
		lore.add(ChatColor.RED + "<---");
		meta.setDisplayName(ChatColor.DARK_PURPLE + "Previous");
		meta.setLore(lore);
		output.setItemMeta(meta);
		return output;
	}

	@Override
	public void onClick(InventoryClickEvent evt) {

		Player player = (Player) evt.getWhoClicked();
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
		SessionData sdat = dat.getSessionData();
		if(sdat.getUIpage() == 0) return;
		sdat.setUIpage(sdat.getUIpage() - 1);
		
	}

}
