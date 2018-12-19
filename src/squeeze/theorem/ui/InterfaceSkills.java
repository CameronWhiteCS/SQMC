package squeeze.theorem.ui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.skill.Skill;

public class InterfaceSkills extends UserInterface {

	private ArrayList<UIComponent> components = new ArrayList<UIComponent>();

	public InterfaceSkills() {
		for(Skill s: Skill.getSkills()) {
			this.components.add(new UIComponentSkillIcon(s));
		}
	}
	
	@Override
	public String getTitle(Player player) {
		PlayerData dat = DataManager.getPlayerData(player.getUniqueId());
		return player.getName() + "'s Skills [" + dat.getTotalLevel() + "]";
	}

	@Override
	public void open(Player player) {
		player.openInventory(getInventory(player));
		
	}

	@Override
	public Inventory getInventory(Player player) {

		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_PURPLE + getTitle(player));
		for(UIComponent comp: components) {
			inv.addItem(comp.getItemStack(player));
		}
		return inv;
	}

	@Override
	public List<UIComponent> getComponents() {
		return this.components;
	}
	
}
