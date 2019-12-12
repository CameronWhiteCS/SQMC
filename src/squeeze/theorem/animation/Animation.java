package squeeze.theorem.animation;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;

public abstract class Animation {
	
	/*Fields*/
	protected Particle particle;
	
	/*Constructors*/
	public Animation(Particle particle) {
		this.particle = particle;
	}
	
	/*Methods*/
	public abstract void animate(Location loc);
	
	public void animate(Entity e) {
		animate(e.getLocation());
	}
	
	/*Accessors*/
	public Particle getParticle() {
		return this.particle;
	}
	
	//TODO: MOVE
	/**
	 *  Creates a circle along the XZ axis 
	 * @param loc Location for this animation to be played
	 * @param radius Radius of this circle
	 * @param step Change in angle between points
	 * @param particle Particle to be used for this animation
	 */
	protected static void xzCircle(Location loc, double radius, double step,
			Particle particle) {

		for (double i = 0; i <= Math.PI * 2; i += step) {

			double x = Math.cos(i) * radius + loc.getX();
			double y = loc.getY();
			double z = Math.sin(i) * radius + loc.getZ();
			
			loc.getWorld().spawnParticle(particle, new Location(loc.getWorld(), x, y, z), 0);

		}

	}
	
}
