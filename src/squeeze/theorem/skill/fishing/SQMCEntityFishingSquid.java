package squeeze.theorem.skill.fishing;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import squeeze.theorem.item.CustomItem;
import squeeze.theorem.item.DropTable;

public class SQMCEntityFishingSquid extends SQMCEntityFish {

	public SQMCEntityFishingSquid() {
		super(ChatColor.BLUE + "Squid", EntityType.SQUID);
		this.setDropTable(new DropTable().addDrop(CustomItem.SQUID, 100));
		setLevelRequired(32);
		setXP(160);
		setMaterial(Material.INK_SAC);
	}

}
