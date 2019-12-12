package squeeze.theorem.animation;

import org.bukkit.Location;
import org.bukkit.Particle;

public class AnimationSphere extends Animation {

	private double radius;
	private double step;
	
	/**
	 *
	 * @param particle Particle to be used in this animation
	 * @param radius The radius of this sphere
	 * @param step Vertical and horizontal space between points on the sphere.
	 * Affects change in theta and change in rho in a polar coordinate representation.
	 */
	public AnimationSphere(Particle particle, double radius, double step) {
		super(particle);
		this.radius = radius;
		this.step = step;
	}
	
	@Override
	public void animate(Location loc) {
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		for(double u = 0; u <= 2 * Math.PI; u+= step) {
			for(double v = -Math.PI/2; v <= Math.PI/2; v += step) {
				loc.getWorld().spawnParticle(particle, new Location(loc.getWorld(), radius * Math.cos(v) * Math.cos(u) + x, + radius * Math.sin(v) + y, radius * Math.cos(v) * Math.sin(u) + z), 0);
			}
		}
	}
	
	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getStep() {
		return step;
	}

	public void setStep(double step) {
		this.step = step;
	}

}
