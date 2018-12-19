package squeeze.theorem.command;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

public abstract class SQMCCommand {
	
	/*Static fields*/
	private static ArrayList<SQMCCommand> commands = new ArrayList<SQMCCommand>();
	public static SQMCCommand skills = new CommandSkills();
	public static SQMCCommand quest = new CommandQuest();
	public static SQMCCommand itemgen = new CommandItemGen();
	public static SQMCCommand dialogue = new CommandDialogue();
	public static SQMCCommand spellbook = new CommandSpellbook();
	public static SQMCCommand commandSqmcXP = new CommandSqmcXP();
	public static SQMCCommand commandSettings = new CommandSettings();
	
	/*Fields*/
	private String label;
	private String permission;
	
	/*Constructors*/
	public SQMCCommand(String label) {
		setLabel(label);
		commands.add(this);
	}
	
	/*Setters and getters*/
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public static ArrayList<SQMCCommand> getCommands(){
		return commands;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	public abstract void onCommand(CommandSender sender, String[] args);
	
}
