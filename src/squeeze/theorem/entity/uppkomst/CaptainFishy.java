package squeeze.theorem.entity.uppkomst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import squeeze.theorem.entity.DialogueNode;
import squeeze.theorem.entity.DialogueNode.DialogueType;
import squeeze.theorem.entity.EyeTracking;
import squeeze.theorem.entity.NPC;
import squeeze.theorem.entity.Pickpocketable;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.skill.Skill;
import squeeze.theorem.ui.UserInterfaceShop;

public class CaptainFishy extends NPC implements EyeTracking, Pickpocketable {

	private UserInterfaceShop shop = new UserInterfaceShop("Captain Fishy's Bait & Tackle");
	
	
	public CaptainFishy() {
		super("Fishy", EntityType.VILLAGER);
		setPrefix("Captain");
		this.setAI(false);
		this.setInvulnerable(true);
		shop.addItem(CustomItem.FISHING_ROD, 10, 8);
		shop.addItem(CustomItem.FEATHER, 1, 1, 5);
		addLocation(new Location(Bukkit.getWorld("world"), 4410, 19, -8649));
		this.setSilent(true);
	}
	
	@Override
	public DialogueNode getDialogueNode(Player player) {
		
		DialogueNode root = new DialogueNode("Aye lad, what can I do ye 'fer?", DialogueType.NPC);
			root.addChild(new DialogueNode("What've you got for sale?", DialogueType.PLAYER) {
				@Override
				public void onSelect(Player player) {
					shop.open(player);
				}
			});
		
		return root;
		
	}

	@Override
	public Map<Skill, Integer> getRequirements() {
		Map<Skill, Integer> reqs = new HashMap<Skill, Integer>();
		reqs.put(Skill.larceny, 1);
		return reqs;
	}

	@Override
	public List<ItemStack> getLoot(Player player) {
		List<ItemStack> loot = new ArrayList<ItemStack>();
		loot.add(CustomItem.FEATHER.getItemStack());
		return loot;
	}

	@Override
	public boolean didSucceed(Player player) {
		int roll = new Random().nextInt(2);
		return(roll == 1);
	}

	@Override
	public double getDamageOnFail(Player player) {
		return 2;
	}

	@Override
	public int getCooldown(Player player) {
		return 60;
	}

	@Override
	public double getXP() {
		return 83D;
	}

	@Override
	public String getFailMessage() {
		return "Hey, what do you think you're doing!";
	}
	
}



