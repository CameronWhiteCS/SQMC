package squeeze.theorem.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import squeeze.theorem.ui.UserInterface;

public class CommandQuest extends SQMCCommand {

	public CommandQuest() {
		super("quest");
	}

	public void onCommand(CommandSender sender, String[] args) {
		
		if(sender.equals(Bukkit.getConsoleSender())) {
			sender.sendMessage(ChatColor.RED + "Only in-game players can use the /quest command");
			return;
		}
		
		UserInterface.quests.open((Player) sender);

	}
	
}
