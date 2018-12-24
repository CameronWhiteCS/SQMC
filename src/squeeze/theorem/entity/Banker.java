package squeeze.theorem.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class Banker extends NPC{

	public Banker(String name, EntityType entityType) {
		super(name, entityType);
		
	}

	@Override
	public DialogueNode getDialogueNode(Player player) {
		
		DialogueNode root = new DialogueNode(String.format("Hello, %s. What can I do for you?", player.getName()), NPC);
			root.addChild("I'd like to access my bank account.", PLAYER);
			
		
		return root;
	}
	
}
