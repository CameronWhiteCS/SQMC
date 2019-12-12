package squeeze.theorem.skill.witchcraft;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import squeeze.theorem.animation.AnimationDoubleHelix;

public class SpellTeleport extends Spell {

	private static AnimationDoubleHelix animation = new AnimationDoubleHelix(Particle.DRAGON_BREATH, 1, Math.PI / 8, 10);
	
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
		
		
		animation.animate(evt.getWhoClicked());
		evt.getWhoClicked().teleport(destination);
		animation.animate(destination);
		super.cast(evt.getWhoClicked());
		
	}

	public Location getDestination() {
		return destination;
	}

	public void setDestination(Location destination) {
		this.destination = destination;
	}
 
}
