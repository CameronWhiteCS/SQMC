package squeeze.theorem.skill.fishing;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import squeeze.theorem.item.CustomItem;
import squeeze.theorem.item.DropTable;

public class SQMCEntityFishingSalmon extends SQMCEntityFish {

	public SQMCEntityFishingSalmon() {
		super(ChatColor.BLUE + "Salmon", EntityType.SALMON);
		this.setName(ChatColor.BLUE + "Salmon");
		this.setEntityType(EntityType.SALMON);
		this.setDropTable(new DropTable().addDrop(CustomItem.SALMON, 100));
		setLevelRequired(1);
		setXP(10);
		setMaterial(Material.SALMON);
	}

}
