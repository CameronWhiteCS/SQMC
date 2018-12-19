package squeeze.theorem.entity.uppkomst;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;

import squeeze.theorem.entity.DialogueNode;
import squeeze.theorem.entity.DialogueNode.DialogueType;
import squeeze.theorem.entity.EyeTracking;
import squeeze.theorem.entity.NPC;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.ui.UserInterfaceShop;

public class MelonFarmer extends NPC implements EyeTracking {

	private UserInterfaceShop shop = new UserInterfaceShop("Uppkomst Melon Emporium", 27);
	
	public MelonFarmer() {
		super("Randy", EntityType.VILLAGER);
		setSuffix("Melon Farmer");
		this.setInvulnerable(true);
		this.setAI(false);
		addLocation(new Location(Bukkit.getWorld("world"), 4411.5, 54, -8581.5));
		shop.addItem(CustomItem.MELON_SLICE, 10, 4);
		setProfession(Profession.FARMER);

	}

	@Override
	public DialogueNode getDialogueNode(Player player) {
		
		DialogueNode root = new DialogueNode("Howdy partner, what can I do you for?", DialogueType.NPC);
		root.addChild(new DialogueNode("What've you got for sale?", DialogueType.PLAYER) {
			@Override
			public void onSelect(Player player) {
				shop.open(player);
			}
		});
		
		root.addChild("So, uh, how did you get into the melon business?", DialogueType.PLAYER);
			root.getChild(1).addChild("Me dad grew melons, and me grandpappay, and me great grandpappy, and me great-great grandpappy... Just one of those melon-growing families, ya know?", DialogueType.NPC);
			root.getChild(1, 0).addChild(new DialogueNode("Uh, yeah. I know what you mean.", DialogueType.PLAYER));
		
		
		
		return root;
	}

}
