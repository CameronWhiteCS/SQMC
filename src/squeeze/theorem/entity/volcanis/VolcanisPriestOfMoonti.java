package squeeze.theorem.entity.volcanis;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.entity.DialogueNode;
import squeeze.theorem.entity.EyeTracking;
import squeeze.theorem.entity.NPC;

public class VolcanisPriestOfMoonti extends NPC implements EyeTracking {

	public VolcanisPriestOfMoonti() {
		super("Priest of Moonti", EntityType.VILLAGER);
		setProfession(Profession.CLERIC);
		setAI(false);
		addLocation(new Location(Bukkit.getWorld("world"), 7193.5, 236, -7304.5));
	}

	@Override
	public DialogueNode getDialogueNode(Player player) {

		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player);
		
		DialogueNode root = new DialogueNode(String.format("Hello there, %s.", player.getName()), NPC);
		root.append("Hey, I think I've seen another priest like you before.", PLAYER);
		root.append("If you spend much time in the wilderness, It's certainly possible.", NPC);
		
		
		
		//if(dat.hasFlags("quest.eternal_flame.start")) return eternalFlameObtainTorch(player);
		
		
		return root;
	}

	private DialogueNode eternalFlameObtainTorch(Player player) {
		
		DialogueNode root = new DialogueNode("", NPC);

		
		
		
		return root;
	}

}
