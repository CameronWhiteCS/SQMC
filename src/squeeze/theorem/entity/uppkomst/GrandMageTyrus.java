package squeeze.theorem.entity.uppkomst;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import squeeze.theorem.entity.Boundable;
import squeeze.theorem.entity.DialogueNode;
import squeeze.theorem.entity.NPC;

public class GrandMageTyrus extends NPC implements Boundable {

	public GrandMageTyrus() {
		super("Tyrus", EntityType.ILLUSIONER);
		setPrefix("Grand Mage");
		addLocation(new Location(Bukkit.getWorld("world"), 4444.5, 54, -8587.5));
		setPassive(true);
	}

	@Override
	public DialogueNode getDialogueNode(Player player) {
		
		DialogueNode root = new DialogueNode("Welcome to the Spell Guild, traveler.", NPC);

		
		return root;
		
	}

	@Override
	public double getWanderRadius() {
		return 10;
	}

}
