package squeeze.theorem.animation;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

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
	
}
