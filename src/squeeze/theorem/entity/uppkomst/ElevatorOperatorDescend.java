package squeeze.theorem.entity.uppkomst;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import squeeze.theorem.entity.Boundable;
import squeeze.theorem.entity.DialogueNode;
import squeeze.theorem.entity.NPC;

public class ElevatorOperatorDescend extends NPC implements Boundable {

	public ElevatorOperatorDescend() {
		super("Elevator Operator", EntityType.ZOMBIE);
		setPassive(true);
		addLocation(new Location(Bukkit.getWorld("world"), 4476.5, 47, -8625.5));
		
	}

	@Override
	public DialogueNode getDialogueNode(Player player) {
		
		DialogueNode root = new DialogueNode("Hello, " + player.getName() + ".", NPC);
		root.append("What is this place?", PLAYER);
		root.append("Many years ago, Brightbriar's mining company used to operate in this location, but it has since been abandoned. In the time that's passed since then, it's become overrun with horrific creatures. If you value your life, stay away from here.", NPC);
			root.getChild(0, 0).addChild(new DialogueNode("I value adventure more than my life! Take me to the mines!", PLAYER) {
				@Override
				public void onSelect(Player player) {
					player.sendMessage(ChatColor.RED + "The elevator operator laboriously cranks the elevator down into the mines.");
					player.teleport(new Location(Bukkit.getWorld("world"), 4497.5, 57, -8602.5, 0, 0));
				}
			});
			root.getChild(0, 0).addChild("Er, I'd prefer to live, thanks!", PLAYER);
		
		return root;
		
	}

	@Override
	public double getWanderRadius() {
		return 3;
	}

}
