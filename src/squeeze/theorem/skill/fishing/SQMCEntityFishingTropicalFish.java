package squeeze.theorem.skill.fishing;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.TropicalFish;
import org.bukkit.entity.TropicalFish.Pattern;

import squeeze.theorem.item.CustomItem;
import squeeze.theorem.item.DropTable;

public class SQMCEntityFishingTropicalFish extends SQMCEntityFish {

	public SQMCEntityFishingTropicalFish() {
		super(ChatColor.BLUE + "Tropical fish", EntityType.TROPICAL_FISH);
		this.setDropTable(new DropTable().addDrop(CustomItem.TROPICAL_FISH, 100));
		setLevelRequired(15);
		setXP(40);
		setMaterial(Material.TROPICAL_FISH);
		
	}

	@Override
	public LivingEntity spawn(Location loc) {
		
		Entity entity = super.spawn(loc);
		TropicalFish tropicalFish = (TropicalFish) entity;
		
		Random RNG = new Random();
		int roll1 = RNG.nextInt(DyeColor.values().length);
		int roll2 = RNG.nextInt(DyeColor.values().length);
		int roll3 = RNG.nextInt(DyeColor.values().length);
		
		tropicalFish.setBodyColor(DyeColor.values()[roll1]);
		tropicalFish.setPatternColor(DyeColor.values()[roll2]);
		tropicalFish.setPattern(Pattern.values()[roll3]);
		
		return tropicalFish;
		
	}

}
