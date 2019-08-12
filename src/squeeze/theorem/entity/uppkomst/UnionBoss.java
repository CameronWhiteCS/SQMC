package squeeze.theorem.entity.uppkomst;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.entity.Boundable;
import squeeze.theorem.entity.DialogueNode;
import squeeze.theorem.entity.DialogueNode.DialogueType;
import squeeze.theorem.entity.EyeTracking;
import squeeze.theorem.entity.NPC;
import squeeze.theorem.item.CustomItem;

public class UnionBoss extends NPC implements EyeTracking, Boundable {
	
	public UnionBoss() {
		super("Bob", EntityType.VILLAGER);
		setSuffix("Union Boss");
		addLocation(new Location(Bukkit.getWorld("world"), 4423, 37, -8612));
	}

	@Override
	public DialogueNode getDialogueNode(Player player) {
		return null;
	}

	@Override
	public double getWanderRadius() {
		return 15;
	}
	
}
