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

public class UIComponentSettingsMusic implements UIComponent {

	@Override
	public ItemStack getItemStack(Player player) {
		
		Material material;
		String status;
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
		boolean music = dat.getMusic();
		
		if(music) {
			material = Material.JUKEBOX;
			status = ChatColor.GREEN + "Enabled";
		} else {
			material = Material.NOTE_BLOCK;
			status = ChatColor.RED + "Disabled";
		}
		
		ItemStack output = new ItemStack(material);
		ItemMeta meta = output.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Music");
		List<String> lore = new ArrayList<String>();
		lore.add(status);
		meta.setLore(lore);
		output.setItemMeta(meta);
		return output;
		
		
	}

	@Override
	public void onClick(InventoryClickEvent evt) {
		
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(evt.getWhoClicked().getUniqueId());
		if(dat.getMusic()) {
			dat.setMusic(false);
		} else {
			dat.setMusic(true);
		}
		
	}

}
