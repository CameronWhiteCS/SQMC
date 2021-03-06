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
import squeeze.theorem.entity.EntityManager;
import squeeze.theorem.entity.SQMCEntity;
import squeeze.theorem.ui.ChestInterface;

public class SkillRanged extends Skill implements Listener {
	
	public SkillRanged() {
		this.setMaterial(Material.BOW);
		this.setName("Ranged");
	}
	
	@Override
	public ChestInterface getSkillGuide(Player player) {
		return ChestInterface.skillguideRanged;
	}

	@EventHandler
	public void onBowShoot(EntityShootBowEvent evt) {
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
			
			SQMCEntity cust = EntityManager.getInstance().getSQMCEntity(entity);
			if(cust == null) return;
			if(cust instanceof CombatStats == false) return;
			CombatStats cs = (CombatStats) cust;
			accuracy += cs.getAccuracy(AttackStyle.RANGED);
			strength += cs.getStrength(AttackStyle.RANGED);
			
		}
		
		CombatManager.getInstance().setAccuracy((Projectile) evt.getProjectile(), accuracy);
		CombatManager.getInstance().setStrength((Projectile) evt.getProjectile(), strength);
		CombatManager.getInstance().addMultiplier((Projectile) evt.getProjectile(), (double) evt.getForce());
		
	}
	
	@EventHandler
	public void onBowShoot(ProjectileLaunchEvent evt) {
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
			
			SQMCEntity cust = EntityManager.getInstance().getSQMCEntity(entity);
			if(cust == null) return;
			if(cust instanceof CombatStats == false) return;
			CombatStats cs = (CombatStats) cust;
			accuracy += cs.getAccuracy(AttackStyle.RANGED);
			strength += cs.getStrength(AttackStyle.RANGED);
			
		}

		CombatManager.getInstance().setAccuracy(evt.getEntity(), accuracy);
		CombatManager.getInstance().setStrength(evt.getEntity(), strength);
	
		
	}
	
}
