package squeeze.theorem.recipe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.JSONArray;
import org.json.JSONObject;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.data.SessionData;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.main.SQMC;
import squeeze.theorem.skill.LevelRequirements;
import squeeze.theorem.skill.Skill;
import squeeze.theorem.skill.cooking.RecipeCookedCod;
import squeeze.theorem.skill.cooking.RecipeCookedPufferfish;
import squeeze.theorem.skill.cooking.RecipeCookedSalmon;
import squeeze.theorem.skill.cooking.RecipeCookedSquid;
import squeeze.theorem.skill.cooking.RecipeCookedTropicalFish;
import squeeze.theorem.ui.ChestInterface;
import squeeze.theorem.ui.UIComponent;

public class SQMCRecipe implements Listener, LevelRequirements, UIComponent  {

	/* Static fields */

	// The following ArrayList must be initialized prior to any instance of
	// CustomRecipe
	private static ArrayList<SQMCRecipe> recipes = new ArrayList<SQMCRecipe>();
	
	private static HashMap<CustomItem, ArrayList<CustomItem>> substitutes = new HashMap<CustomItem, ArrayList<CustomItem>>(){
		private static final long serialVersionUID = 1742301123063001029L;

	{
		put(CustomItem.OAK_PLANKS, new ArrayList<CustomItem>() {
			private static final long serialVersionUID = -3000508525723108403L;
		{
			add(CustomItem.BIRCH_PLANKS);
			add(CustomItem.SPRUCE_PLANKS);
			add(CustomItem.JUNGLE_PLANKS);
			add(CustomItem.ACACIA_PLANKS);
			add(CustomItem.DARK_OAK_PLANKS);
		}});
		
		put(CustomItem.OAK_LOG, new ArrayList<CustomItem>() {
			private static final long serialVersionUID = -3000508525723108403L;
		{
			add(CustomItem.BIRCH_LOG);
			add(CustomItem.SPRUCE_LOG);
			add(CustomItem.JUNGLE_LOG);
			add(CustomItem.ACACIA_LOG);
			add(CustomItem.DARK_OAK_LOG);
		}});
		
	}};
	
	public static final SQMCRecipe WOODEN_SHOVEL = fromFile("smithing/wooden-shovel");
	public static final SQMCRecipe WOODEN_HOE = fromFile("smithing/wooden-hoe");
	public static final SQMCRecipe WOODEN_SWORD = fromFile("smithing/wooden-sword");
	public static final SQMCRecipe WOODEN_AXE = fromFile("smithing/wooden-axe");
	public static final SQMCRecipe WOODEN_PICKAXE = fromFile("smithing/wooden-pickaxe");
	public static final SQMCRecipe LEATHER_BOOTS = fromFile("smithing/leather-boots");
	public static final SQMCRecipe LEATHER_HELMET = fromFile("smithing/leather-helmet");
	public static final SQMCRecipe LEATHER_LEGGINGS = fromFile("smithing/leather-leggings");
	public static final SQMCRecipe LEATHER_CHESTPLATE = fromFile("smithing/leather-chestplate");
	
	public static final SQMCRecipe COBBLESTONE_SHOVEL = fromFile("smithing/cobblestone-shovel");
	public static final SQMCRecipe COBBLESTONE_HOE = fromFile("smithing/cobblestone-hoe");
	public static final SQMCRecipe COBBLESTONE_SWORD = fromFile("smithing/cobblestone-sword");
	public static final SQMCRecipe COBBLESTONE_AXE = fromFile("smithing/cobblestone-axe");
	public static final SQMCRecipe COBBLESTONE_PICKAXE = fromFile("smithing/cobblestone-pickaxe");
	public static final SQMCRecipe HARD_LEATHER_BOOTS = fromFile("smithing/hard-leather-boots");
	public static final SQMCRecipe HARD_LEATHER_HELMET = fromFile("smithing/hard-leather-helmet");
	public static final SQMCRecipe HARD_LEATHER_LEGGINGS = fromFile("smithing/hard-leather-leggings");
	public static final SQMCRecipe HARD_LEATHER_CHESTPLATE = fromFile("smithing/hard-leather-chestplate");
	
