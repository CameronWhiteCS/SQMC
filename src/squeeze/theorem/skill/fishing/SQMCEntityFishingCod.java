package squeeze.theorem.skill.fishing;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import squeeze.theorem.item.CustomItem;
import squeeze.theorem.item.DropTable;

public class SQMCEntityFishingCod extends SQMCEntityFish {

	public SQMCEntityFishingCod() {
		super(ChatColor.BLUE + "Cod", EntityType.COD);
		this.setDropTable(new DropTable().addDrop(CustomItem.COD, 100));
		setLevelRequired(5);
		setXP(20);
		setMaterial(Material.COD);

	}


}
