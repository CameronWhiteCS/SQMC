package squeeze.theorem.entity.uppkomst;

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

public class UppkomstPriestOfMoonti extends NPC implements EyeTracking {

	public UppkomstPriestOfMoonti() {
		super("Priest of Moonti", EntityType.VILLAGER);
		setProfession(Profession.CLERIC);
		addLocation(new Location(Bukkit.getWorld("world"), 3899.5, 256, -8969.5));
		this.setAI(false);
	}

	@Override
	public DialogueNode getDialogueNode(Player player) {
		
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player);
		if(!dat.hasFlags("quest.eternal_flame.start")) return eternalFlameStart(player);
		
		return null;
		

	}

	private DialogueNode eternalFlameStart(Player player) {
		
		DialogueNode root = new DialogueNode(String.format("Hello, %s.", player.getName()), NPC);
		root.append("Uh, hi. What are you doing up here?", PLAYER);
		root.append(" I'm the last high priest of Moonti in Uppkomst. I spend my days tending for The Flame.", NPC);
		root.append("Who is Moonti?", PLAYER);
		root.append(" You have much to learn, child. Moonti is the uncaused cause, the unmoved mover. Before the world spun... No, before the world burned, there was Moonti.", NPC);
		root.append("Next", PLAYER);
		root.append("It was from nothing that Moonti created the world. And the world had form, but not order. The chaos and the order were one. There were untold horrors, and", NPC);
		root.append("Next", PLAYER);
		root.append("untold beauties. To rid the world He had created of its blemishes, Moonti set it ablaze. And the heat of The Flame ignited Moonti, and with the world He did burn also.", NPC);
		root.append("Next", PLAYER);
		root.append("Together, Moonti and His creation burned for 1000 years. Eventually, the flames subsided, and the disorder of creation had been banished through the presence of The Flame.", NPC);
		root.append("Next", PLAYER);
		DialogueNode n1 = new DialogueNode("That is why the followers of Moonti revere fire, for we believe our God lives on in The Flame.", NPC);
			n1.addChild("I'm moved by your religion -- what must I do to follow Moonti?", PLAYER);
				n1.getChild(0).append("To become a follower of Moonti is a serious matter. I can teach you the Way of The Flame, but you must show me you are worthy.", NPC);
				n1.getChild(0).append("What must I do?", PLAYER);
				n1.getChild(0).append("There two places in this world where the The Flame still burns, because their disorder has yet to be cleansed. One of these places, the laymen call Volcanis. There, you will find another", NPC);
				n1.getChild(0).append("Next", PLAYER);
				n1.getChild(0).append(new DialogueNode("Priest of Moonti. Retrieve from him a flame, and carry it back with you to where we now stand, with it still burning. This is our custom, that any follower of The Flame should take this pilgrimage.", NPC) {
					@Override
					public void onSelect(Player player) {
						DataManager dataManager = DataManager.getInstance();
						PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
						dat.addFlag("quest.eternal_flame.start");
					}
				});
				n1.getChild(0).append("Isn't Volcanis an island? How can I carry a flame through the ocean without it going out?", PLAYER);
				n1.getChild(0).append("Protect it as you swim, and don't let your head under the water. There are more precuations you must take. The Flame is fragile, and must be protected.", NPC);
				
			n1.addChild("Doesn't it get lonely up here?", PLAYER);
			n1.addChild("That's a nice story, Grandpa.", PLAYER);
		root.append(n1);
		
		
		return root;
		
	}

}