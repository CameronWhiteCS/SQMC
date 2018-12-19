package squeeze.theorem.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import squeeze.theorem.ui.UserInterface;

public class CommandSkills extends SQMCCommand {

	public CommandSkills() {
		super("skills");
	}

	@SuppressWarnings("deprecation")
	public void onCommand(CommandSender sender, String[] args) {

		if (sender == Bukkit.getConsoleSender()) {
			
			sender.sendMessage(ChatColor.RED + "Only in-game players can use the /skills command.");
			return;
		}

		Player target = (Player) sender;

		if (args.length >= 1) {

			if (Bukkit.getPlayer(args[0]) != null) {
				target = Bukkit.getPlayer(args[0]);
			} else {
				sender.sendMessage(ChatColor.RED + "That player is offline.");
				return;
			}

		}

		Player player = (Player) sender;
		player.openInventory(UserInterface.skills.getInventory(target));

	}

}
