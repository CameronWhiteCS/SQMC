package squeeze.theorem.skill;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import squeeze.theorem.ui.UserInterface;

public class SkillDefense extends Skill {

	public SkillDefense() {
		this.setMaterial(Material.SHIELD);
		this.setName("Defense");
	}
	
	@Override
	public UserInterface getSkillGuide(Player player) {
		return UserInterface.skillguideDefense;
	}
	
}
