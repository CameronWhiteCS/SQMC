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
		
		PlayerData dat = DataManager.getPlayerData(player.getUniqueId());
		if(!dat.hasFlags("quest.join_the_revolution.start")) return joinTheRevolutionStart(player);
		
		return null;
	}
	
	@Override
	public LivingEntity spawn(Location loc) {
		Villager v = (Villager) super.spawn(loc);
		v.setProfession(Profession.PRIEST);
		return v;
	}

	@Override
	public double getWanderRadius() {
		return 15;
	}

	private DialogueNode joinTheRevolutionStart(Player player) {
		
		DialogueNode root = new DialogueNode("The gentle laborer shall no longer suffer from the noxious greed of Brightbriar! We will dismantle oppression ore-by-ore! We will mine the support columns of tyranny in half!", DialogueType.NPC);
		root.addChild("What the hell are you talking about?", DialogueType.PLAYER);
		root.getChild(0).addChild("Brightbriar's greed has been left unchecked for far too long!", DialogueType.NPC);
			root.getChild(0, 0).addChild("I still don't follow... Who is this Brightbriar character?", DialogueType.PLAYER);
			root.getChild(0, 0, 0).addChild("He is the flithiest of the bourgeoisie! He poisoned our water supply, burned our crops, and delivered a plague unto our houses!", DialogueType.NPC);
			root.getChild(0, 0, 0, 0).addChild("He did!?", DialogueType.PLAYER);
			root.getChild(0, 0, 0, 0, 0).addChild("No... But are we gonna sit around until he does?", DialogueType.NPC);
			root.getChild(0, 0, 0, 0, 0, 0).addChild("You're right! What can I do to help stop Brightbriar?", DialogueType.PLAYER);
		root.addChild("I agree. It's high time we seize the means of production and abolish private property. The proletariat have nothing to lose but their chains!!!", DialogueType.PLAYER);
			root.getChild(1).addChild(new DialogueNode("I knew it! I know a Bolshevik when I see one! Here, take these. We're recruting for the Labor Party. I need you to hand them out to people around town.", DialogueType.NPC) {
				@Override
				public void onSelect(Player player) {
					PlayerData dat = DataManager.getPlayerData(player.getUniqueId());
					dat.addFlags("quest.join_the_revolution.start", "quest.join_the_revolution.grant_manifesto");
						dat.giveItem(CustomItem.THE_COMMUNIST_MANIFESTO, 5);
				}
			});
			root.getChild(1, 0).addChild("With alacrity, comrade!", DialogueType.PLAYER);
			
		return root;
		
	}
	
}