	public static final SQMCRecipe STONE_SHOVEL = fromFile("smithing/stone-shovel");
	public static final SQMCRecipe STONE_HOE = fromFile("smithing/stone-hoe");
	public static final SQMCRecipe STONE_SWORD = fromFile("smithing/stone-sword");
	public static final SQMCRecipe STONE_AXE = fromFile("smithing/stone-axe");
	public static final SQMCRecipe STONE_PICKAXE = fromFile("smithing/stone-pickaxe");
	public static final SQMCRecipe CHAINMAIL_BOOTS = fromFile("smithing/chainmail-boots");
	public static final SQMCRecipe CHAINMAIL_HELMET = fromFile("smithing/chainmail-helmet");
	public static final SQMCRecipe CHAINMAIL_LEGGINGS = fromFile("smithing/chainmail-leggings");
	public static final SQMCRecipe CHAINMAIL_CHESTPLATE = fromFile("smithing/chainmail-chestplate");
	
	public static final SQMCRecipe IRON_SHOVEL = fromFile("smithing/iron-shovel");
	public static final SQMCRecipe IRON_HOE = fromFile("smithing/iron-hoe");
	public static final SQMCRecipe IRON_SWORD = fromFile("smithing/iron-sword");
	public static final SQMCRecipe IRON_AXE = fromFile("smithing/iron-axe");
	public static final SQMCRecipe IRON_PICKAXE = fromFile("smithing/iron-pickaxe");
	public static final SQMCRecipe IRON_BOOTS = fromFile("smithing/iron-boots");
	public static final SQMCRecipe IRON_HELMET = fromFile("smithing/iron-helmet");
	public static final SQMCRecipe IRON_LEGGINGS = fromFile("smithing/iron-leggings");
	public static final SQMCRecipe IRON_CHESTPLATE = fromFile("smithing/iron-chestplate");
	
	public static final SQMCRecipe STEEL_SHOVEL = fromFile("smithing/steel-shovel");
	public static final SQMCRecipe STEEL_HOE = fromFile("smithing/steel-hoe");
	public static final SQMCRecipe STEEL_SWORD = fromFile("smithing/steel-sword");
	public static final SQMCRecipe STEEL_AXE = fromFile("smithing/steel-axe");
	public static final SQMCRecipe STEEL_PICKAXE = fromFile("smithing/steel-pickaxe");
	public static final SQMCRecipe STEEL_BOOTS = fromFile("smithing/steel-boots");
	public static final SQMCRecipe STEEL_HELMET = fromFile("smithing/steel-helmet");
	public static final SQMCRecipe STEEL_LEGGINGS = fromFile("smithing/steel-leggings");
	public static final SQMCRecipe STEEL_CHESTPLATE = fromFile("smithing/steel-chestplate");
	
	public static final SQMCRecipe DIAMOND_SHOVEL = fromFile("smithing/diamond-shovel");
	public static final SQMCRecipe DIAMOND_HOE = fromFile("smithing/diamond-hoe");
	public static final SQMCRecipe DIAMOND_SWORD = fromFile("smithing/diamond-sword");
	public static final SQMCRecipe DIAMOND_AXE = fromFile("smithing/diamond-axe");
	public static final SQMCRecipe DIAMOND_PICKAXE = fromFile("smithing/diamond-pickaxe");
	public static final SQMCRecipe DIAMOND_BOOTS = fromFile("smithing/diamond-boots");
	public static final SQMCRecipe DIAMOND_HELMET = fromFile("smithing/diamond-helmet");
	public static final SQMCRecipe DIAMOND_LEGGINGS = fromFile("smithing/diamond-leggings");
	public static final SQMCRecipe DIAMOND_CHESTPLATE = fromFile("smithing/diamond-chestplate");
	
