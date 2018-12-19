package squeeze.theorem.entity;

import org.bukkit.entity.Player;

import squeeze.theorem.item.Lootable;
import squeeze.theorem.skill.LevelRequirements;

public interface Pickpocketable extends LevelRequirements, Lootable {

	public boolean didSucceed(Player player);
	public double getDamageOnFail(Player player);
	public int getCooldown(Player player);
	public double getXP();
	public String getFailMessage();
	
}
