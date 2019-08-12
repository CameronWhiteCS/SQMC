package squeeze.theorem.recipe;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.data.SessionData;
import squeeze.theorem.event.SQMCRecipeEvent;
import squeeze.theorem.skill.Skill;
import squeeze.theorem.ui.ChestInterface;

public class RecipeManager implements Runnable, Listener {

	private static RecipeManager instance = new RecipeManager();
	
	private RecipeManager() {
		
	}
	
	@Override
	public void run() {
		DataManager dataManager = DataManager.getInstance();
		for(PlayerData dat: dataManager.getOnlinePlayers()) {
			SessionData sdat = dat.getSessionData();
			if(sdat.getRecipe() != null) {
				Bukkit.getPluginManager().callEvent(new SQMCRecipeEvent(sdat.getRecipe(), dat.getPlayer()));
			}
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent evt) {
		if(evt.isCancelled()) return;
		DataManager dataManager = DataManager.getInstance();
		Location craftingLocation = dataManager.getPlayerData(evt.getPlayer().getUniqueId()).getSessionData().getCraftingLocation();
		if(craftingLocation == null) return;
		Location to = evt.getTo();
		if(craftingLocation.distance(to) < 2) return;
		PlayerData dat = dataManager.getPlayerData(evt.getPlayer().getUniqueId());
		if(dat.getSessionData().getRecipe() != null) dat.getSessionData().setRecipe(null);
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onCustomRecipe(SQMCRecipeEvent evt) {
		if(!evt.isCancelled() && evt.getSQMCRecipe().canCraft(evt.getPlayer(), false)) {
			evt.getSQMCRecipe().craftItem(evt.getPlayer());
			for (Skill s : evt.getXpRewards().keySet()) {
				DataManager dataManager = DataManager.getInstance();
				dataManager.getPlayerData(evt.getPlayer().getUniqueId()).awardXP(s, evt.getXpRewards().get(s));
				
			}
		} else {
			DataManager dataManager = DataManager.getInstance();
			SessionData dat = dataManager.getPlayerData(evt.getPlayer().getUniqueId()).getSessionData();
			dat.setRecipe(null);
		}
		
	}
	
	public static RecipeManager getInstance() {
		return instance;
	}
	
}
