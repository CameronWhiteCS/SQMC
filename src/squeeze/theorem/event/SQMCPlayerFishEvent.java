package squeeze.theorem.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerFishEvent;

import squeeze.theorem.skill.fishing.SQMCEntityFish;

public class SQMCPlayerFishEvent extends Event implements Cancellable {

	/* Static fields */

	private boolean isCancelled;
	private static HandlerList handlerList = new HandlerList();

	/* Fields */
	
	private PlayerFishEvent vanillaEvent;
	private SQMCEntityFish customEntityFish;
	private double XP;
	
	
	public SQMCPlayerFishEvent(PlayerFishEvent evt, SQMCEntityFish fish) {
		setVanillaEvent(evt);
		setSQMCEntityFish(fish);
		setXP(fish.getXP());
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
		return handlerList;
	}

	public static HandlerList getHandlerList() {
		return handlerList;
	}

	public PlayerFishEvent getVanillaEvent() {
		return vanillaEvent;
	}

	public void setVanillaEvent(PlayerFishEvent vanillaEvent) {
		this.vanillaEvent = vanillaEvent;
	}

	public SQMCEntityFish getSQMCEntityFish() {
		return customEntityFish;
	}

	public void setSQMCEntityFish(SQMCEntityFish customEntityFish) {
		this.customEntityFish = customEntityFish;
	}

	public Player getPlayer() {
		return getVanillaEvent().getPlayer();
	}

	public double getXP() {
		return XP;
	}

	public void setXP(double xP) {
		XP = xP;
	}
	
}
