package squeeze.theorem.entity.uppkomst;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import squeeze.theorem.entity.Boundable;
import squeeze.theorem.entity.DialogueNode;
import squeeze.theorem.entity.DialogueNode.DialogueType;
import squeeze.theorem.entity.EyeTracking;
import squeeze.theorem.entity.NPC;

public class MailCarrier extends NPC implements EyeTracking, Boundable{
	
	/*Constructors*/
	public MailCarrier() {
		super("Tobin", EntityType.VILLAGER);
		setSuffix("Mail Carrier");
		this.setInvulnerable(true);
		addLocation(new Location(Bukkit.getWorld("world"), 4394, 24, -8627));
		
	}
	
	/*Methods*/
	@Override
	public DialogueNode getDialogueNode(Player player) {
		
		DialogueNode root = new DialogueNode("Hello, " + player.getName() + ". What can I do for you?", DialogueType.NPC);
			root.addChild("Do you have any mail for me?", DialogueType.PLAYER);
			root.getChild(0).addChild("I'm afraid the developer is a lazy fuck and hasn't implemented the mail feature yet.", DialogueType.NPC);
			root.getChild(0, 0).addChild("Oh. Well, when do we get that feature?", DialogueType.PLAYER);
			root.getChild(0, 0, 0).addChild("Probably never. Have you ever met a developer?", DialogueType.NPC);
			root.getChild(0, 0, 0).addChild("Good point.", DialogueType.PLAYER);
		
		return root;
		
	}

	@Override
	public double getWanderRadius() {
		return 10;
	}

}
