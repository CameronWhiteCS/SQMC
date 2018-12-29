package squeeze.theorem.animation;

import org.bukkit.Location;
import org.bukkit.Particle;

/**
 * This class contains a series of static methods to play particle animations
 * at a given location. This class cannot, and should not be, instantiated. 
 * @author SqueezeTheorem
 *
 */
public abstract class Animations {

	/**
	 *  Creates a circle along the XZ axis 
	 * @param loc Location for this animation to be played
	 * @param radius Radius of this circle
	 * @param step Change in angle between points
	 * @param particle Particle to be used for this animation
	 */
	public static void xzCircle(Location loc, double radius, double step,
			Particle particle) {

		for (double i = 0; i <= Math.PI * 2; i += step) {

			double x = Math.cos(i) * radius + loc.getX();
			double y = loc.getY();
			double z = Math.sin(i) * radius + loc.getZ();
			
			loc.getWorld().spawnParticle(particle, new Location(loc.getWorld(), x, y, z), 0);

		}

	}
	
	/**
	 * 
	 * @param loc Location to play this animation
	 * @param radius The radius of this sphere
	 * @param step Vertical and horizontal space between points on the sphere.
	 * Affects change in theta and change in rho in a polar coordinate representation.
	 * @param particle Particle to be used in this animation
	 */
	public static void sphere(Location loc, double radius, double step, Particle particle) {
		
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		
		
		for(double u = 0; u <= 2 * Math.PI; u+= step) {
			for(double v = -Math.PI/2; v <= Math.PI/2; v += step) {
				loc.getWorld().spawnParticle(particle, new Location(loc.getWorld(), radius * Math.cos(v) * Math.cos(u) + x, + radius * Math.sin(v) + y, radius * Math.cos(v) * Math.sin(u) + z), 0);
			}
		}
	}
	
	/**
	 * Plays a double helix, or DNA-shaped, animation at the provided location.
	 * @param loc Location to play this animation
	 * @param radius Horizontal radius of the animation
	 * @param height Height of the animation
	 * @param step Change in angle and change in height between points
	 * @param particle Particle to be used for this animation
	 */
	public static void doubleHelix(Location loc, double radius, double height, double step, Particle particle) {
		
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		
		for(double i = 0; i < height; i = i + step) {
			loc.getWorld().spawnParticle(particle, Math.cos(i) * radius + x, y + i, Math.sin(i) * radius + z, 0);
			loc.getWorld().spawnParticle(particle, -Math.cos(i) * radius + x, y + i, -Math.sin(i) * radius + z, 0);
		}
		
	}

}
