package squeeze.theorem.skill.witchcraft;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import squeeze.theorem.animation.Animations;

public class SpellTeleport extends Spell {

	private Location destination;
	
	public SpellTeleport(String name, String desc, Material m, Location destination) {
		super(name, desc, m);
		setSound(Sound.ENTITY_ENDERMAN_TELEPORT);
		setDestination(destination);
	}
	
	@Override
	public void onClick(InventoryClickEvent evt) {
		
		//Cooldown check
		Player player = (Player) evt.getWhoClicked();
		if(this.getCooldownTimer().isOnCooldown(player.getUniqueId())) {
			getCooldownTimer().sendCooldownMessage(player);
			player.closeInventory();
			return;
		}
		
		
		Animations.doubleHelix(evt.getWhoClicked().getLocation(), 1, 10, Math.PI / 8, Particle.DRAGON_BREATH);
		evt.getWhoClicked().teleport(destination);
		Animations.doubleHelix(destination, 1, 10, Math.PI / 16, Particle.DRAGON_BREATH);
		super.cast(evt.getWhoClicked());
			
		
		
	}

	public Location getDestination() {
		return destination;
	}

	public void setDestination(Location destination) {
		this.destination = destination;
	}
 
}
