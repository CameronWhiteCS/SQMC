package squeeze.theorem.ui.settings;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import squeeze.theorem.ui.UIComponent;
import squeeze.theorem.ui.UserInterface;

public class UserInterfaceSettings extends UserInterface {

	/*Fields*/
	private List<UIComponent> components = new ArrayList<UIComponent>();
	
	/*Constructors*/
	public UserInterfaceSettings() {
		components.add(new UIComponentSettingsHUD());
		components.add(new UIComponentSettingsCombatMode());
		components.add(new UIComponentSettingsMusic());
		components.add(new UIComponentSettingsXPBars());
	}
	

	@Override
	public void open(Player player) {
		player.openInventory(getInventory(player));
		
	}

	@Override
	public Inventory getInventory(Player player) {
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_PURPLE + getTitle(player));
		
		for(UIComponent comp: getComponents()) {
			inv.addItem(comp.getItemStack(player));
		}
		
		return inv;
	}

	@Override
	public List<UIComponent> getComponents() {
		return this.components;
	}

	@Override
	public String getTitle(Player player) {
		return"Game Settings" + appendID();
	}

}
