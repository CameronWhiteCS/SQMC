package squeeze.theorem.skill;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import squeeze.theorem.combat.AttackStyle;
import squeeze.theorem.combat.CombatManager;
import squeeze.theorem.combat.CombatStats;
import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.entity.SQMCEntity;
import squeeze.theorem.ui.UserInterface;

public class SkillRanged extends Skill implements Listener {
	
	public SkillRanged() {
		this.setMaterial(Material.BOW);
		this.setName("Ranged");
	}
	
	@Override
	public UserInterface getSkillGuide(Player player) {
		return UserInterface.skillguideRanged;
	}

	@EventHandler
	public static void onBowShoot(EntityShootBowEvent evt) {
		Entity entity = evt.getEntity();
		if(evt.getProjectile() instanceof Arrow == false) return;
		double accuracy = 0.0;
		double strength = 0.0;
		
		if(entity instanceof Player) {
			DataManager dataManager = DataManager.getInstance();
			PlayerData dat = dataManager.getPlayerData(entity.getUniqueId());
			accuracy += dat.getAccuracy(AttackStyle.RANGED);
			strength += dat.getStrength(AttackStyle.RANGED);
		} else {
			
			SQMCEntity cust = SQMCEntity.getSQMCEntity(entity);
			if(cust == null) return;
			if(cust instanceof CombatStats == false) return;
			CombatStats cs = (CombatStats) cust;
			accuracy += cs.getAccuracy(AttackStyle.RANGED);
			strength += cs.getStrength(AttackStyle.RANGED);
			
		}
		
		CombatManager.setAccuracy((Projectile) evt.getProjectile(), accuracy);
		CombatManager.setStrength((Projectile) evt.getProjectile(), strength);
		CombatManager.addMultiplier((Projectile) evt.getProjectile(), (double) evt.getForce());
		
	}
	
	@EventHandler
	public static void onBowShoot(ProjectileLaunchEvent evt) {
		if(evt.getEntity() instanceof Trident == false) return;
		if(evt.getEntity().getShooter() instanceof Entity == false) return;
		
		Entity entity = (Entity) evt.getEntity().getShooter();
		
		double accuracy = 0.0;
		double strength = 0.0;
		
		if(entity instanceof Player) {
			DataManager dataManager = DataManager.getInstance();
			PlayerData dat = dataManager.getPlayerData(entity.getUniqueId());
			accuracy += dat.getAccuracy(AttackStyle.RANGED);
			strength += dat.getStrength(AttackStyle.RANGED);
		} else {
			
			SQMCEntity cust = SQMCEntity.getSQMCEntity(entity);
			if(cust == null) return;
			if(cust instanceof CombatStats == false) return;
			CombatStats cs = (CombatStats) cust;
			accuracy += cs.getAccuracy(AttackStyle.RANGED);
			strength += cs.getStrength(AttackStyle.RANGED);
			
		}

		CombatManager.setAccuracy(evt.getEntity(), accuracy);
		CombatManager.setStrength(evt.getEntity(), strength);
	
		
	}
	
}