	public static final SQMCRecipe IRIDESCENT_SHOVEL = fromFile("smithing/iridescent-shovel");
	public static final SQMCRecipe IRIDESCENT_HOE = fromFile("smithing/iridescent-hoe");
	public static final SQMCRecipe IRIDESCENT_SWORD = fromFile("smithing/iridescent-sword");
	public static final SQMCRecipe IRIDESCENT_AXE = fromFile("smithing/iridescent-axe");
	public static final SQMCRecipe IRIDESCENT_PICKAXE = fromFile("smithing/iridescent-pickaxe");
	public static final SQMCRecipe IRIDESCENT_BOOTS = fromFile("smithing/iridescent-boots");
	public static final SQMCRecipe IRIDESCENT_HELMET = fromFile("smithing/iridescent-helmet");
	public static final SQMCRecipe IRIDESCENT_LEGGINGS = fromFile("smithing/iridescent-leggings");
	public static final SQMCRecipe IRIDESCENT_CHESTPLATE = fromFile("smithing/iridescent-chestplate");
	
	public static final SQMCRecipe RADIANT_SHOVEL = fromFile("smithing/radiant-shovel");
	public static final SQMCRecipe RADIANT_HOE = fromFile("smithing/radiant-hoe");
	public static final SQMCRecipe RADIANT_SWORD = fromFile("smithing/radiant-sword");
	public static final SQMCRecipe RADIANT_AXE = fromFile("smithing/radiant-axe");
	public static final SQMCRecipe RADIANT_PICKAXE = fromFile("smithing/radiant-pickaxe");
	public static final SQMCRecipe RADIANT_BOOTS = fromFile("smithing/radiant-boots");
	public static final SQMCRecipe RADIANT_HELMET = fromFile("smithing/radiant-helmet");
	public static final SQMCRecipe RADIANT_LEGGINGS = fromFile("smithing/radiant-leggings");
	public static final SQMCRecipe RADIANT_CHESTPLATE = fromFile("smithing/radiant-chestplate");
	
	/*Smelting recipes*/
	public static SQMCRecipe STONE = fromFile("smithing/stone");
	public static SQMCRecipe IRON_NUGGET = fromFile("smithing/iron-nugget");
	public static SQMCRecipe IRON_INGOT = fromFile("smithing/iron-ingot");
	public static SQMCRecipe STEEL_INGOT = fromFile("smithing/steel-ingot");
	
	/*Crafting table recipes*/
	public static SQMCRecipe OAK_PLANKS = new SQMCRecipe()
			.setOutput(CustomItem.OAK_PLANKS)
			.addInput(CustomItem.OAK_LOG)
			.setRecipeType(RecipeType.CRAFTING_TABLE);
	
	public static SQMCRecipe BIRCH_PLANKS = new SQMCRecipe()
			.setOutput(CustomItem.BIRCH_PLANKS)
			.addInput(CustomItem.BIRCH_LOG)
			.setRecipeType(RecipeType.CRAFTING_TABLE);
	
	public static SQMCRecipe SPRUCE_PLANKS = new SQMCRecipe()
			.setOutput(CustomItem.SPRUCE_PLANKS)
			.addInput(CustomItem.SPRUCE_LOG)
			.setRecipeType(RecipeType.CRAFTING_TABLE);
	
	public static SQMCRecipe JUNGLE_PLANKS = new SQMCRecipe()
			.setOutput(CustomItem.JUNGLE_PLANKS)
			.addInput(CustomItem.JUNGLE_LOG)
			.setRecipeType(RecipeType.CRAFTING_TABLE);
	
	public static SQMCRecipe ACACIA_PLANKS = new SQMCRecipe()
			.setOutput(CustomItem.ACACIA_PLANKS)
			.addInput(CustomItem.ACACIA_LOG)
			.setRecipeType(RecipeType.CRAFTING_TABLE);
	
	public static SQMCRecipe DARK_OAK_PLANKS = new SQMCRecipe()
			.setOutput(CustomItem.DARK_OAK_PLANKS)
			.addInput(CustomItem.DARK_OAK_LOG)
			.setRecipeType(RecipeType.CRAFTING_TABLE);
	
	
	/*Cooking recipes*/
	public static RecipeCookedSalmon recipeCookedSalmon = new RecipeCookedSalmon();
	public static RecipeCookedCod recipeCookedCod = new RecipeCookedCod();
	public static RecipeCookedTropicalFish recipeCookedTropicalFish = new RecipeCookedTropicalFish();
	public static RecipeCookedPufferfish recipeCookedPufferfish = new RecipeCookedPufferfish();
	public static RecipeCookedSquid recipeCookedSquid = new RecipeCookedSquid();
	
