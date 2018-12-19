package squeeze.theorem.entity.uppkomst;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;

import squeeze.theorem.entity.Boundable;
import squeeze.theorem.entity.DialogueNode;
import squeeze.theorem.entity.NPC;

public class Child extends NPC implements Boundable{

	public Child() {
		super("Child", EntityType.VILLAGER);
		setBaby(true);
		addLocation(new Location(Bukkit.getWorld("world"), 4450.5, 25, -8659.5));
		setProfession(Profession.NITWIT);
	}

	@Override
	public DialogueNode getDialogueNode(Player player) {
		return null;
	}

	@Override
	public double getWanderRadius() {
		return 7;
	}
	
}
