package squeeze.theorem.skill.conjuration;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import squeeze.theorem.item.CustomItem;
import squeeze.theorem.skill.Skill;

public class SaddledPig extends Familiar {

	public SaddledPig() {
		super("Saddled Pig", EntityType.PIG);
		this.setMaterial(Material.PIG_SPAWN_EGG);
		this.setSummonXp(50);
		this.addRequirement(Skill.conjuration, 1);
		this.setCooldown(300 * 20);
		this.setLifespan(20*30);
		this.setHealth(10);
	}
	
	@Override
	public void onSpawn(LivingEntity le) {
		super.onSpawn(le);
		Pig pig = (Pig) le;
		pig.setSaddle(true);
		le.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.125);
	}
	
	@Override
	public void onSummon(Player player, LivingEntity le) {
		ItemStack carrotStick = CustomItem.CARROT_ON_STICK.getItemStack(1);
		if(!player.getInventory().containsAtLeast(carrotStick, 1)) {
			player.getInventory().addItem(carrotStick);
		}
	}

}
