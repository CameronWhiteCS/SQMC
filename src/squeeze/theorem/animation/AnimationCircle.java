package squeeze.theorem.animation;

import org.bukkit.Location;
import org.bukkit.Particle;

public class AnimationCircle extends Animation {

	private double radius;
	private double step;
	
	public AnimationCircle(Particle particle, double radius, double step) {
		super(particle);
		this.radius = radius;
		this.step = step;
	}
	
	@Override
	public void animate(Location loc) {

		for (double i = 0; i <= Math.PI * 2; i += step) {

			double x = Math.cos(i) * radius + loc.getX();
			double y = loc.getY();
			double z = Math.sin(i) * radius + loc.getZ();
			
			loc.getWorld().spawnParticle(particle, new Location(loc.getWorld(), x, y, z), 0);

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
