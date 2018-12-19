package squeeze.theorem.item;

import java.util.ArrayList;

import org.bukkit.ChatColor;

import squeeze.theorem.skill.LevelRequirements;

public interface Tool extends LevelRequirements {

	public double getSuccessRate();
	
	public int getTier();
	
	public double getLuck();
	
	public default ArrayList<String> getToolLore(){
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.DARK_PURPLE + "Precision: " + getSuccessRate() + "%");
		lore.add(ChatColor.DARK_PURPLE + "Tier: " + getTier());
		lore.add(ChatColor.DARK_PURPLE + "Luck: " + getLuck() + "%");
		
		return lore;
		
	}
	
	
}
