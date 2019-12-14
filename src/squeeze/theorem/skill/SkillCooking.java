package squeeze.theorem.skill;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.entity.EntityManager;
import squeeze.theorem.entity.SQMCEntity;
import squeeze.theorem.recipe.RecipeType;
import squeeze.theorem.skill.firemaking.SQMCEntityFire;
import squeeze.theorem.ui.ChestInterface;

public class SkillCooking extends Skill implements Listener, Runnable {

	private static HashMap<Player, Entity> fireUsers = new HashMap<Player, Entity>();
	
	public SkillCooking() {
		setName("Cooking");
		setMaterial(Material.BREAD);
	}

	@Override
	public ChestInterface getSkillGuide(Player player) {
		return ChestInterface.skillguideCooking;
	}

	@EventHandler(priority=EventPriority.LOW)
	public void onFireClick(PlayerInteractEntityEvent evt) {

		if (EntityManager.getInstance().getSQMCEntity(evt.getRightClicked()) != null && !evt.isCancelled()) {
			SQMCEntity ce = EntityManager.getInstance().getSQMCEntity(evt.getRightClicked());
			if (ce instanceof SQMCEntityFire) {

				ChestInterface.fire.open(evt.getPlayer());
				addFireUser(evt.getPlayer(), evt.getRightClicked());
				evt.setCancelled(true);
			}
		}

	}
	
	public static void addFireUser(Player player, Entity entity) {
		fireUsers.put(player, entity);
	}

	public static HashMap<Player, Entity> getFireUsers() {
		return fireUsers;
	}

	@Override
	public void run() {
		
		for(Player player: fireUsers.keySet()) {
			Entity entity = fireUsers.get(player);

			if(!entity.isDead()) continue;
			if(player.getOpenInventory().getTitle().equals(ChatColor.DARK_PURPLE + "Fire")) {
				notifyBurnout(player);
				continue;
			}
			
			DataManager dataManager = DataManager.getInstance();
			PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
			if(dat.getSessionData().getRecipe() == null) continue;
			if(dat.getSessionData().getRecipe().getRecipeType() == RecipeType.COOKING_FIRE) {
				notifyBurnout(player);
				continue;
			}
					
		}
		
	}
	
	private static void notifyBurnout(Player player){
		player.sendMessage(ChatColor.RED + "The fire burns out, leaving behind a piles of ashes.");
		DataManager dataManager = DataManager.getInstance();
		dataManager.getPlayerData(player.getUniqueId()).getSessionData().setRecipe(null);
		getFireUsers().remove(player);
		player.closeInventory();
	}
	
}
