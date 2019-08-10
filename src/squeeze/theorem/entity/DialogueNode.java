package squeeze.theorem.entity;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.data.SessionData;

public class DialogueNode {
	
	/*Static fields*/
	public static enum DialogueType{
		NPC,
		PLAYER;
	}
	
	/*Fields*/
	private ArrayList<DialogueNode> children = new ArrayList<DialogueNode>();
	private String text;
	private DialogueNode dest;
	private DialogueType dialogueType;
	
	/*Constructors*/
	public DialogueNode(String text) {
		setText(text);
	}
	
	public DialogueNode(String text, DialogueType type) {
		setText(text);
		setType(type);
	}
	
	/*Setters and getters*/
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public void setType(DialogueType type) {
		this.dialogueType = type;
	}
	
	public DialogueType getType() {
		if(this.dialogueType != null) return this.dialogueType;
		if(getChildren().size() <= 1) return DialogueType.PLAYER;
		return DialogueType.NPC;
	}
	
	public DialogueNode getDest() {
		return dest;
	}

	public DialogueNode setDest(DialogueNode dest) {
		this.dest = dest;
		return this;
	}

	public DialogueNode setChildren(ArrayList<DialogueNode> children) {
		this.children = children;
		return this;
	}
	
	public ArrayList<DialogueNode> getChildren(){
		return children;
	}
	
	public void addChild(DialogueNode child) {
		children.add(child);
	}
	
	public void addChild(String text) {
		children.add(new DialogueNode(text));
	}

	public void addChild(String text, DialogueType type) {
		children.add(new DialogueNode(text, type));
	}

	public void addChild(String text, DialogueNode dest) {
		children.add(new DialogueNode(text).setDest(dest));
	}
	
	public void addChild(String text, DialogueType type, DialogueNode dest) {
		children.add(new DialogueNode(text, type).setDest(dest));
	}
	
	public void append(DialogueNode node) {
		DialogueNode end = this;
		while(end.getChild(0) != null) {
			end = end.getChild(0);
		}
		end.addChild(node);
	}
	
	public void append(String text, DialogueType type) {
		append(new DialogueNode(text, type));
	}
	
	public DialogueNode getChild(int index) {
		if(index < 0) return null;
		if(children.size() > index) return children.get(index);
		return null;
	}

	public DialogueNode getChild(int... indices) {
		
		DialogueNode output = this;
		for(int i: indices) {
				output = output.getChild(i);
		}
		
		return output;
		
	}	
	
	/*Methods*/
	//Intended to be overwritten
	public void onSelect(Player player) {

	}

	public void send(Player player) {
		
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
		SessionData sessionData = dat.getSessionData();
		sessionData.setDialogueNode(this);
		
	}
	
}
