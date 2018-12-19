package squeeze.theorem.skill.fishing;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import squeeze.theorem.item.CustomItem;
import squeeze.theorem.item.DropTable;

public class SQMCEntityFishingTurtle extends SQMCEntityFish {

	public SQMCEntityFishingTurtle() {
		super(ChatColor.BLUE + "Sea turtle", EntityType.TURTLE);
		this.setDropTable(new DropTable().addDrop(CustomItem.SCUTE, 100));
		setLevelRequired(39);
		setXP(320);
		setMaterial(Material.TURTLE_EGG);
	}


	
	
}
