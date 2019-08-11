package squeeze.theorem.entity.uppkomst;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;

import squeeze.theorem.entity.DialogueNode;
import squeeze.theorem.entity.EyeTracking;
import squeeze.theorem.entity.NPC;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.ui.UserInterfaceShop;

public class Blacksmith extends NPC implements EyeTracking {

	private UserInterfaceShop shop = new UserInterfaceShop("Grunkle's Blacksmith", 27);
	
	public Blacksmith() {
		super("Grunkle", EntityType.VILLAGER);
		setProfession(Profession.TOOLSMITH);
		setSuffix("the Blacksmith");
		addLocation(new Location(Bukkit.getWorld("world"), 4424.5, 54, -8594.5));
		shop.addItem(CustomItem.WOODEN_PICKAXE, 25, 0, 1);
		shop.addItem(CustomItem.WOODEN_AXE, 25, 0, 1);
		shop.addItem(CustomItem.CALCIUM_CARBONATE, 50, 5, 1);
		this.setAI(false);
	}

	@Override
	public DialogueNode getDialogueNode(Player player) {
		
		DialogueNode root = new DialogueNode("Greetings traveler. What can I do for you?", NPC);
			root.append(new DialogueNode("What do you have for sale?", PLAYER) {
				@Override
				public void onSelect(Player player) {
					shop.open(player);
				}
			});
		
		return root;
		
	}

}
