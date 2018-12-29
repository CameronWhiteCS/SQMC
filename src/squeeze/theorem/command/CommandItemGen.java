package squeeze.theorem.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import squeeze.theorem.item.CustomItem;

public class CommandItemGen extends SQMCCommand {

	public CommandItemGen() {
		super("itemgen");
	}

	public void onCommand(CommandSender sender, String[] args) {

		if (sender.equals(Bukkit.getConsoleSender())) {
			sender.sendMessage(ChatColor.RED + "Only in-game players can use the itemgen command");
			return;
		}

		if(args.length < 1) {
			sender.sendMessage(ChatColor.RED + "No arguments specified. Usage: /itemgen <name> <amount>");
		}
			
		CustomItem item = null;
		int amount = 1;
		
		for(CustomItem ci: CustomItem.getItems()) {
			if(ci.getName().toLowerCase().equals(ChatColor.stripColor(args[0].toLowerCase().replaceAll("_", " ")))) {
				item = ci;
				break;
			}
		}
		
		if(item == null) {
			sender.sendMessage(ChatColor.RED + "Unknown item: " + args[0].toLowerCase().replaceAll("_", " "));
			return;
		} 
		
		try {
			amount = Integer.parseInt(args[1]);
		} catch(Exception exc) {
			if(args.length > 1) sender.sendMessage(ChatColor.RED + "Failed to parse int: " + args[1]);
			return;
		}
		
		Player player = (Player) sender;
		player.getInventory().addItem(item.getItemStack(amount));

	}

}
