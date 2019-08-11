package squeeze.theorem.skill;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.entity.SQMCEntity;
import squeeze.theorem.event.FireLightEvent;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.ui.ChestInterface;

public class SkillFiremaking extends Skill implements Listener, Runnable {
	
	public SkillFiremaking() {
		this.setName("Firemaking");
		this.setMaterial(Material.FLINT_AND_STEEL);
	}

	//Ignites fires when a flint and steel is in your hand
	@EventHandler
	public void onRightClick(PlayerInteractEvent evt) {

		Player player = evt.getPlayer();

		if (evt.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !evt.isCancelled()) {

			CustomItem mainhand = CustomItem.getCustomItem(player.getInventory().getItemInMainHand());
			CustomItem offhand = CustomItem.getCustomItem(player.getInventory().getItemInOffHand());

			// Flint check
			if (mainhand == null) return;
			if (mainhand != CustomItem.FLINT_AND_STEEL) return;

			// Null offhand check
			if (offhand == null) {
				player.sendMessage(ChatColor.RED + "You'll need logs in your offhand slot to use a flint and steel");
				evt.setCancelled(true);
				return;
			}

			//Make sure there's no nearby fires
			Location loc = evt.getClickedBlock().getLocation();
			Collection<Entity> entities = loc.getWorld().getNearbyEntities(loc, 2, 2, 2);
			for(Entity e: entities) {
				SQMCEntity ce = SQMCEntity.getSQMCEntity(e);
				if(ce != null) {
					if(ce instanceof SQMCEntityFire) {
						player.sendMessage(ChatColor.RED + "You can't light a fire that close to another fire.");
						evt.setCancelled(true);
						return;
					}
				}
			}
			
			//Find matching fire, the call custom event
			for (SQMCEntityFire cef : SQMCEntityFire.getFires()) {
				if (cef.getCustomItem().equals(offhand)) {

					if(!cef.meetsRequirements(player)) {
						cef.sendInsufficientLevelNotice(player, "light " + cef.getCustomItem().getName() + ".");
						evt.setCancelled(true);
						return;
					}
					
					Bukkit.getPluginManager().callEvent(new FireLightEvent(player, cef, evt.getClickedBlock().getLocation(), player.getInventory().getItemInOffHand()));
					evt.setCancelled(true);
					return;
										
				}
			}

			//If no matches are found, inform player they need valid logs.
			player.sendMessage(ChatColor.RED + "You'll need logs in the offhand slot to use flint and steel.");
			evt.setCancelled(true);

		}

	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onLight(FireLightEvent evt) {
		if(!evt.isCancelled()) {
			evt.getFire().spawn(evt.getLocation());
			DataManager dataManager = DataManager.getInstance();
			PlayerData dat = dataManager.getPlayerData(evt.getPlayer().getUniqueId());
			dat.awardXP(Skill.firemaking, evt.getXP());
			evt.getLogs().setAmount(evt.getLogs().getAmount() - 1);
		}
		
	}

	//Removes old fires, and drop ashes
	@Override
	public void run() {

		for (World w : Bukkit.getWorlds()) {
			for (Entity e : w.getEntities()) {
					
					if (SQMCEntity.getSQMCEntity(e) == null) continue;
					SQMCEntity cust = SQMCEntity.getSQMCEntity(e);
					if (cust instanceof SQMCEntityFire) {
						SQMCEntityFire custFire = (SQMCEntityFire) cust;
						if (e.getTicksLived() > custFire.getBurnTime()) {
							
						if(e instanceof LivingEntity) {
							((LivingEntity) e).setHealth(0);
						}
						
						
					}
				}
			}
		}

	}

	@Override
	public ChestInterface getSkillGuide(Player player) {
		return ChestInterface.skillguideFiremaking;
	}

}
