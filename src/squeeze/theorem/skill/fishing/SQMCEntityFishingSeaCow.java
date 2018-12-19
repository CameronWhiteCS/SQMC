package squeeze.theorem.skill.fishing;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import squeeze.theorem.item.CustomItem;
import squeeze.theorem.item.DropTable;

public class SQMCEntityFishingSeaCow extends SQMCEntityFish {

	public SQMCEntityFishingSeaCow() {
		super(ChatColor.BLUE + "Sea cow", EntityType.COW);
		this.setDropTable(new DropTable().addDrop(CustomItem.LEATHER, 100));
		setLevelRequired(45);
		setXP(500);
		setMaterial(Material.COW_SPAWN_EGG);
	}

}
