package squeeze.theorem.ui;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;

public class InterfaceMechanics implements Runnable, Listener {

	/* Events */
	@EventHandler
	public static void onInventoryClick(InventoryClickEvent evt) {
		
		//Null checks
		if (evt == null) return;
		if (evt.getCurrentItem() == null) return;
		if (evt.getInventory().getName() == null) return;
		if (evt.getInventory().getName().contains("ID:") == false) return;

		if(evt.isShiftClick()) {
			evt.setCancelled(true);
			return;
		}
		
		//Variable creation
		Player player = (Player) evt.getWhoClicked();
		Inventory inv = evt.getInventory();
		ItemStack stack = evt.getCurrentItem();
		
		//Once the event has been recognized as occuring within a UI, cancel it if it occurs within the top inventory
		int size = player.getOpenInventory().getTopInventory().getSize();
		if(evt.getRawSlot() < size) {
			evt.setCancelled(true);
		}
		
		int ID = Integer.parseInt(ChatColor.stripColor(inv.getTitle()).split("ID:")[1]);
		for (UserInterface ui : UserInterface.getUserInterfaces()) {
			if(ID != ui.getID()) continue;
				for (UIComponent comp : ui.getComponents()) {
					if (comp.getItemStack(player).equals(stack)) {

						
						comp.onClick(evt);
						
						return;

					}
				}
			
		}
	}

	
	@Override
	public void run() {

		DataManager dataManager = DataManager.getInstance();
		for (PlayerData dat : dataManager.getOnlinePlayers()) {
			for(UserInterface ui: UserInterface.getUserInterfaces()) {
				ui.refresh(dat.getPlayer());
			}

		}

	}
	
}
