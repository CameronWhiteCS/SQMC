package squeeze.theorem.skill;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import squeeze.theorem.ui.ChestInterface;

public class SkillLarceny extends Skill {

	public SkillLarceny() {
		this.setMaterial(Material.CHEST);
		this.setName("Larceny");
		
	}

	@Override
	public ChestInterface getSkillGuide(Player player) {
		return ChestInterface.skillguideLarceny;
	}
	
	
	
}
