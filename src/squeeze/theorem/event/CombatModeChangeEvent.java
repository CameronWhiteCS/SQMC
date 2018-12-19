package squeeze.theorem.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import squeeze.theorem.combat.CombatMode;

public class CombatModeChangeEvent extends Event
{

	private static HandlerList handlerList = new HandlerList();
	
	private CombatMode combatMode;
	private Player player;
	
	public CombatModeChangeEvent(Player player, CombatMode combatMode) {
		setPlayer(player);
		setCombatMode(combatMode);
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}
	
	public static HandlerList getHandlerList() {
	    return handlerList;
	}

	public CombatMode getCombatMode() {
		return combatMode;
	}

	public void setCombatMode(CombatMode combatMode) {
		this.combatMode = combatMode;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
