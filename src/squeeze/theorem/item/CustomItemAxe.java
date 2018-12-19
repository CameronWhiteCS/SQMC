package squeeze.theorem.item;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;

import squeeze.theorem.skill.LevelRequirements;
import squeeze.theorem.skill.Skill;

public class CustomItemAxe extends CustomItem implements LevelRequirements{

	private double successRate;
	private HashMap<Skill, Integer> requirements = new HashMap<Skill, Integer>();
	private double luck;
	
	public CustomItemAxe(int ID, String name, Material material, int levelRequired, double successRate, double luck, String... lore) {
		super(ID, name, material, lore);
		setSuccessRate(successRate);
		setLuck(luck);
		requirements.put(Skill.woodcutting, levelRequired);
	}

	@Override
	public Map<Skill, Integer> getRequirements() {
		return this.requirements;
	}

	public void addRequirement(Skill s, int lvl) {
		requirements.put(s, lvl);
	}

	public double getSuccessRate() {
		return this.successRate;
	}

	public void setSuccessRate(double successRate) {
		this.successRate = successRate;
		
	}

	@Override
	public int getLevelRequired(Skill s) {
		return getRequirements().get(s);
	}

	public double getLuck() {
		return luck;
	}

	public void setLuck(double luck) {
		this.luck = luck;
	}

}
