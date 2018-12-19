package squeeze.theorem.ui.settings;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.ui.UIComponent;

public class UIComponentSettingsHUD implements UIComponent {

	@Override
	public ItemStack getItemStack(Player player) {
		PlayerData dat = DataManager.getPlayerData(player.getUniqueId());
		String status;
		Material material;
		if(dat.getHud() == true) {
			status = ChatColor.GREEN + "Enabled";
			material = Material.LIME_STAINED_GLASS_PANE;
		} else {
			status = ChatColor.RED + "Disabled";
			material = Material.RED_STAINED_GLASS_PANE;
		}
		ItemStack output = new ItemStack(material);
		ItemMeta meta = output.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(status);
		meta.setDisplayName(ChatColor.GOLD + "HUD");
		meta.setLore(lore);
		output.setItemMeta(meta);
		return output;
	
		
	}

	@Override
	public void onClick(InventoryClickEvent evt) {
		
		PlayerData dat = DataManager.getPlayerData(evt.getWhoClicked().getUniqueId());
		if(dat.getHud() == false) {
			dat.setHud(true);
		} else {
			dat.setHud(false);
		}
		
	}

}
