package squeeze.theorem.data;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class VanillaData {
	
	private World world = Bukkit.getWorld("world");
	private double x = world.getSpawnLocation().getX();
	private double y = world.getSpawnLocation().getY();;
	private double z = world.getSpawnLocation().getZ();
	private float pitch = world.getSpawnLocation().getPitch();
	private float yaw = world.getSpawnLocation().getYaw();
	
	private double health = 5;
	private int foodLevel = 20;
	private int air = 30;
	private int fireTicks = -20;
	private float fallDistance = 0;
	
	//TODO Make it where database inventory data is saved here 
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public float getPitch() {
		return pitch;
	}
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	public float getYaw() {
		return yaw;
	}
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	public int getFoodLevel() {
		return foodLevel;
	}
	public void setFoodLevel(int foodLevel) {
		this.foodLevel = foodLevel;
	}
	public double getHealth() {
		return health;
	}
	public void setHealth(double health) {
		this.health = health;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	public int getAir() {
		return air;
	}
	public void setAir(int air) {
		this.air = air;
	}
	public int getFireTicks() {
		return fireTicks;
	}
	public void setFireTicks(int fireTicks) {
		this.fireTicks = fireTicks;
	}
	public float getFallDistance() {
		return fallDistance;
	}
	public void setFallDistance(float fallDistance) {
		this.fallDistance = fallDistance;
	}
	public World getWorld() {
		return world;
	}
	public void setWorld(World world) {
		this.world = world;
	}
	
}
