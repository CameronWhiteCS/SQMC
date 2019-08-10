package squeeze.theorem.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.data.SessionData;
import squeeze.theorem.entity.DialogueNode;

public class CommandDialogue extends SQMCCommand{

	public CommandDialogue() {
		super("dialogue");
		
	}

	public void onCommand(CommandSender sender, String[] args) {
		
		if(sender.equals(Bukkit.getConsoleSender())) {
			sender.sendMessage(ChatColor.RED + "Only in-game players can use the dialogue command");
			return;
		}
		
		Player player = (Player) sender;
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
		SessionData sessionData = dat.getSessionData();
		DialogueNode node = sessionData.getDialogueNode();
		
		if(node == null) {
			player.sendMessage(ChatColor.RED + "You're not currently speaking to an NPC.");
			return;
		}
		
		int index;
		try {
			index = Integer.parseInt(args[0]) - 1;
		} catch(Exception exc) {
			player.sendMessage(ChatColor.RED + "Failed to parse integer: " + args[0]);
			return;
		}
		
		if(node.getChildren().size() > index && index >= 0) {
			sessionData.setDialogueNode(node.getChild(index));
		} else {
			player.sendMessage(ChatColor.RED + "Invalid dialogue path");
		}

	}

}
