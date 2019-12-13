package squeeze.theorem.ui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.data.SessionData;
import squeeze.theorem.event.SQMCRecipeEvent;

public class InterfaceManager implements Listener {

	private static InterfaceManager instance = new InterfaceManager();
	
	private InterfaceManager() {
		
	}
	
	/* Events */
	@EventHandler
	public static void onInventoryClick(InventoryClickEvent evt) {

		if (evt == null) return;
		if (evt.getCurrentItem() == null) return;
		
		Player player = (Player) evt.getWhoClicked();
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player);
		SessionData sdat = dat.getSessionData();
		ChestInterface chestInterface = sdat.getChestInterface();
		int rawSlot = evt.getRawSlot();
		
		//Prevent item smuggling
		if(sdat.getInterfaceInventory() != evt.getClickedInventory()) {
			return;
		} else {
			evt.setCancelled(true);
		}
		
		//Navigation buttons
		if(rawSlot == 45) {
			player.getOpenInventory().close();
			return;
		} else if(rawSlot == 52) {
			if(sdat.getUIpage() > 0) {
				sdat.setUIpage(sdat.getUIpage() - 1);
				chestInterface.open(player, sdat.getUIpage());	
			}
			return;
		} else if(rawSlot == 53){
			if(sdat.getUIpage() != chestInterface.getPageCount()) {
				sdat.setUIpage(sdat.getUIpage() + 1);
				chestInterface.open(player, sdat.getUIpage());
			}
			return;
		}
		
		int pageNumber = sdat.getUIpage();
		int index = pageNumber * chestInterface.getPageSize() + rawSlot;
		UIComponent comp = chestInterface.getComponent(index);
		if(comp != null) {
			comp.onClick(evt);
			chestInterface = sdat.getChestInterface();
			if(chestInterface != null) chestInterface.refresh(player);
		}
		
		
	}
	
	/*UPDATING OUT OF DATE INTERFACES*/
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onCraft(SQMCRecipeEvent evt) {
		Player player = evt.getPlayer();
		DataManager dataManager = DataManager.getInstance();
		SessionData sdat = dataManager.getPlayerData(player).getSessionData();
		Inventory interfaceInventory = sdat.getInterfaceInventory();
		ChestInterface chestInterface = sdat.getChestInterface();
		if(interfaceInventory == null) return;
		if(player.getOpenInventory() == null) return;
		if(interfaceInventory == player.getOpenInventory().getTopInventory()) {
			chestInterface.refresh(player);
		}
	}
	
	public static InterfaceManager getInstance() {
		return instance;
	}
	
}
