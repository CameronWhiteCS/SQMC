package squeeze.theorem.skill;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import squeeze.theorem.ui.ChestInterface;

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
			ChestInterface.anvil.open(evt.getPlayer());
			evt.setCancelled(true);
		}
		
		//Furnace interface
		if(m.equals(Material.FURNACE) && evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			evt.getPlayer().closeInventory();
			ChestInterface.furnace.open(evt.getPlayer());
			evt.setCancelled(true);
		}
	}

	@Override
	public ChestInterface getSkillGuide(Player player) {
		return ChestInterface.skillguideSmithing;
	}

}
