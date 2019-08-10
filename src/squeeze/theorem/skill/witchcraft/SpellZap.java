package squeeze.theorem.skill.witchcraft;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;

import squeeze.theorem.animation.Animations;
import squeeze.theorem.combat.AttackStyle;
import squeeze.theorem.combat.CombatStats;
import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.entity.SQMCEntity;

public class SpellZap extends Spell implements CombatSpell {

	/*Fields*/
	private Particle particle;
	private double damage;
	private double accuracy;
	private double length;
	
	/*Constructors*/
	public SpellZap(String name, String desc, Material m) {
		super(name, desc, m);
	}

	/*Setters and getters*/
	public Particle getParticle() {
		return particle;
	}

	public SpellZap setParticle(Particle particle) {
		this.particle = particle;
		return this;
	}

	public double getDamage() {
		return damage;
	}

	public SpellZap setDamage(double damage) {
		this.damage = damage;
		return this;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public SpellZap setAccuracy(double accuracy) {
		this.accuracy = accuracy;
		return this;
	}
	
	public double getLength() {
		return length;
	}

	public SpellZap setLength(double length) {
		this.length = length;
		return this;
	}
	
	/*Inherited methods*/
	@Override
	public void cast(Entity e) {
		
		/*Initialize the variables needed for this function*/
		if(e instanceof LivingEntity == false) return;
		LivingEntity le = (LivingEntity) e;
		Location origin = le.getLocation().add(new Vector(0, le.getEyeHeight(), 0));
		Location loc = le.getLocation().add(new Vector(0, le.getEyeHeight(), 0));
		Vector direction = le.getLocation().getDirection().multiply(0.1);
		
		double damage = this.damage;
		
		//Add stat-based damage to this calculation
		if(e instanceof Player) {
			DataManager dataManager = DataManager.getInstance();
			PlayerData dat = dataManager.getPlayerData(e.getUniqueId());
			accuracy += dat.getAccuracy(AttackStyle.MAGIC);
			damage += dat.getStrength(AttackStyle.MAGIC);
		}
		
		if(SQMCEntity.getSQMCEntity(e) != null) {
			SQMCEntity ce = SQMCEntity.getSQMCEntity(e);
			if(ce instanceof CombatStats == false) return;
			CombatStats cs = (CombatStats) ce;
			damage += cs.getStrength(AttackStyle.MAGIC);
		}
		
		boolean hit = false;
		while(loc.distance(origin) < this.length && !hit) {
			
			loc.getWorld().spawnParticle(particle, loc, 0);
			
			if(!loc.getBlock().isPassable()) break;
			
			Collection<Entity> entities = loc.getWorld().getNearbyEntities(loc, 1, 1, 1);
			for(Entity entity: entities) {
				if(entity == e) continue;
				
				hit = true;
				Bukkit.getPluginManager().callEvent(new EntityDamageByEntityEvent(le, (LivingEntity) entity, DamageCause.MAGIC, damage));
				Animations.sphere(loc, 1, Math.PI/8, particle);
				break;
			}
			
			loc = loc.add(direction);

		}
		
		super.cast(e);
	}

}
