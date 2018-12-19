package squeeze.theorem.entity.uppkomst;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import squeeze.theorem.combat.AttackStyle;
import squeeze.theorem.combat.CombatStats;
import squeeze.theorem.entity.DialogueNode;
import squeeze.theorem.entity.NPC;
import squeeze.theorem.entity.Respawnable;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.item.DropTable;

public class BotchedExperiment extends NPC implements Respawnable, CombatStats  {

	private ArrayList<DropTable> dropTables = new ArrayList<DropTable>();
	
	public BotchedExperiment() {
		super("Botched Experiment", EntityType.DROWNED);
		this.setAI(false);
		this.setInvulnerable(false);
		addLocation(new Location(Bukkit.getWorld("world"), 4447, 60, -8586, 180, 0));
		dropTables.add(new DropTable().addDrop(CustomItem.GOLD_INGOT, 1));
	}

	@Override
	public DialogueNode getDialogueNode(Player player) {
		DialogueNode root = new DialogueNode("Neverending pain... Oh the agony!", NPC) {
			@Override
			public void onSelect(Player player) {
				player.sendMessage(ChatColor.RED + "He appears to be shuttering in pain. Maybe I can help him somehow.");
			}

		};
		
		return root;
	}

	@Override
	public int getRespawnDelay() {
		return 15 * 20;
	}

	@Override
	public double getStrength(AttackStyle style) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDefense(AttackStyle style) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAccuracy(AttackStyle style) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getHealth() {
		return 10;
	}

}
