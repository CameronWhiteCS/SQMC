package squeeze.theorem.event;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import squeeze.theorem.item.CustomItemPickaxe;
import squeeze.theorem.skill.mining.Ore;

public class OreMineEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	
	private boolean isCancelled;
	private Player player;
	private Ore ore;
	private CustomItemPickaxe pickaxe;
	private ConcurrentHashMap<Location, Material> blocks;
	private double XP;
	
	
	public OreMineEvent(Player player, Ore ore, ConcurrentHashMap<Location, Material> blocks, CustomItemPickaxe pickaxe) {
		setPlayer(player);
		setOre(ore);
		setPickaxe(pickaxe);
		setBlocks(blocks);
		setXP(ore.getXP());
	}	
	
	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;

	}
	
	@Override
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Ore getOre() {
		return ore;
	}

	public void setOre(Ore ore) {
		this.ore = ore;
	}

	public CustomItemPickaxe getPickaxe() {
		return pickaxe;
	}

	public void setPickaxe(CustomItemPickaxe pickaxe) {
		this.pickaxe = pickaxe;
	}

	public ConcurrentHashMap<Location, Material> getBlocks() {
		return this.blocks;
	}

	public void setBlocks(ConcurrentHashMap<Location, Material> blocks) {
		this.blocks = blocks;
	}

	public double getXP() {
		return XP;
	}

	public void setXP(double xP) {
		XP = xP;
	}

}
