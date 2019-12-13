package squeeze.theorem.skill;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import squeeze.theorem.ui.ChestInterface;

public class SkillConjuration extends Skill {

	public SkillConjuration() {
		this.setMaterial(Material.WITHER_SKELETON_SKULL);
		this.setName("Conjuration");
	}
	
	@Override
	public ChestInterface getSkillGuide(Player player) {
		return ChestInterface.skillguideConjuration;
	}

}
