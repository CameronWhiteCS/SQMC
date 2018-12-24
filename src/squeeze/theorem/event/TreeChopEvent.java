package squeeze.theorem.event;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import squeeze.theorem.item.CustomItemAxe;
import squeeze.theorem.skill.Tree;

public class TreeChopEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	
	private boolean isCancelled;
	private Player player;
	private Tree tree;
	private ConcurrentHashMap<Location, Material> blocks;
	private CustomItemAxe axe;
	private double XP;
	
	public TreeChopEvent (Player player, Tree tree, ConcurrentHashMap<Location, Material> blocks, CustomItemAxe axe) {
		setPlayer(player);
		setTree(tree);
		setBlocks(blocks);
		setAxe(axe);
		setXP(tree.getXP());
	}
	
	@Override
	public boolean isCancelled() {
		return this.isCancelled;
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

	public Tree getTree() {
		return this.tree;
	}

	public Player getPlayer() {
		return this.player;
	}

	public CustomItemAxe getAxe() {
		return this.axe;
	}

	public ConcurrentHashMap<Location, Material> getBlocks() {
		return this.blocks;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

	public void setBlocks(ConcurrentHashMap<Location, Material> blocks) {
		this.blocks = blocks;
	}

	public void setAxe(CustomItemAxe axe) {
		this.axe = axe;
	}

	public double getXP() {
		return XP;
	}

	public void setXP(double xP) {
		XP = xP;
	}

}
