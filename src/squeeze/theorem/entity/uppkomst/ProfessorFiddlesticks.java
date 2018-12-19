package squeeze.theorem.entity.uppkomst;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import squeeze.theorem.entity.DialogueNode;
import squeeze.theorem.entity.EyeTracking;
import squeeze.theorem.entity.NPC;

public class ProfessorFiddlesticks extends NPC implements EyeTracking {
	
	public ProfessorFiddlesticks() {
		super("Fiddlesticks", EntityType.VINDICATOR);
		setAI(false);
		setInvulnerable(true);
		addLocation(new Location(Bukkit.getWorld("world"), 4440.5, 65, -8592.5, -90, 0));
		setPrefix("Professor");
		setSuffix("Ph.D");
		
	}

	@Override
	public DialogueNode getDialogueNode(Player player) {
		
		DialogueNode root = new DialogueNode("True magic is control not of others, or of the world around you, but of the self.", NPC);
		
		return root;
	}

}
