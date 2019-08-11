package squeeze.theorem.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import squeeze.theorem.ui.ChestInterface;

public class CommandSettings extends SQMCCommand {

	public CommandSettings() {
		super("settings");
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		
		if(sender.equals(Bukkit.getConsoleSender())) {
			sender.sendMessage(ChatColor.RED + "Only in-game players can use the /settings command.");
			return;
		}
		ChestInterface.settings.open((Player) sender);
		
	}

}
