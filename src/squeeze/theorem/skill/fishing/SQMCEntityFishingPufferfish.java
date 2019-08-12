package squeeze.theorem.skill.fishing;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PufferFish;

import squeeze.theorem.item.CustomItem;
import squeeze.theorem.item.DropTable;

public class SQMCEntityFishingPufferfish extends SQMCEntityFish{

	public SQMCEntityFishingPufferfish() {
		super(ChatColor.BLUE + "Pufferfish", EntityType.PUFFERFISH);
		this.setDropTable(new DropTable().addDrop(CustomItem.SQUID, 100));
		setLevelRequired(25);
		setXP(80);
		setMaterial(Material.PUFFERFISH);
	}
	
	
	@Override
	public void onSpawn(LivingEntity entity) {
		 PufferFish puff = (PufferFish) entity;
		 puff.setPuffState(10);
	}

}
