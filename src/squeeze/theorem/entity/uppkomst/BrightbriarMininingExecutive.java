package squeeze.theorem.entity.uppkomst;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;

import squeeze.theorem.entity.DialogueNode;
import squeeze.theorem.entity.NPC;

public class BrightbriarMininingExecutive extends NPC {
	
	public BrightbriarMininingExecutive() {
		super("Brightbriar", EntityType.VILLAGER);
		addLocation(new Location(Bukkit.getWorld("world"), 4457.5, 76.18750, -8574.5, 180, 0));
		this.setAI(false);
		this.setSilent(true);
		setPrefix("Mr.");
		this.setInvulnerable(true);
		setSuffix("Mining Executive");
		setProfession(Profession.PRIEST);
	}
	
	@Override
	public DialogueNode getDialogueNode(Player player) {
		
		
		//Non-quest dialogue
		DialogueNode root = new DialogueNode("Welcome to Brightbriar Mining Corporation, Uppkomst's premiere mining company. What can I do for you? ", NPC);
		
		return root;
		
	}

}
