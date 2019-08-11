package squeeze.theorem.skill;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import squeeze.theorem.ui.ChestInterface;

public class SkillStrength extends Skill {

	public SkillStrength() {
		this.setMaterial(Material.IRON_SWORD);
		this.setName("Strength");
	}
	
	@Override
	public ChestInterface getSkillGuide(Player player) {
		return ChestInterface.skillguideStrength;
	}
	
}
