package squeeze.theorem.skill.witchcraft;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.skill.Skill;

public class SpellNone extends Spell {

	public SpellNone() {
		super("None", "Clears your current spell", Material.BARRIER);
		this.addRequirement(Skill.witchcraft, 1);
		this.setXP(0);
		this.setCost(0);
		this.setCooldown(0);
	}

	@Override
	public void onClick(InventoryClickEvent evt) {
		Player player = (Player) evt.getWhoClicked();
		PlayerData dat = DataManager.getPlayerData(player.getUniqueId());
		int slot = player.getInventory().getHeldItemSlot();
		dat.getSessionData().setSpell(slot, null);
		player.sendMessage(ChatColor.GREEN + "Spell unbound from slot " + (slot + 1) + ".");
		player.closeInventory();
	}
	
	@Override
	public void cast(Entity e) {
		
	}
	
}
