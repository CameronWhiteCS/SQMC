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

public class ShadyMan extends NPC implements Boundable, EyeTracking  {
	
	public ShadyMan() {
		super(ChatColor.GOLD + "Kyle", EntityType.VILLAGER);
		setSuffix("Shady Man");
		addLocation(new Location(Bukkit.getWorld("world"), 4418, 39, -8600));
	}

	@Override
	public DialogueNode getDialogueNode(Player player) {
		
		return new DialogueNode("...", DialogueType.NPC);
		
	}
	
	@Override
	public double getWanderRadius() {
		return 10;
	}

}
