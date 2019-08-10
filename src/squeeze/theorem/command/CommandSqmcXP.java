package squeeze.theorem.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.skill.Skill;

public class CommandSqmcXP extends SQMCCommand {

	public CommandSqmcXP() {
		super("sqmcxp");
		
	}
	
	@Override
	public void onCommand(CommandSender sender, String[] args) {
		if(sender.equals(Bukkit.getConsoleSender())) {
			sender.sendMessage(ChatColor.RED + "Only in-game players can use the setxp command");
			return;
		}
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(((Player) sender).getUniqueId());
		
		Skill skill = null;
		for(Skill s: Skill.getSkills()) {
			if(s.getName().toLowerCase().equals(args[0].toLowerCase())) skill = s;
		}
		
		if(skill == null) {
			sender.sendMessage(ChatColor.RED + "Unknown skill: " + args[0]);
			return;
		}
		
		Double xp = 0.0;
		try {
			xp = Double.parseDouble(args[1]);
		}catch(Exception exc) {
			sender.sendMessage(ChatColor.RED + "Failed to parse double: " + args[1]);
			return;
		}
		
		
		dat.setXP(skill, xp);
	}

}
