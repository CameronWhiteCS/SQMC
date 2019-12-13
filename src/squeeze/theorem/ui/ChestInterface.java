package squeeze.theorem.ui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import squeeze.theorem.bank.UserInterfaceBank;
import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.data.SessionData;
import squeeze.theorem.recipe.RecipeType;
import squeeze.theorem.ui.settings.UserInterfaceSettings;
import squeeze.theorem.util.SqueezeUtil;

public abstract class ChestInterface {

	/*Static fields*/
	
	public static List<ChestInterface> chestInterfaces = new ArrayList<ChestInterface>();
	
	public static UserInterfaceSettings settings = new UserInterfaceSettings("Settings");
	public static UserInterfaceQuests quests = new UserInterfaceQuests("Quests");
	
	public static InterfaceSkills skills = new InterfaceSkills("Skills");
	public static ChestInterface anvil = new UserInterfaceGenericCrafting("Anvil", RecipeType.SMITHING_ANVIL);
	public static ChestInterface furnace = new UserInterfaceGenericCrafting("Furnace", RecipeType.SMITHING_FURNACE);
	public static ChestInterface fire = new UserInterfaceGenericCrafting("Fire", RecipeType.COOKING_FIRE);
	public static ChestInterface craftingTable = new UserInterfaceGenericCrafting("Crafting Table", RecipeType.CRAFTING_TABLE);

	public static ChestInterface skillguideCooking = new UserInterfaceSkillguideCooking("Cooking");
	public static ChestInterface skillguideDefense = new UserInterfaceSkillguideDefense("Defense");
	public static ChestInterface skillguideFiremaking = new UserInterfaceSkillguideFiremaking("Firemaking");
	public static ChestInterface skillguideFishing = new UserInterfaceSkillguideFishing("Fishing");
	public static ChestInterface skillguideHitpoints = new UserInterfaceSkillguideHitpoints("Hitpoints");
	public static ChestInterface skillguideMining = new UserInterfaceSkillguideMining("Mining");
	public static ChestInterface skillguideRanged = new UserInterfaceSkillguideRanged("Ranged");
	public static ChestInterface skillguideSmithing = new UserInterfaceSkillguideSmithing("Smithing");
	public static ChestInterface skillguideStrength = new UserInterfaceSkillguideStrength("Strength");
	public static ChestInterface skillguideWitchcraft = new UserInterfaceSkillguideWitchcraft("Witchcraft");
	public static ChestInterface skillguideWoodcutting = new UserInterfaceSkillguideWoodcutting("Woodcutting");
	public static ChestInterface skillguideLarceny = new UserInterfaceSkillguideLarceny("Larceny");
	public static ChestInterface skillguideConjuration = new UserInterfaceSkillguideConjuration("Conjuration");
	public static ChestInterface bank = new UserInterfaceBank();
	
	/*Fields*/
	protected ArrayList<UIComponent> components = new ArrayList<UIComponent>();
	private String title;
	
	/*Constructors*/
	public ChestInterface(String title) {
		chestInterfaces.add(this);
		this.title = title;
	}

	/*Setters and getters*/
	
	public String getTitle(Player player) {
		return title;
	}

	public void addComponent(UIComponent comp) {
		this.components.add(comp);
	}

	public Inventory getInventory(Player player) {
		
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
		SessionData sdat = dat.getSessionData();
		
		Inventory inv;
			
		if(components.size() > 45) {
			inv = Bukkit.createInventory(null, 54, ChatColor.DARK_PURPLE + getTitle(player));
			inv.setItem(45,  SqueezeUtil.generateItem(Material.FIRE_CORAL_BLOCK, 1, ChatColor.RED + "Close Menu"));
			inv.setItem(52, SqueezeUtil.generateItem(Material.RED_WOOL, 1, ChatColor.RED + "Previous", ChatColor.RED + "<-----"));
			inv.setItem(53, SqueezeUtil.generateItem(Material.LIME_WOOL, 1, ChatColor.GREEN + "Next", ChatColor.GREEN + "----->"));
		} else {
			inv = Bukkit.createInventory(null, getPageSize(), ChatColor.DARK_PURPLE + getTitle(player));
		}
		
		int minIndex = sdat.getUIpage() * getPageSize();
		int maxIndex = minIndex + getPageSize() - 1;
		
		for(int i = minIndex; i <= maxIndex; i++) {
			UIComponent comp = getComponent(i);
			if(comp != null) {
				inv.addItem(comp.getItemStack(player));
			}
		}
		
		return inv;
		
	}
	
	public int getPageSize() {
		
		if(components.size() > 45) return 45;
		
		return components.size() / 9 * 9 + 9;
	}
	
	public void open(Player player, int page) {
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player);
		SessionData sdat = dat.getSessionData();
		sdat.setChestInterface(this);
		sdat.setUIpage(page);
		Inventory inv = getInventory(player);
		sdat.setInterfaceInventory(inv);
		player.openInventory(inv);
	}
	
	
	public void open(Player player) {
		open(player, 0);
	}
	
	public void refresh(Player player) {
		DataManager dataManager = DataManager.getInstance();
		SessionData sdat = dataManager.getPlayerData(player).getSessionData();
		Inventory inv = player.getOpenInventory().getTopInventory();
		if(inv != sdat.getInterfaceInventory()) return;
		inv.setContents(getInventory(player).getContents());
	}
	
	public List<UIComponent> getComponents(){
		return this.components;
	}
	
	public UIComponent getComponent(int index) {
		if(index < getComponents().size()) {
			return getComponents().get(index);
		} else {
			return null;
		}
	}

	public int getPageCount() {
		if(components.size() == 0) return 1;
		return (int) Math.ceil(components.size() / 45);
	}
	
	public static List<ChestInterface> getChestInterfaces() {
		return chestInterfaces;
	}
	
	public String getTitle() {
		return this.title;
	}
	
}

