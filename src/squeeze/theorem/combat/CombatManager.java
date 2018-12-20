package squeeze.theorem.combat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import squeeze.theorem.animation.Animations;
import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.entity.Boundable;
import squeeze.theorem.entity.SQMCEntity;
import squeeze.theorem.item.CombatItem;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.item.Lootable;
import squeeze.theorem.skill.witchcraft.Spell;

public class CombatManager implements Listener, Runnable {

	/*Static fields*/
	private static Map<Projectile, Double> strength = new ConcurrentHashMap<Projectile, Double>();
	private static Map<Projectile, Double> accuracy = new ConcurrentHashMap<Projectile, Double>();
	private static Map<Projectile, Double> multiplier = new ConcurrentHashMap<Projectile, Double>();
	private static Map<Projectile, Spell> spell = new ConcurrentHashMap<Projectile, Spell>();
	
	/*Primary method*/
	@EventHandler(priority = EventPriority.LOW)
	public static void onDamage(EntityDamageByEntityEvent evt) {
				
		/* Rule out immediately invalid damage events */
		if(evt.isCancelled()) return;
		if(evt.getDamager() instanceof Projectile == false && evt.getDamager() instanceof LivingEntity == false) return;
		if(evt.getEntity() instanceof LivingEntity == false) return;
		if(evt.getEntity().isInvulnerable() == true) {
			evt.setCancelled(true);
			return;
		}
		
		/* Determine the numbers to be used for the calculations */
		AttackStyle style = getAttackStyle(evt);
		LivingEntity damager = (LivingEntity) getDamager(evt);
		LivingEntity damagee = (LivingEntity) evt.getEntity();
		Projectile projectile = getProjectile(evt);

		double multiplier = getMultiplier(projectile);
		double accuracy = getAccuracy(style, damager, damagee, projectile);
		double damage = getDamage(style, damager, damagee, projectile, evt) * multiplier;
		double defense = getDefense(style, damagee);
		
		/* Make people abusing entity mechanics miss */
		
		SQMCEntity ce = SQMCEntity.getSQMCEntity(damagee);
		/*if (ce != null) {
			if (ce instanceof Boundable) {
				Boundable boundable = (Boundable) ce;
				Location loc = SQMCEntity.getLocation(damagee);
				if (damagee instanceof Mob) {
					Mob mob = (Mob) damagee;
					if (mob.getTarget() != null) {
						if (mob.getTarget().getLocation().distance(loc) > boundable.getWanderRadius()) {
							evt.setCancelled(true);
							if (damagee instanceof Player == false) Animations.sphere(damagee.getLocation(), 2, Math.PI / 4, Particle.DAMAGE_INDICATOR);
							return;
						}
					}
				}

				if (damager.getLocation().distance(loc) > boundable.getWanderRadius()) {
					evt.setCancelled(true);
					if (damagee instanceof Player == false) Animations.sphere(damagee.getLocation(), 2, Math.PI / 4, Particle.DAMAGE_INDICATOR);
					return;
				}

			}
		}*/
		
		
		//Defense calculations
		double defenseRoll = Math.random() * defense;
		double accuracyRoll = Math.random() * accuracy;

		/*Make player miss if they don't meet item requirements*/
		if(style == AttackStyle.MELEE && damager instanceof Player) {
			Player player = (Player) damager;
			
			mainhand:
			{
				CustomItem cust = CustomItem.getCustomItem(player.getInventory().getItemInMainHand());
				if(cust instanceof CombatItem == false) break mainhand;
				CombatItem cmbt = (CombatItem) cust;
				if(cmbt.meetsRequirements(player) == false) {
					cmbt.sendInsufficientLevelNotice(player, "use this weapon");
					accuracyRoll = 0;
					defenseRoll = 1;
				}
			}
			
			offhand:{
				CustomItem cust = CustomItem.getCustomItem(player.getInventory().getItemInOffHand());
				if(cust instanceof CombatItem == false) break offhand;
				CombatItem cmbt = (CombatItem) cust;
				if(cmbt.meetsRequirements(player) == false) {
					cmbt.sendInsufficientLevelNotice(player, "use this weapon");
					accuracyRoll = 0;
					defenseRoll = 1;
				}
			}
			
			
		}
		
		/* Dodge attacks */
		if (defenseRoll > accuracyRoll) {
			if (damagee instanceof Player == false) Animations.sphere(damagee.getLocation(), 2, Math.PI / 4, Particle.DAMAGE_INDICATOR);
			evt.setCancelled(true);
			return;
		}

		if (damagee instanceof Player && damager instanceof Player) {
			evt.setCancelled(true);
			return;
		}
		
		/* Damage the entity */
		double xpDamage;
		if (damagee.getHealth() < damage) {
			xpDamage = damagee.getHealth();
			evt.setDamage(0);
			damagee.damage(damagee.getHealth());
			if(ce instanceof Lootable) {
				((Lootable) ce).dropLoot((Player) damager, damagee.getLocation());
			}
			
		} else {
			xpDamage = damage;
			evt.setDamage(0);
			damagee.damage(damage);
		}
		
		/* Award XP to the player */
		if (damager instanceof Player) {
			PlayerData dat = DataManager.getPlayerData(damager.getUniqueId());
			if (dat != null)
				dat.awardCombatXP(style, xpDamage);
		}
		
		/*Let the victim fight back*/
		if(damagee instanceof Mob) {
			Mob mob = (Mob) damagee;
			mob.setTarget(damager);
		}

	}
	