	/* Fields */
	private CustomItem output;
	private int amount = 1;
	private Map<Skill, Integer> requirements = new LinkedHashMap<Skill, Integer>();
	private Map<Skill, Double> xp = new LinkedHashMap<Skill, Double>();
	private Map<CustomItem, Integer> inputs = new LinkedHashMap<CustomItem, Integer>();
	private RecipeType recipeType;
	private boolean acceptsSubstitutes = false;

	/* Constructors */

	public SQMCRecipe() {
		recipes.add(this);
	}

	/* Setters and getters */
	
	public static ArrayList<SQMCRecipe> getRecipes() {
		return recipes;
	}
	
	private void setInputs(Map<CustomItem, Integer> inputs) {
		this.inputs = inputs;
	}
	
	public SQMCRecipe setOutput(CustomItem ci) {
		this.output = ci;
		return this;
	}
	
	public SQMCRecipe setOutput(CustomItem ci, int amount) {
		this.output = ci;
		this.amount = amount;
		return this;
	}

	public CustomItem getOutput() {
		return this.output;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public SQMCRecipe addRequirement(Skill s, int lvl) {
		requirements.put(s, lvl);
		return this;
	}

	public Map<Skill, Integer> getRequirements() {
		return this.requirements;
	}
	
	private void setRequirements(Map<Skill, Integer> requirements) {
		this.requirements = requirements;
		
	}

	public SQMCRecipe setXP(Skill s, Double amount) {
		xp.put(s, amount);
		return this;
	}
	
	private void setXP(Map<Skill, Double> xp) {
		this.xp = xp;
	}

	public Map<Skill, Double> getXP() {
		return xp;
	}
	
	public double getXP(Skill s) {
		if(getXP().containsKey(s)) return getXP().get(s);
		return 0;
	}

	public SQMCRecipe addInput(CustomItem ci, int amount) {
		inputs.put(ci, amount);
		return this;
	}
	
	public SQMCRecipe addInput(CustomItem ci) {
		inputs.put(ci, 1);
		return this;
	}

	public Map<CustomItem, Integer> getInputs() {
		return this.inputs;
	}

	public SQMCRecipe setRecipeType(RecipeType type) {
		this.recipeType = type;
		return this;
	}

	public RecipeType getRecipeType() {
		return this.recipeType;
	}

	@Override
	public void onClick(InventoryClickEvent evt) {
		DataManager dataManager = DataManager.getInstance();
		Player player = (Player) evt.getWhoClicked();
		PlayerData playerData = dataManager.getPlayerData(player);
		SessionData sessionData = playerData.getSessionData();
		if(canCraft(player, true)) {
			sessionData.setRecipe(this);
			sessionData.setCraftingLocation(player.getLocation());
			player.sendMessage(ChatColor.GREEN + "You are now crafting " + getOutput().getName() + ChatColor.GREEN + ".");
			player.closeInventory();
		} else {
			player.closeInventory();
		}
		
	}
	
	@Override
	public ItemStack getItemStack(Player player) {
		ItemStack output = new ItemStack(getOutput().getItemStack().getType());
		
		for(Enchantment e: getOutput().getEnchantments().keySet()) {
			output.addUnsafeEnchantment(e, getOutput().getEnchantments().get(e));
		}
		
		ItemMeta meta = output.getItemMeta();
		
		meta.setDisplayName(ChatColor.GOLD + getOutput().getName());
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		
		ArrayList<String> lore = new ArrayList<String>();

		if(getLevelRequirementLore(player).size() > 0) {
			lore.add(ChatColor.GRAY + "-------");

			lore.addAll(getLevelRequirementLore(player));
		}

		lore.add(ChatColor.GRAY + "-------");

		for (CustomItem ci : getInputs().keySet()) {

			if (hasRequiredInput(player, ci)) {
				lore.add(ChatColor.GREEN + "" + getInputs().get(ci) + " " + ci.getName() + " \u2713");
			} else {
				lore.add(ChatColor.RED + "" + getInputs().get(ci) + " " + ci.getName() + " \u2715");
			}

		}

		if(this.getXP().keySet().size() > 0) {
			
			lore.add(ChatColor.GRAY + "-------");

			for (Skill s : getXP().keySet()) {
				lore.add(ChatColor.DARK_PURPLE + "" + getXP(s) + " " + s.getProperName() + " XP");
			}
			
		}
		
		meta.setLore(lore);
		output.setItemMeta(meta);
		return output;
	}
	
	public ItemStack getSkillGuideItemStack(Player player) {
		ItemStack output = new ItemStack(getOutput().getItemStack());
		
		for(Enchantment e: getOutput().getEnchantments().keySet()) {
			output.addUnsafeEnchantment(e, getOutput().getEnchantments().get(e));
		}
		
		ItemMeta meta = output.getItemMeta();
		
		meta.setDisplayName(ChatColor.GOLD + getOutput().getName());
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		
		ArrayList<String> lore = new ArrayList<String>();

		lore.add(ChatColor.GRAY + "-------");

		lore.addAll(getLevelRequirementLore(player));

		lore.add(ChatColor.GRAY + "-------");

		for (Skill s : getXP().keySet()) {
			lore.add(ChatColor.DARK_PURPLE + "" + getXP().get(s) + " " + s.getProperName() + " XP");
		}
		
		meta.setLore(lore);
		output.setItemMeta(meta);
		return output;
	}

	/* Craft a recipe when its recipe icon is clicked */
	
	public boolean canCraft(Player player, boolean notify) {	
		
		if (!meetsRequirements(player)) {
			if(notify) {
				sendInsufficientLevelNotice(player, "craft that");
				player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			}
			return false;
		}
		
		if (!hasRequiredItems(player)) {
			if(notify) {
				player.sendMessage(ChatColor.RED + "You don't have enough materials to craft that.");
				player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			}
			return false;
		}
		
		return true;
	}
		
	/*Open crafting table UI*/
	@EventHandler(priority=EventPriority.LOW)
	public void onInteract(PlayerInteractEvent evt) {
		if(evt.getClickedBlock() == null) return;
		if(evt.isCancelled()) return;
		Material m = evt.getClickedBlock().getType();
		if(m.equals(Material.CRAFTING_TABLE) && evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			evt.getPlayer().closeInventory();
			ChestInterface.craftingTable.open(evt.getPlayer());
			evt.setCancelled(true);
		}
		
	}
	
	//TODO: Optimize method
	public void craftItem(Player player) {
	
		for (CustomItem input : inputs.keySet()) {
	
			int amountToRemove = inputs.get(input);
			int removed = 0;
	
			while (amountToRemove > removed) {
				
				for(ItemStack i: player.getInventory().getContents()) {
					CustomItem ci = CustomItem.getCustomItem(i);
					if(ci == null) continue;
					
					//Non-substitute
					if(ci.equals(input)) {
						CustomItem.setCount(i, CustomItem.getCount(i) - 1);
						removed++;
						break;
					}
					
					//Remove substitutes
					if(substitutes.containsKey(input) && acceptsSubstitutes()) {
						if(substitutes.get(input).contains(ci)) {
							CustomItem.setCount(i, CustomItem.getCount(i) - 1);
							removed++;
							break;
						}
					}
				}
				
			
			}
	
		}
	
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
		dat.giveItem(getOutput().getItemStack(getAmount()));
		player.getWorld().playSound(player.getLocation(), getRecipeType().getSound(), getRecipeType().getVolume(), 0);
	
	}
	
	public boolean hasRequiredItems(Player player) {
		for (CustomItem input : inputs.keySet()) {
			if(!hasRequiredInput(player, input)) return false;
		}
		return true;
	}
	
	public boolean hasRequiredInput(Player player, CustomItem input) {
		
		int amountRequired = inputs.get(input);
		int totalAvailable = 0;
		
		//Add total without substitutes
		for(ItemStack i: player.getInventory().getContents()) {
			CustomItem ci = CustomItem.getCustomItem(i);
			if(ci == null) continue;
			if(ci.equals(input)) {
				totalAvailable += CustomItem.getCount(i);
			}
		}
		
		//Add substitute total
		if(substitutes.keySet().contains(input) && acceptsSubstitutes()) {
			for(CustomItem substitute: substitutes.get(input)) {
				for(ItemStack i: player.getInventory().getContents()) {
					CustomItem examined = CustomItem.getCustomItem(i);
					if(examined == null) continue;
					if(examined.equals(substitute)) totalAvailable += CustomItem.getCount(i);
				}
			}
		}
		
		if(amountRequired > totalAvailable) {
			return false;	
		} else {
			return true;	
		}
		
	}

	public boolean acceptsSubstitutes() {
		return acceptsSubstitutes;
	}

	public SQMCRecipe setAcceptsSubstitutes(boolean acceptsSubstitutes) {
		this.acceptsSubstitutes = acceptsSubstitutes;
		return this;
	}

	@Override
	public int getLevelRequired(Skill s) {
		return requirements.get(s);
	}

	public static SQMCRecipe fromJSON(JSONObject obj) {
		
		/*Initialize variables*/
		int outputID = 1;
		int outputAmount = 1;
		Map<CustomItem, Integer> inputs = new LinkedHashMap<CustomItem, Integer>();
		Map<Skill, Integer> requirements = new LinkedHashMap<Skill, Integer>();
		Map<Skill, Double> xp = new LinkedHashMap<Skill, Double>();
		RecipeType type = RecipeType.SMITHING_ANVIL;
		boolean acceptsSubstitutes = false;
		
		/*Assign values*/
		if(obj.has("output-id")) outputID = obj.getInt("output-id");
		if(obj.has("output-amount")) outputAmount = obj.getInt("output-amount");
		if(obj.has("accepts-substitutes")) acceptsSubstitutes = obj.getBoolean("accepts-substitutes");
		
		if(obj.has("inputs")) {
			JSONArray jsonInputs = obj.getJSONArray("inputs");
			for(Object o: jsonInputs) {
				JSONObject jsonInput = (JSONObject) o;
				CustomItem input = CustomItem.STONE;
				int inputAmount = 1;
				if(jsonInput.has("item-id")) input = CustomItem.getCustomItem(jsonInput.getInt("item-id"));
				if(jsonInput.has("amount")) inputAmount = jsonInput.getInt("amount");
				inputs.put(input, inputAmount);
			}
		}
		
		if(obj.has("recipe-type")) {
			String typeString = obj.getString("recipe-type");
			for(RecipeType t: RecipeType.values()) {
				if(t.toString().equalsIgnoreCase(typeString)) {
					type = t;
				}
			}
		}
		
		if(obj.has("xp")) {
			JSONObject jsonXP = obj.getJSONObject("xp");
			for(Skill s: Skill.getSkills()) {
				if(jsonXP.has(s.getName().toLowerCase())) {
					xp.put(s, jsonXP.getDouble(s.getName().toLowerCase()));
				}
			}
		}
		
		if(obj.has("requirements")) {
			JSONObject jsonRequirements = obj.getJSONObject("requirements");
			for(Skill s: Skill.getSkills()) {
				if(jsonRequirements.has(s.getName().toLowerCase())) {
					requirements.put(s, jsonRequirements.getInt(s.getName().toLowerCase()));
				}
			}
		}
		
		
		/*Create and return objects*/
		SQMCRecipe recipe = new SQMCRecipe();
		recipe.setAcceptsSubstitutes(acceptsSubstitutes);
		recipe.setOutput(CustomItem.getCustomItem(outputID), outputAmount);
		recipe.setInputs(inputs);
		recipe.setRecipeType(type);
		recipe.setXP(xp);
		recipe.setRequirements(requirements);
		
		return recipe;
		
	}
	
	public static SQMCRecipe fromFile(String name) {
		
		try {
			File f = new File(SQMC.getPlugin(SQMC.class).getDataFolder() + "/res/recipe/" + name + ".json");
			Scanner sc = new Scanner(f);
			String s = "";
			while(sc.hasNextLine()) {
				s += sc.nextLine();
			}
			sc.close();
			return fromJSON(new JSONObject(s));
		} catch(IOException exc) {
			exc.printStackTrace();
			return null;
		}
		
	}
	
}
