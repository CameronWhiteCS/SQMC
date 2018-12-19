package squeeze.theorem.entity.uppkomst;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;

import squeeze.theorem.entity.DialogueNode;
import squeeze.theorem.entity.EyeTracking;
import squeeze.theorem.entity.NPC;
import squeeze.theorem.ui.UserInterfaceShop;

public class Florist extends NPC implements EyeTracking {

	private UserInterfaceShop shop = new UserInterfaceShop("Uppkomst Flowers", 27);
	
	public Florist() {
		super("Marianne", EntityType.VILLAGER);
		setSuffix("");
		setAI(false);
		addLocation(new Location(Bukkit.getWorld("world"), 4403.5, 54, -8581.5));
		setProfession(Profession.NITWIT);
	
		
	}

	@Override
	public DialogueNode getDialogueNode(Player player) {
		DialogueNode root = new DialogueNode("Hey love, it's a wonderful day in Uppkomst, ain't it? What can I do for 'ya?", NPC);
		root.addChild(new DialogueNode("What've you got for sale?", PLAYER) {
			@Override
			public void onSelect(Player player) {
				shop.open(player);
			}
		});
		
		return root;
	}

}
