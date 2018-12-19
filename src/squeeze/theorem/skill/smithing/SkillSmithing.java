package squeeze.theorem.skill.smithing;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import squeeze.theorem.skill.Skill;
import squeeze.theorem.ui.UserInterface;

public class SkillSmithing extends Skill implements Listener {

	
	
	public SkillSmithing() {
		this.setMaterial(Material.ANVIL);
		setName("Smithing");
	}

	@EventHandler(priority=EventPriority.LOW)
	public void onInteract(PlayerInteractEvent evt) {
		if(evt.getClickedBlock() == null) return;
		if(evt.isCancelled()) return;
		Material m = evt.getClickedBlock().getType();
		
		//Anvil interface
		if(m.equals(Material.ANVIL) && evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			evt.getPlayer().closeInventory();
			UserInterface.anvil.open(evt.getPlayer());
			evt.setCancelled(true);
		}
		
		//Furnace interface
		if(m.equals(Material.FURNACE) && evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			evt.getPlayer().closeInventory();
			UserInterface.furnace.open(evt.getPlayer());
			evt.setCancelled(true);
		}
	}

	@Override
	public UserInterface getSkillGuide(Player player) {
		return UserInterface.skillguideSmithing;
	}

}
