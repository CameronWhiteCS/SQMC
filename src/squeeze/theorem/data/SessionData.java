package squeeze.theorem.data;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;

import mkremins.fanciful.FancyMessage;
import squeeze.theorem.entity.DialogueNode;
import squeeze.theorem.entity.DialogueNode.DialogueType;
import squeeze.theorem.entity.SQMCEntity;
import squeeze.theorem.recipe.SQMCRecipe;
import squeeze.theorem.skill.witchcraft.Spell;
import squeeze.theorem.ui.ChestInterface;

public class SessionData {

	private Entity NPC;
	private DialogueNode dialogueNode;
	private PlayerData playerData;
	private HashMap<Integer, Spell> spells = new HashMap<Integer, Spell>();
	private int UIpage;
	private SQMCRecipe recipe;
	private Location craftingLocation;
	private ChestInterface chestInterface;
	private Inventory interfaceInventory;

	public SessionData(PlayerData dat) {
		setPlayerData(dat);
	}

	public Entity getNPC() {
		return NPC;
	}

	public void setNPC(Entity nPC) {
		NPC = nPC;
	}

	public DialogueNode getDialogueNode() {
		return dialogueNode;
	}

	public void setDialogueNode(DialogueNode dialogueNode) {

		this.dialogueNode = dialogueNode;
		
		//Allow for legitimate null asignments
		if(getNPC() == null) return;
		if(dialogueNode == null) return;
		
		//Name of NPC
		String name = getNPC().getCustomName();
		if(SQMCEntity.getSQMCEntity(getNPC()) != null) {
			name = SQMCEntity.getSQMCEntity(getNPC()).getName();
		}
		if(dialogueNode.getType() == DialogueType.NPC) {
			getPlayerData().getPlayer().sendMessage(ChatColor.GOLD + "[" + ChatColor.BLUE + ChatColor.stripColor(name) + ChatColor.GOLD + "] " + ChatColor.GOLD + dialogueNode.getText());
		}
		
		//Input feedback & GOTO
		if(dialogueNode.getType() == DialogueType.PLAYER) {
			getPlayerData().getPlayer().sendMessage(ChatColor.GREEN + ">>> " + dialogueNode.getText());
			if(dialogueNode.getDest() != null) {
				setDialogueNode(dialogueNode.getDest());
			} else {
				setDialogueNode(dialogueNode.getChild(0));
			}
			
		}
		
		
		//Send formatted dialogue options
		if(dialogueNode.getChildren().size() >= 1 && dialogueNode.getType() == DialogueType.NPC){
			ArrayList<DialogueNode> nodes = dialogueNode.getChildren();
			for(int i = 0; i < nodes.size(); i++) {
				FancyMessage msg = new FancyMessage("[" + (i+1) + "] " + replaceVars(nodes.get(i).getText())).command("/dialogue " + (i+1)).color(ChatColor.GRAY);
				msg.send(getPlayerData().getPlayer());
			}
		}
		
		//End conversations
		if(dialogueNode.getChildren().size() == 0 && dialogueNode.getDest() == null) {
			endConversation(ChatColor.GRAY + "=======");
		}

		//Run the onSelect method
		dialogueNode.onSelect(getPlayerData().getPlayer());
		
	}
	
	public void endConversation() {
		
		endConversation(ChatColor.RED + "End of conversation.");

	}
	
	public void endConversation(String message) {
		
		if(getDialogueNode() != null || getNPC() != null) {

			setDialogueNode(null);
			setNPC(null);
			getPlayerData().getPlayer().sendMessage(message);
			
		}

	}
	
	private String replaceVars(String s) {
		return s.replaceAll("$NAME", getPlayerData().getPlayer().getName());
	}

	public PlayerData getPlayerData() {
		return playerData;
	}

	public void setPlayerData(PlayerData playerData) {
		this.playerData = playerData;
	}

	public void setSpell(int slot, Spell spell) {
		spells.put(slot, spell);
	}

	public Spell getSpell(int slot) {
		return this.spells.get(slot);
	}

	public int getUIpage() {
		return UIpage;
	}

	public void setUIpage(int uIpage) {
		UIpage = uIpage;
	}

	public SQMCRecipe getRecipe() {
		return recipe;
	}

	public void setRecipe(SQMCRecipe recipe) {
		this.recipe = recipe;
	}

	public Location getCraftingLocation() {
		return this.craftingLocation;
	}

	public void setCraftingLocation(Location craftingLocation) {
		this.craftingLocation = craftingLocation;
		
	}

	public ChestInterface getChestInterface() {
		return chestInterface;
	}

	public void setChestInterface(ChestInterface chestInterface) {
		this.chestInterface = chestInterface;
	}

	public Inventory getInterfaceInventory() {
		return interfaceInventory;
	}

	public void setInterfaceInventory(Inventory interfaceInventory) {
		this.interfaceInventory = interfaceInventory;
	}

}
