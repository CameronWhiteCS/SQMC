package squeeze.theorem.entity;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public abstract class NPC extends SQMCEntity implements Anchorable, Interactable {

	List<Location> locations = new ArrayList<Location>();
	
	public NPC(String name, EntityType entityType) {
		super(name, entityType);
		this.setInvulnerable(true);
		this.setSilent(true);
	}

	@Override
	public abstract DialogueNode getDialogueNode(Player player);

	@Override
	public List<Location> getLocations() {
		return locations;
	}
	
	protected void addLocation(Location loc) {
		locations.add(loc);
	}

}
