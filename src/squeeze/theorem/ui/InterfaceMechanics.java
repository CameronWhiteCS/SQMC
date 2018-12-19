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
		if (evt.getCursor() == null) return;
		if (evt.getInventory().getName() == null) return;
		if (!evt.getInventory().getName().contains(ChatColor.DARK_PURPLE + "")) return;

		
		//Variable creation
		Player player = (Player) evt.getWhoClicked();
		Inventory inv = evt.getInventory();
		ItemStack stack = evt.getCurrentItem();

		//Once the event has been recognized as occuring within a UI, cancel it if it's a shift click and then return
		if(evt.isShiftClick()) {
			evt.setCancelled(true);
			return;
		}
		
		//Once the event has been recognized as occuring within a UI, cancel it if it occurs within the top inventory
		int size = player.getOpenInventory().getTopInventory().getSize();
		if(evt.getRawSlot() < size) {
			evt.setCancelled(true);
		}

		for (UserInterface ui : UserInterface.getUserInterfaces()) {
			if (ui.getTitle(player).equals(ChatColor.stripColor(inv.getTitle()))) {
				for (UIComponent comp : ui.getComponents()) {
					if (comp.getItemStack(player).equals(stack)) {

						
						comp.onClick(evt);
						
						return;

					}
				}
			}
		}
	}

	// Automatically updates outdated UI's
	// TODO: Make this function compatable with the skills menu: make it where if
	// you look up andother players skils, that interface will update as well. Right
	// now, only personalized interfaces update.
	@Override
	public void run() {

		for (PlayerData dat : DataManager.getOnlinePlayers()) {
			Player player = dat.getPlayer();
			if (player.getOpenInventory().getTitle() == null)
				continue;

			String title = player.getOpenInventory().getTopInventory().getTitle();

			for (UserInterface ui : UserInterface.userInterfaces) {

				if (ui.getInventory(player).getTitle().equals(title)) {

					ItemStack[] visibleContents = player.getOpenInventory().getTopInventory().getContents();
					ItemStack[] expectedContents = ui.getInventory(player).getContents();

					if (!visibleContents.equals(expectedContents)) {
						player.getOpenInventory().getTopInventory().setContents(expectedContents);
					}

				}

			}

		}

	}
	
}