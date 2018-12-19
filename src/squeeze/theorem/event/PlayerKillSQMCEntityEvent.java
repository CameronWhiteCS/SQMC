package squeeze.theorem.event;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import squeeze.theorem.entity.SQMCEntity;

public class PlayerKillSQMCEntityEvent extends Event {

	private SQMCEntity customEntity;
	private LivingEntity entity;
	private Player player;
	
	private static HandlerList handlers = new HandlerList();
	
	public PlayerKillSQMCEntityEvent(Player player, LivingEntity entity, SQMCEntity customEntity) {
		setSQMCEntity(customEntity);
		setPlayer(player);
		setEntity(entity);
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}

	public SQMCEntity getSQMCEntity() {
		return customEntity;
	}

	public void setSQMCEntity(SQMCEntity customEntity) {
		this.customEntity = customEntity;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public LivingEntity getEntity() {
		return entity;
	}

	public void setEntity(LivingEntity entity) {
		this.entity = entity;
	}
	
}
