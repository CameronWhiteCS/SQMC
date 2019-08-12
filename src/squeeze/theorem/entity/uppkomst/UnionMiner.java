package squeeze.theorem.entity.uppkomst;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

import squeeze.theorem.entity.Boundable;
import squeeze.theorem.entity.DialogueNode;
import squeeze.theorem.entity.DialogueNode.DialogueType;
import squeeze.theorem.entity.EyeTracking;
import squeeze.theorem.entity.NPC;

public class UnionMiner extends NPC implements EyeTracking, Boundable {
	
	public UnionMiner() {
		super(ChatColor.GREEN + "Union Miner", EntityType.VILLAGER);
		addLocation(new Location(Bukkit.getWorld("world"), 4422, 37, -8612));
	}
	
	@Override
	public DialogueNode getDialogueNode(Player player){
		
		DialogueNode root = new DialogueNode("The gentle laborer shall no longer suffer from the noxious greed of Brightbriar! We will dismantle oppression ore-by-ore! We will mine the support columns of tyranny in half!", DialogueType.NPC);
		root.addChild("Erm, what?", DialogueType.PLAYER);
	
		return root;
		
	}

	@Override
	public double getWanderRadius() {
		return 15;
	}
	
}
