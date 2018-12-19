package squeeze.theorem.animation;

import org.bukkit.Location;
import org.bukkit.Particle;

public abstract class Animations {

	public static void xzCircle(Location loc, double radius, double step,
			Particle p) {

		for (double i = 0; i <= Math.PI * 2; i += step) {

			double x = Math.cos(i) * radius + loc.getX();
			double y = loc.getY();
			double z = Math.sin(i) * radius + loc.getZ();
			
			loc.getWorld().spawnParticle(p, new Location(loc.getWorld(), x, y, z), 0);

		}

	}
	
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
