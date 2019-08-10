package squeeze.theorem.ui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import squeeze.theorem.bank.UserInterfaceBank;
import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.recipe.RecipeType;
import squeeze.theorem.ui.settings.UserInterfaceSettings;

public abstract class UserInterface {

	/* Static fields */
	public static List<UserInterface> userInterfaces = new ArrayList<UserInterface>();
	
	public static UserInterface settings = new UserInterfaceSettings();
	public static UserInterface quests = new UserInterfaceQuests("Quests", 45);
	
	public static UserInterface skills = new InterfaceSkills();
	public static UserInterface anvil = new UserInterfaceGenericCrafting("Anvil", 45, RecipeType.SMITHING_ANVIL);
	public static UserInterface furnace = new UserInterfaceGenericCrafting("Furnace", 27, RecipeType.SMITHING_FURNACE);
	public static UserInterface fire = new UserInterfaceGenericCrafting("Fire", 27, RecipeType.COOKING_FIRE);
	public static UserInterface craftingTable = new UserInterfaceGenericCrafting("Crafting Table", 27, RecipeType.CRAFTING_TABLE);

	public static UserInterface skillguideCooking = new UserInterfaceSkillguideCooking("Cooking", 27);
	public static UserInterface skillguideDefense = new UserInterfaceSkillguideDefense("Defense", 27);
	public static UserInterface skillguideFiremaking = new UserInterfaceSkillguideFiremaking("Firemaking", 27);
	public static UserInterface skillguideFishing = new UserInterfaceSkillguideFishing("Fishing", 27);
	public static UserInterface skillguideHitpoints = new UserInterfaceSkillguideHitpoints("Hitpoints", 27);
	public static UserInterface skillguideMining = new UserInterfaceSkillguideMining("Mining", 27);
	public static UserInterface skillguideRanged = new UserInterfaceSkillguideRanged("Ranged", 27);
	public static UserInterface skillguideSmithing = new UserInterfaceSkillguideSmithing("Smithing", 45);
	public static UserInterface skillguideStrength = new UserInterfaceSkillguideStrength("Strength", 27);
	public static UserInterface skillguideWitchcraft = new UserInterfaceSkillguideWitchcraft("Witchcraft", 27);
	public static UserInterface skillguideWoodcutting = new UserInterfaceSkillguideWoodcutting("Woodcutting", 27);
	public static UserInterface skillguideLarceny = new UserInterfaceSkillguideLarceny("Larceny", 27);
	public static UserInterface bank = new UserInterfaceBank();
	
	public UserInterface() {
		userInterfaces.add(this);
	}
	
	
	public String appendID() {
		return "                                                                                " + "ID:" + getID();
	}
	
	public void open(Player player) {
		Inventory inv = getInventory(player);
		player.openInventory(inv);
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
		dat.getSessionData().setUIpage(0);
	}
	public abstract Inventory getInventory(Player player);
	public abstract List<UIComponent> getComponents();
	public abstract String getTitle(Player player);

	public static List<UserInterface> getUserInterfaces() {
		return userInterfaces;
	}
	
	public int getID() {
		return userInterfaces.indexOf(this);
	}
	
	//TODO - visible contents NEVER equals expected contents -- why (array.length maybe)?
	public void refresh(Player player) {
		if (player.getOpenInventory().getTitle() == null) return;
		String title = player.getOpenInventory().getTopInventory().getTitle();
		if(title.contains("ID:" + getID()) == false) return;
		int ID = Integer.parseInt(title.split("ID:")[1]);
		if(ID != getID()) return;
		
		
		/*
		ItemStack[] visibleContents = player.getOpenInventory().getTopInventory().getContents();
		ItemStack[] expectedContents = getInventory(player).getContents();
		if (visibleContents.equals(expectedContents) == false) player.getOpenInventory().getTopInventory().setContents(expectedContents);
		*/
		player.getOpenInventory().getTopInventory().setContents(getInventory(player).getContents());
	}

}