	/*Calculation methods*/
	private static double getAccuracy(AttackStyle style, Entity damager, Entity damagee, Projectile projectile) {

		if (projectile == null) {

			if (damager instanceof Player) {
				PlayerData dat = DataManager.getPlayerData(damager.getUniqueId());
				return dat.getAccuracy(style);
			} else {
				SQMCEntity cust = SQMCEntity.getSQMCEntity(damager);
				if (cust == null)
					return 0.0;
				if (cust instanceof CombatStats == false)
					return 0.0;
				return ((CombatStats) cust).getAccuracy(style);

			}

		} else {

			return getAccuracy(projectile);

		}

	}

	private static double getDamage(AttackStyle style, Entity damager, Entity damagee, Projectile projectile, EntityDamageByEntityEvent evt) {

		if(evt.getCause() == DamageCause.ENTITY_SWEEP_ATTACK) return 1;
		
		if (projectile == null) {

			if (damager instanceof Player) {
				
				
				PlayerData dat = DataManager.getPlayerData(damager.getUniqueId());
				Player player = dat.getPlayer();
				
				double genericAttackDamage = player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue();
				
				return (dat.getStrength(style)) * (evt.getDamage() / genericAttackDamage);
				
			} else {
				SQMCEntity cust = SQMCEntity.getSQMCEntity(damager);
				if (cust == null)
					return 0.0;
				if (cust instanceof CombatStats == false)
					return 0.0;
				return ((CombatStats) cust).getStrength(style);

			}

		} else {

			return getStrength(projectile);

		}

	}

	private static double getDefense(AttackStyle style, Entity damagee) {

		if (damagee instanceof Player) {
			PlayerData dat = DataManager.getPlayerData(damagee.getUniqueId());
			return dat.getDefense(style);
		} else {
			SQMCEntity cust = SQMCEntity.getSQMCEntity(damagee);
			if (cust == null)
				return 0.0;
			if (cust instanceof CombatStats == false)
				return 0.0;
			return ((CombatStats) cust).getDefense(style);

		}

	}

	private static Projectile getProjectile(EntityDamageByEntityEvent evt) {

		if (evt.getDamager() instanceof Projectile)
			return (Projectile) evt.getDamager();

		return null;
	}

	private static Entity getDamager(EntityDamageByEntityEvent evt) {

		if (evt.getDamager() instanceof Projectile) {

			Projectile proj = (Projectile) evt.getDamager();
			if (proj.getShooter() == null)
				return null;
			if (proj.getShooter() instanceof Entity == false)
				return null;
			return (Entity) proj.getShooter();

		}

		return evt.getDamager();
	}

	private static AttackStyle getAttackStyle(EntityDamageByEntityEvent evt) {

		if(evt.getCause() == DamageCause.MAGIC || evt.getDamager() instanceof WitherSkull) return AttackStyle.MAGIC;
		
		if(evt.getDamager() instanceof Arrow) return AttackStyle.RANGED;
		
		return AttackStyle.MELEE;

	}
	
	/*Projectile manipulation methods*/
	public static void setAccuracy(Projectile proj, double value) {
		accuracy.put(proj, value);
	}

	public static double getAccuracy(Projectile proj) {
		if (accuracy.containsKey(proj)) return accuracy.get(proj);
		return 0;
	}

	public static void setStrength(Projectile proj, double value) {
		strength.put(proj, value);
	}

	public static double getStrength(Projectile proj) {
		if (strength.containsKey(proj)) return strength.get(proj);
		return 0;
	}

	public static void addMultiplier(Projectile proj, Double value) {
		if(multiplier.containsKey(proj)) {
			multiplier.put(proj, multiplier.get(proj) * value);
		} else {
			multiplier.put(proj, value);
		}
	}
	
	public static double getMultiplier(Projectile proj) {
		if(proj == null) return 1;
		if (multiplier.containsKey(proj)) return multiplier.get(proj);
		return 1;
	}
	
	public static void setSpell(Projectile proj, Spell value) {
		spell.put(proj, value);
	}

	public static Spell getSpell(Projectile proj) {
		if (spell.containsKey(proj)) return spell.get(proj);
		return null;
	}

	/*Inherited methods*/
	@Override
	public void run() {
		
		for(World w: Bukkit.getWorlds()) {
			for(Projectile proj: w.getEntitiesByClass(Projectile.class)) {
				if(proj.getTicksLived() >= 20*30) proj.remove();
			}
		}
		
		for(Projectile p: accuracy.keySet()) {
			if(p.isDead()) accuracy.remove(p);
		}
		
		for(Projectile p: strength.keySet()) {
			if(p.isDead()) strength.remove(p);
		}
		
		for(Projectile p: multiplier.keySet()) {
			if(p.isDead()) multiplier.remove(p);
		}
		
		for(Projectile p: spell.keySet()) {
			if(p.isDead()) spell.remove(p);
		}
		
	}
	
}
