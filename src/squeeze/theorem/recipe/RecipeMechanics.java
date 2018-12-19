package squeeze.theorem.recipe;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.data.SessionData;
import squeeze.theorem.event.SQMCRecipeEvent;

public class RecipeMechanics implements Runnable, Listener {

	@Override
	public void run() {

			for(PlayerData dat: DataManager.getOnlinePlayers()) {
				SessionData sdat = dat.getSessionData();
				if(sdat.getRecipe() != null) {
					Bukkit.getPluginManager().callEvent(new SQMCRecipeEvent(sdat.getRecipe(), dat.getPlayer()));
				}
			}
		
		
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent evt) {
		if(evt.isCancelled()) return;
		Location from = evt.getFrom();
		Location to = evt.getTo();
		if(to.getX() == from.getX() && from.getY() == to.getY() && from.getZ() == to.getZ()) return;
		PlayerData dat = DataManager.getPlayerData(evt.getPlayer().getUniqueId());
		if(dat.getSessionData().getRecipe() != null) dat.getSessionData().setRecipe(null);
	}
	
}
