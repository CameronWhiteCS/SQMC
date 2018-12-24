package squeeze.theorem.skill;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.ui.UserInterface;

public class SkillHitpoints extends Skill implements Runnable, Listener {

	public SkillHitpoints() {
		this.setName("Hitpoints");
		this.setMaterial(Material.COOKED_BEEF);
	}
	
	@Override
	public UserInterface getSkillGuide(Player player) {
		return UserInterface.skillguideHitpoints;
	}

	@Override
	public void run() {
		
		for(PlayerData dat: DataManager.getOnlinePlayers()) {
			Player player = dat.getPlayer();
			AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
			attribute.setBaseValue(dat.getMaxHealth());
			
			double maxHeal = attribute.getBaseValue() - player.getHealth();
			double healAmount = dat.getHPPerMinute() / 1200;
			if(healAmount > maxHeal) healAmount = maxHeal;
			
			if(!player.isDead()) player.setHealth(player.getHealth() + healAmount);
			
			
		}
		
	}
	
	@EventHandler
	public void onHeal(EntityRegainHealthEvent evt) {
		
		if(evt.getEntity() instanceof Player && evt.getRegainReason().equals(RegainReason.SATIATED)){
			evt.setCancelled(true);
		}
		
		
	}

}
