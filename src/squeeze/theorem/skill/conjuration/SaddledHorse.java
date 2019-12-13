package squeeze.theorem.skill.conjuration;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import squeeze.theorem.skill.Skill;

public class SaddledHorse extends Familiar {

	public SaddledHorse() {
		super("Horse", EntityType.HORSE);
		this.setMaterial(Material.SADDLE);
		this.setSummonXp(100);
		this.setLifespan(300*20);
		this.setCooldown(30*60*20);
		this.addRequirement(Skill.conjuration, 10);
		this.setHealth(20);
	}
	
	@Override
	public void onSpawn(LivingEntity le) {
		super.onSpawn(le);
		Horse horse = (Horse) le;
		horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
		horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.2);
	}

}
