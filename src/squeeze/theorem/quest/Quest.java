package squeeze.theorem.quest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.ui.UIComponent;

public abstract class Quest  {

	
	/*Static fields*/
	private static List<Quest> quests = new ArrayList<Quest>();
	public static Quest embargo = new QuestEmbargo();
	
	/*Fields*/
	private String title;
	private String[] description = {"Description"};
	private String startFlag;
	private String endFlag;
	private String startPoint = "";
	private Material material;
	private String reward = "";
	private Map<String, String> questLog = new LinkedHashMap<String, String>();
	
	/*Constructors*/
	public Quest(String title, String startFlag, String endFlag, Material material) {
		setTitle(title);
		setStartFlag(startFlag);
		setEndFlag(endFlag);
		setMaterial(material);
		quests.add(this);
	}
	
	public Quest() {
		quests.add(this);
	}
	
	/*Setters and getters*/
	public Material getMaterial() {
		return material;
	}

	public Quest setMaterial(Material material) {
		this.material = material;
		return this;
	}

	public String getEndFlag() {
		return endFlag;
	}

	public Quest setEndFlag(String endFlag) {
		this.endFlag = endFlag;
		return this;
	}

	public String getStartFlag() {
		return startFlag;
	}

	public Quest setStartFlag(String startFlag) {
		this.startFlag = startFlag;
		return this;
	}

	public String[] getDescription() {
		return description;
	}

	public Quest setDescription(String...description) {
		this.description = description;
		return this;
	}

	public String getTitle() {
		return title;

	}

	public Quest setTitle(String title) {
		this.title = title;
		return this;
	}

	public static List<Quest> getQuests(){
		return quests;
	}
	
	public String getStartPoint() {
		return startPoint;
	}

	public Quest setStartPoint(String startPoint) {
		this.startPoint = startPoint;
		return this;
	}
	
	public String getReward() {
		return reward;
	}

	public Quest setReward(String reward) {
		this.reward = reward;
		return this;
	}
	
	public void addQuestLogEntry(String flag, String page) {
		questLog.put(flag, page);
	}

	/*Methods*/
	public UIComponent getUIComponent() {
		return new UIComponent() {

			@Override
			public ItemStack getItemStack(Player player) {
				DataManager dataManager = DataManager.getInstance();
				PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
				ItemStack stack = new ItemStack(getMaterial());
				ItemMeta meta = stack.getItemMeta();
				
				meta.setDisplayName(ChatColor.GOLD + getTitle());
				List<String> lore = new ArrayList<String>();
				for(String s: getDescription()) {
					lore.add(ChatColor.DARK_PURPLE + s);
				}
				lore.add(ChatColor.GRAY + "Start point: " + getStartPoint());
				lore.add(ChatColor.GRAY + "Reward: " + getReward());
				
				if(!dat.hasFlags(startFlag)) {
					lore.add(ChatColor.RED + "Not started");
				} else if(dat.hasFlags(startFlag) && !dat.hasFlags(endFlag)){
					lore.add(ChatColor.GOLD + "In progress");
				} else {
					lore.add(ChatColor.GREEN + "Complete");
				}
				
				
				
				meta.setLore(lore);
				stack.setItemMeta(meta);
				return stack;
			}

			@Override
			public void onClick(InventoryClickEvent evt) {
				DataManager dataManager = DataManager.getInstance();
				PlayerData dat = dataManager.getPlayerData(evt.getWhoClicked().getUniqueId());
				dat.giveItem(getQuestLog((Player)evt.getWhoClicked()));
				((Player) evt.getWhoClicked()).closeInventory();
				
			}
			
		};
	}

	public ItemStack getQuestLog(Player player) {
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
		
		ItemStack stack = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bm = (BookMeta) stack.getItemMeta();
		bm.setAuthor("SQMC Team");
		bm.setDisplayName(ChatColor.GOLD + getTitle() + " Quest Log");
		
		for(String flag: questLog.keySet()) {
			if(dat.hasFlags(flag)) bm.addPage("§6" + getTitle() + "§r\n" + questLog.get(flag));
		}
		
		stack.setItemMeta(bm);
		return stack;
	}

}
