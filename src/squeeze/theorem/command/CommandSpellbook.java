package squeeze.theorem.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;

public class CommandSpellbook extends SQMCCommand {

	public CommandSpellbook() {
		super("spellbook");
	}
	
	public void onCommand(CommandSender sender, String[] args) {
		
		if(sender.equals(Bukkit.getConsoleSender())) {
			sender.sendMessage(ChatColor.RED + "Only in-game players can use the spellbook command.");
			return;
		} 
		
		Player player = (Player) sender;
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
		dat.getSpellbook().open(player);
		
	}

}
