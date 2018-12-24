package squeeze.theorem.skill;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import squeeze.theorem.ui.UserInterface;

public class SkillStrength extends Skill {

	public SkillStrength() {
		this.setMaterial(Material.IRON_SWORD);
		this.setName("Strength");
	}
	
	@Override
	public UserInterface getSkillGuide(Player player) {
		return UserInterface.skillguideStrength;
	}
	
}
