package squeeze.theorem.item;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;

import squeeze.theorem.skill.Skill;

public class CustomItemPickaxe extends CustomItem implements Tool {

	private HashMap<Skill, Integer> requirements = new HashMap<Skill, Integer>();
	private double successRate;
	private int tier = 1;
	private double luck;
	
	
	//tier
	public CustomItemPickaxe(int ID, String name, Material material, int levelRequired, double successRate, int tier, double luck, String... lore) {
		super(ID, name, material, lore);
		setSuccessRate(successRate);
		setTier(tier);
		setLuck(luck);
		requirements.put(Skill.mining, levelRequired);
	}

	@Override
	public Map<Skill, Integer> getRequirements() {
		return this.requirements;
	}

	public void addRequirement(Skill s, int lvl) {
		requirements.put(s, lvl);
	}

	@Override
	public int getLevelRequired(Skill s) {
		return getRequirements().get(s);
	}

	@Override
	public double getSuccessRate() {
		return this.successRate;
	}

	public void setSuccessRate(double successRate) {
		this.successRate = successRate;

	}
	
	public void setTier(int tier) {
		this.tier = tier;
	}
	
	public int getTier() {
		return this.tier;
	}

	public double getLuck() {
		return luck;
	}

	public void setLuck(double luck) {
		this.luck = luck;
	}

}
