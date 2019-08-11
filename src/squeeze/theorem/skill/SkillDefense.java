package squeeze.theorem.skill;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import squeeze.theorem.ui.ChestInterface;

public class SkillDefense extends Skill {

	public SkillDefense() {
		this.setMaterial(Material.SHIELD);
		this.setName("Defense");
	}
	
	@Override
	public ChestInterface getSkillGuide(Player player) {
		return ChestInterface.skillguideDefense;
	}
	
}
