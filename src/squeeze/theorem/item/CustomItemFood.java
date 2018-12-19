package squeeze.theorem.item;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import squeeze.theorem.mechanics.Cooldown;

public class CustomItemFood extends CustomItem {

	private double healAmount;
	
	
	public CustomItemFood(int ID, String name, Material material, double healAmount, String...lore) {
		super(ID, name, material, lore);
		setHealAmount(healAmount);
		
	}

	public double getHealAmount() {
		return healAmount;
	}

	public void setHealAmount(double healAmount) {
		this.healAmount = healAmount;
	}
	
	@Override
	public void onRightClick(PlayerInteractEvent evt) {
		
		if(evt.getHand().equals(EquipmentSlot.OFF_HAND)) return;
		Player player = evt.getPlayer();
		UUID id = player.getUniqueId();
		Cooldown cooldown = Cooldown.food;
		ItemStack stack = evt.getPlayer().getInventory().getItemInMainHand();
		
		if(evt.getPlayer().getHealth() == evt.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()) {
			player.sendMessage(ChatColor.RED + "You're at full health already, eating would be pointless.");
			return;
		}
		
		if(cooldown.isOnCooldown(id)) {
			cooldown.sendCooldownMessage(player);
		} else {
			stack.setAmount(stack.getAmount() - 1);
			cooldown.addPlayer(id, 100);
			double healAmount = getHealAmount();
			double difference = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - player.getHealth();
			if(difference < healAmount) healAmount = difference;
			player.setHealth(player.getHealth() + healAmount);
		}
		
	}

}
