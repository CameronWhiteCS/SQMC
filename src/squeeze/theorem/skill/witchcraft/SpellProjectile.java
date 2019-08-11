package squeeze.theorem.skill.witchcraft;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.WitherSkull;
import org.bukkit.projectiles.ProjectileSource;

import net.minecraft.server.v1_14_R1.PacketPlayOutEntityDestroy;
import squeeze.theorem.animation.Animations;
import squeeze.theorem.combat.AttackStyle;
import squeeze.theorem.combat.CombatManager;
import squeeze.theorem.combat.CombatStats;
import squeeze.theorem.data.DataManager;
import squeeze.theorem.entity.SQMCEntity;

public class SpellProjectile extends Spell implements CombatSpell {

	private boolean gravity;
	private double animationRadius;
	private Particle particle;
	private double damage;
	private double accuracy;

	public SpellProjectile(String name, String desc, Material m) {
		super(name, desc, m);
	}
	
	public void animate(Location loc) {

		Animations.sphere(loc, getAnimationRadius(), Math.PI/4, getParticle());
		
	}

	@Override
	public void cast(Entity e) {
		
		CombatStats cs;
		if(e instanceof ProjectileSource == false) return;
		ProjectileSource projectileSource = (ProjectileSource) e;
		
		if (e instanceof Player) {
			DataManager dataManager = DataManager.getInstance();
			cs = dataManager.getPlayerData(e.getUniqueId());
		} else {
			SQMCEntity cust = SQMCEntity.getSQMCEntity(e);
			if (cust == null)
				return;
			if (cust instanceof CombatStats == false)
				return;
			cs = (CombatStats) cust;
		}

		WitherSkull skull = projectileSource.launchProjectile(WitherSkull.class);
		skull.setIsIncendiary(false);
		skull.setVelocity(e.getLocation().getDirection().multiply(0.5));
		CombatManager.setAccuracy(skull, cs.getAccuracy(AttackStyle.MAGIC) + getAccuracy());
		CombatManager.setStrength(skull, cs.getStrength(AttackStyle.MAGIC) + getDamage());
		CombatManager.setSpell(skull, this);

		
		PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] {skull.getEntityId()});
		
		for(Player p: Bukkit.getOnlinePlayers()) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
		
		super.cast(e);

	}
	
	//Intended to be overwritten
	public void onHit(Projectile proj, Entity entity) {
		
	}
	
	public boolean isGravity() {
		return gravity;
	}

	public SpellProjectile setGravity(boolean gravity) {
		this.gravity = gravity;
		return this;
	}

	public Particle getParticle() {
		return particle;
	}

	public SpellProjectile setParticle(Particle particle) {
		this.particle = particle;
		return this;
	}

	public double getAnimationRadius() {
		return animationRadius;
	}

	public SpellProjectile setAnimationRadius(double animationRadius) {
		this.animationRadius = animationRadius;
		return this;
	}

	public double getDamage() {
		return damage;
	}

	public SpellProjectile setDamage(double damage) {
		this.damage = damage;
		return this;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public SpellProjectile setAccuracy(double accuracy) {
		this.accuracy = accuracy;
		return this;
	}
	
}
