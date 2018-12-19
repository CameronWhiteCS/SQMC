package squeeze.theorem.region;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import squeeze.theorem.audio.SQMCSong;

public class Cuboid {

	/*Fields*/
	private Location location1;
	private Location location2;
	private SQMCSong song;
	private int color = -1;
	
	/*Constructors*/
	public Cuboid(Location loc1, Location loc2) {
		this.setLocation1(loc1);
		this.setLocation2(loc2);
	}
	
	public Cuboid(World world, double x1, double y1, double z1, double x2, double y2, double z2) {
		this.setLocation1(new Location(world, x1, y1, z1));
		this.setLocation2(new Location(world, x2, y2, z2));
	}
	
	public Cuboid(String world, double x1, double y1, double z1, double x2, double y2, double z2) {
		this.setLocation1(new Location(Bukkit.getWorld(world), x1, y1, z1));
		this.setLocation2(new Location(Bukkit.getWorld(world), x2, y2, z2));
	}
	
	/*Setters and getters*/
	public void setLocation1(Location location1) {
		this.location1 = location1;
	}
	
	public Location getLocation1() {
		return location1;
	}

	public void setLocation2(Location location2) {
		this.location2 = location2;
	}
	
	public Location getLocation2() {
		return location2;
	}
	
	public Cuboid setColor(int color) {
		this.color = color;
		return this;
	}
	
	public int getColor() {
		return color;
	}
	
	public Cuboid setSong(SQMCSong s) {
		this.song = s;
		return this;
	}
	
	public SQMCSong getSong() {
		return this.song;
	}
	
	/*Calculations derived from fields*/
	public boolean contains(Location loc) {
		
		return (
				   loc.getWorld() == location1.getWorld()
				&& loc.getWorld() == location2.getWorld()
				&& loc.getX() >= getLowerX()
				&& loc.getX() <= getUpperX()
				&& loc.getY() >= getLowerY()
				&& loc.getY() <= getUpperY()
				&& loc.getZ() >= getLowerZ()
				&& loc.getZ() <= getUpperZ()
				);
	}

	public double getLowerX() {
		return Math.min(location1.getBlockX(), location2.getBlockX());
	}
	
	public double getUpperX() {
		return Math.max(location1.getBlockX(), location2.getBlockX());
	}
	
	public double getLowerY() {
		return Math.min(location1.getBlockY(), location2.getBlockY());
	}
	
	public double getUpperY() {
		return Math.max(location1.getBlockY(), location2.getBlockY());
	}
	
	public double getLowerZ() {
		return Math.min(location1.getBlockZ(), location2.getBlockZ());
	}
	
	public double getUpperZ() {
		return Math.max(location1.getBlockZ(), location2.getBlockZ());
	}
	
}
