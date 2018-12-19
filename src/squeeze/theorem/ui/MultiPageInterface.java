package squeeze.theorem.ui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;

public class MultiPageInterface extends UserInterface {

	/*Static fields*/
	private static ForwardButton forward = new ForwardButton();
	private static BackwardButton back = new BackwardButton();
	
	/*Fields*/
	private String title;
	private ArrayList<UIComponent> components = new ArrayList<UIComponent>();
		
	private int size;

	/*Constructors*/
	public MultiPageInterface(String title, int size) {
		setTitle(title);
		setSize(size);
		components.add(back);
		components.add(forward);
	}

	/*Setters and getters*/
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String getTitle(Player player) {
		return title;
	}


	@Override
	public void open(Player player) {
		PlayerData dat = DataManager.getPlayerData(player.getUniqueId());
		dat.getSessionData().setUIpage(0);
		player.openInventory(getInventory(player));
		
	}

	public void addComponent(UIComponent comp) {
		this.components.add(comp);
	}
	
	@Override
	public List<UIComponent> getComponents() {
		return this.components;
	}

	@Override
	public Inventory getInventory(Player player) {
		
		PlayerData dat = DataManager.getPlayerData(player.getUniqueId());
		
		Inventory inv;
		
		if(this.components.size() > getSize()) {
			inv = Bukkit.createInventory(null, getSize() + 9, ChatColor.DARK_PURPLE + getTitle(player));
			inv.setItem(inv.getSize() - 2, back.getItemStack(player));
			inv.setItem(inv.getSize() - 1, forward.getItemStack(player));
		} else {
			inv = Bukkit.createInventory(null, getSize(), ChatColor.DARK_PURPLE + getTitle(player));
		}
		
		for(UIComponent comp: getComponents(dat.getSessionData().getUIpage())) {
			if(comp != back && comp != forward) inv.addItem(comp.getItemStack(player));
		}
	
		return inv;
		
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	private List<UIComponent> getComponents(int page){
		
		ArrayList<UIComponent> output = new ArrayList<UIComponent>();
		
		for(int i = 2; i <= getSize() + 1; i++) {
			
			int index = i + page * getSize();
			if(components.size() > index) output.add(components.get(index));
			
		}
		
		
		return output;
		
	}

}
