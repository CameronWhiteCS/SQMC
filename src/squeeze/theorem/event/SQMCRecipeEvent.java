package squeeze.theorem.event;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import squeeze.theorem.recipe.SQMCRecipe;
import squeeze.theorem.skill.Skill;

public class SQMCRecipeEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();
	
	private boolean isCancelled;
	private SQMCRecipe sqmcRecipe;
	private Player player;
	private HashMap<Skill, Double> xpRewards = new HashMap<Skill, Double>();
	
	public SQMCRecipeEvent(SQMCRecipe customRecipe, Player player) {
		setSQMCRecipe(customRecipe);
		setPlayer(player);
		
		for(Skill s: customRecipe.getXP().keySet()) {
			this.xpRewards.put(s, customRecipe.getXP().get(s));
		}

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

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public SQMCRecipe getSQMCRecipe() {
		return sqmcRecipe;
	}

	public void setSQMCRecipe(SQMCRecipe sqmcRecipe) {
		this.sqmcRecipe = sqmcRecipe;
	}

	public HashMap<Skill, Double> getXpRewards() {
		return xpRewards;
	}

	public void setXpRewards(HashMap<Skill, Double> xpRewards) {
		this.xpRewards = xpRewards;
	}

}
