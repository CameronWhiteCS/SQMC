package squeeze.theorem.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import squeeze.theorem.skill.firemaking.SQMCEntityFire;

public class FireLightEvent extends Event implements Cancellable {

	private static HandlerList handlerList = new HandlerList();
	
	private Player player;
	private SQMCEntityFire fire;
	private Location location;
	private ItemStack logs;
	private boolean isCancelled;
	private double XP;

	public FireLightEvent(Player player, SQMCEntityFire fire, Location location, ItemStack logs) {
		setPlayer(player);
		setFire(fire);
		setLocation(location);
		setLogs(logs);
		setXP(fire.getXP());
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}

	public static HandlerList getHandlerList() {
		return handlerList;
	}

	public SQMCEntityFire getFire() {
		return fire;
	}

	public void setFire(SQMCEntityFire fire) {
		this.fire = fire;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public ItemStack getLogs() {
		return logs;
	}

	public void setLogs(ItemStack logs) {
		this.logs = logs;
	}

	@Override
	public boolean isCancelled() {
		return this.isCancelled;
	}

	@Override
	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public double getXP() {
		return XP;
	}

	public void setXP(double xP) {
		XP = xP;
	}

}
