package squeeze.theorem.entity.uppkomst;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager.Profession;

import squeeze.theorem.entity.DialogueNode;
import squeeze.theorem.entity.EyeTracking;
import squeeze.theorem.entity.NPC;

public class MagicStudent extends NPC implements EyeTracking {
	
	public MagicStudent() {
		super("Magic Student", EntityType.VILLAGER);
		this.setAI(false);
		addLocation(new Location(Bukkit.getWorld("world"), 4447.5, 64.18750, -8593.5));
		addLocation(new Location(Bukkit.getWorld("world"), 4447.5, 64.18750, -8591.5));
		addLocation(new Location(Bukkit.getWorld("world"), 4444.5, 64.18750, -8592.5));
		setProfession(Profession.NITWIT);
	}

	@Override
	public DialogueNode getDialogueNode(Player player) {
		
		int roll = new Random().nextInt(3);
		
		if(roll == 0) return new DialogueNode("Ugh, MGC1001 is such a terrible course...", NPC);
		if(roll == 1) return new DialogueNode("This guy is awful, I'm giving him a 1/5 on RateMyProfessor!", NPC);
		if(roll == 2) return new DialogueNode("50,000 in debt for this terrible lecture?", NPC);
		
		return null;
		
	}

}
