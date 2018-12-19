package squeeze.theorem.skill.witchcraft;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import squeeze.theorem.combat.CombatManager;
import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.data.SessionData;
import squeeze.theorem.item.CombatItem;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.skill.Skill;
import squeeze.theorem.ui.UserInterface;

public class SkillWitchcraft extends Skill implements Listener, Runnable {
	
	private int ticksSoFar = 0;
	
	public SkillWitchcraft() {

		this.setMaterial(Material.CAULDRON);
		this.setName("Witchcraft");
	}
	
	@Override
	public UserInterface getSkillGuide(Player player) {
		return UserInterface.skillguideWitchcraft;
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onRightClick(PlayerInteractEvent evt) {
		
		Player player = evt.getPlayer();
		PlayerData dat = DataManager.getPlayerData(player.getUniqueId());
		SessionData sessionData = dat.getSessionData();
		
		if(evt.getItem() == null) return;
		if(evt.isCancelled() && evt.getAction() != Action.RIGHT_CLICK_AIR) return;
		if(evt.getHand() != EquipmentSlot.HAND) return;
		
		CustomItem ci = CustomItem.getCustomItem(evt.getItem());
		if(ci == null) return;
		if(ci instanceof CombatItem == false) return;
		if(((CombatItem) ci).isWand() == false) return;
		
		int slot = player.getInventory().getHeldItemSlot();
		Spell spell = sessionData.getSpell(slot);
		if(spell == null) return;
		if(!spell.canCast(player)) {
			player.closeInventory();
			 return;
		}
		
		spell.cast(player);
		
		
	}
	
	@EventHandler
	public static void onHit(EntityDamageByEntityEvent evt) {
		if(evt.isCancelled()) return;
		if(evt.getDamager() instanceof Snowball == false) return;
		Projectile proj = (Projectile) evt.getDamager();
		Spell spell = CombatManager.getSpell(proj);
		if(spell == null) return;
		if(spell instanceof SpellProjectile) {
			((SpellProjectile) spell).onHit(proj, evt.getEntity());
		}
		
	}
	
	/*Scheduler stuff*/
	public void run() {
		animateOrbSpells();
		manaRegen();
	}
	
	private void animateOrbSpells() {
		
		for(World w: Bukkit.getWorlds()) {
			for(Projectile proj: w.getEntitiesByClass(Projectile.class)) {
				Spell spell = CombatManager.getSpell(proj);
				if(spell == null) continue;
				if(spell instanceof SpellProjectile == false) continue;
				((SpellProjectile) spell).animate(proj.getLocation());
			}
		}
		
	}
	
	private void manaRegen() {
	
		if(ticksSoFar < 9) {
			ticksSoFar++;
			return;
		}

		for(PlayerData dat: DataManager.getOnlinePlayers()) {
			
			int mana = dat.getMana();
			int maxIncrease = dat.getMaxMana() - mana;
			if(dat.getManaPerSecond() > maxIncrease) {
				dat.setMana(dat.getMana() + maxIncrease);
			} else {
				dat.setMana(dat.getManaPerSecond() + dat.getMana());
			}
			
			ticksSoFar = 0;
		}
		
		
	}
	
	
}
