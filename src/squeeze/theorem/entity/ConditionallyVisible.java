package squeeze.theorem.entity;

import java.util.List;

import org.bukkit.entity.Player;

public interface ConditionallyVisible {

	public boolean isVisible(Player player);
	public List<Player> getViewers();
	
}
