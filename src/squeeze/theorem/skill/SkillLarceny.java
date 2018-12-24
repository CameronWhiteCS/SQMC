package squeeze.theorem.skill;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import squeeze.theorem.ui.UserInterface;

public class SkillLarceny extends Skill {

	public SkillLarceny() {
		this.setMaterial(Material.CHEST);
		this.setName("Larceny");
		
	}

	@Override
	public UserInterface getSkillGuide(Player player) {
		return UserInterface.skillguideLarceny;
	}
	
	
	
}
