package squeeze.theorem.animation;

import org.bukkit.Location;
import org.bukkit.Particle;

public class AnimationDoubleHelix extends Animation {

	private double radius;
	private double height;
	private double step;
	
	/**
	 * Plays a double helix, or DNA-shaped, animation at the provided location.
	 * @param particle Particle to be used for this animation
	 * @param radius Horizontal radius of the animation
	 * @param step Change in angle and change in height between points
	 * @param height Height of the animation
	 */
	public AnimationDoubleHelix(Particle particle, double radius, double step, double height) {
		super(particle);
		this.radius = radius;
		this.step = step;
		this.height = height;
	}
	
	@Override
	public void animate(Location loc) {
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		
		for(double i = 0; i < height; i = i + step) {
			loc.getWorld().spawnParticle(particle, Math.cos(i) * radius + x, y + i, Math.sin(i) * radius + z, 0);
			loc.getWorld().spawnParticle(particle, -Math.cos(i) * radius + x, y + i, -Math.sin(i) * radius + z, 0);
		}
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getStep() {
		return step;
	}

	public void setStep(double step) {
		this.step = step;
	}
	
}
