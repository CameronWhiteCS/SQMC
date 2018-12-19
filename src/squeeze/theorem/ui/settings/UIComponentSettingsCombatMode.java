package squeeze.theorem.ui.settings;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import squeeze.theorem.combat.CombatMode;
import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.ui.UIComponent;

public class UIComponentSettingsCombatMode implements UIComponent {

	@Override
	public ItemStack getItemStack(Player player) {
		PlayerData dat = DataManager.getPlayerData(player.getUniqueId());
		
		Material material;
		String status;
		CombatMode mode = dat.getCombatMode();
		if(mode == CombatMode.AGGRESSIVE) {
			material = Material.IRON_SWORD;
			status = ChatColor.RED + "Aggressive";
		} else if (mode == CombatMode.DEFENSIVE) {
			material = Material.SHIELD;
			status = ChatColor.AQUA + "Defensive";
		} else {
			material = Material.TRIDENT;
			status = ChatColor.GREEN + "Split";
		}
		
		ItemStack output = new ItemStack(material);
		ItemMeta meta = output.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Combat mode");
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		List<String> lore = new ArrayList<String>();
		lore.add(status);
		meta.setLore(lore);
		output.setItemMeta(meta);
		return output;
		
	}

	@Override
	public void onClick(InventoryClickEvent evt) {
		
		PlayerData dat = DataManager.getPlayerData(evt.getWhoClicked().getUniqueId());
		CombatMode mode = dat.getCombatMode();
		
		if(mode == CombatMode.AGGRESSIVE) {
			dat.setCombatMode(CombatMode.DEFENSIVE);
		}
		
		if(mode == CombatMode.DEFENSIVE) {
			dat.setCombatMode(CombatMode.SPLIT);
		}
		
		if(mode == CombatMode.SPLIT) {
			dat.setCombatMode(CombatMode.AGGRESSIVE);
		}
		
	}

}
