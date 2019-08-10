package squeeze.theorem.skill;

import java.util.ArrayList;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;

public interface LevelRequirements {

	public Map<Skill, Integer> getRequirements();
	
	public default int getLevelRequired(Skill s) {
		return getRequirements().get(s);
	}
	
	public default ArrayList<String> getLevelRequirementLore(Player player){
	
		ArrayList<String> lore = new ArrayList<String>();
		
		for(Skill s: getRequirements().keySet()) {
			
			lore.add(getLevelRequirementLore(player, s));
			
		}
		
		return lore;
		
	}
	
	public default String getLevelRequirementLore(Player player, Skill skill){
		
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
			
		if (dat.getLevel(skill) >= getLevelRequired(skill)) {
			return ChatColor.GREEN + "Level " + getLevelRequired(skill) + " " + skill.getProperName() + " \u2713";
		} else {
			return ChatColor.RED + "Level " + getLevelRequired(skill) + " " + skill.getProperName() + " \u2715";
		}
		
	}
	
	public default boolean meetsRequirements(Player player) {
		
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
		for(Skill s: getRequirements().keySet()) {
			if(dat.getLevel(s) < getRequirements().get(s)) {
				return false;
			}
		}
		
		return true;
	}
	
	public default void sendInsufficientLevelNotice(Player player, String action) {
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
		for(Skill s: getRequirements().keySet()) {
			if(dat.getLevel(s) < getRequirements().get(s)) {
				player.sendMessage(ChatColor.RED + "You need at least level " + getRequirements().get(s) + " " + s.getProperName() + " to " + action + ".");
			}
		}
	}
	
}
