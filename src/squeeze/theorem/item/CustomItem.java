package squeeze.theorem.item;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import squeeze.theorem.combat.AttackStyle;

public class CustomItem implements Listener {

	/*Static fields*/
	private static ArrayList<CustomItem> items = new ArrayList<CustomItem>();
	
	/* Fields */

	private int ID;
	private String name;
	private Material material;
	private ArrayList<String> lore = new ArrayList<String>();
	private Map<Enchantment, Integer> enchantments = new LinkedHashMap<Enchantment, Integer>();
	private String[] description = {""};
	private int maxStackSize = 1;
	
	/*Woodcutting & Woodworking: Axes, Logs, Planks, & Woodworking*/
	
	//Level required, precision, tier, luck
	public static final CustomItem STARTER_BOOK = new CustomItemStarterBook(0, "Starter book", Material.WRITTEN_BOOK, "A book for newbies");
	public static final CustomItem WOODEN_AXE = new CustomItemAxe(1, "Wooden axe", Material.WOODEN_AXE, 1, 0.0, 0, "A crusty axe");
	public static final CustomItem COBBLESTONE_AXE = new CustomItemAxe(2, "Cobblestone axe", Material.STONE_AXE, 10, 5.0, 2.0, "");
	public static final CustomItem STONE_AXE = new CustomItemAxe(3, "Stone axe", Material.STONE_AXE, 20, 5.0, 2.0, "").addEnchantment(Enchantment.DIG_SPEED);
	public static final CustomItem IRON_AXE = new CustomItemAxe(4, "Iron axe", Material.IRON_AXE, 30, 7.5, 4.5, "");
	public static final CustomItem STEEL_AXE = new CustomItemAxe(5, "Steel axe", Material.IRON_AXE, 40, 7.5, 4.5, "").addEnchantment(Enchantment.ARROW_DAMAGE);
	public static final CustomItem DIAMOND_AXE = new CustomItemAxe(6, "Diamond axe", Material.DIAMOND_AXE, 50, 10, 7, "");
	public static final CustomItem IRIDESCENT_AXE = new CustomItemAxe(7, "Iridescent axe", Material.DIAMOND_AXE, 60, 10, 7, "").addEnchantment(Enchantment.ARROW_DAMAGE);
	public static final CustomItem RADIANT_AXE = new CustomItemAxe(8, "Radiant axe", Material.DIAMOND_AXE, 70, 10, 7, "").addEnchantment(Enchantment.ARROW_DAMAGE);
	
	public static final CustomItem OAK_LOG = new CustomItem(9, "Oak log", Material.OAK_LOG, "I should take this to a sawmill.");
	public static final CustomItem BIRCH_LOG = new CustomItem(10, "Birch log", Material.BIRCH_LOG, "I should take this to a sawmill.");
	public static final CustomItem SPRUCE_LOG = new CustomItem(11, "Spruce log", Material.SPRUCE_LOG, "I should take this to a sawmill.");
	public static final CustomItem JUNGLE_LOG = new CustomItem(12, "Jungle log", Material.JUNGLE_LOG, "I should take this to a sawmill.");
	public static final CustomItem ACACIA_LOG = new CustomItem(13, "Acacia log", Material.ACACIA_LOG, "I should take this to a sawmill.");
	public static final CustomItem DARK_OAK_LOG = new CustomItem(14, "Dark oak log", Material.DARK_OAK_LOG, "I should take this to a sawmill.");

	public static final CustomItem OAK_PLANKS = new CustomItem(15, "Oak planks", Material.OAK_PLANKS, "");
	public static final CustomItem BIRCH_PLANKS = new CustomItem(16, "Birch planks", Material.BIRCH_PLANKS, "");
	public static final CustomItem SPRUCE_PLANKS = new CustomItem(17, "Spruce planks", Material.SPRUCE_PLANKS, "");
	public static final CustomItem JUNGLE_PLANKS = new CustomItem(18, "Jungle planks", Material.JUNGLE_PLANKS, "");
	public static final CustomItem ACACIA_PLANKS = new CustomItem(19, "Acacia planks", Material.ACACIA_PLANKS, "");
	public static final CustomItem DARK_OAK_PLANKS = new CustomItem(20, "Dark oka planks", Material.DARK_OAK_PLANKS, "");
	
	public static final CustomItem STICK = new CustomItem(21, "Stick", Material.STICK, "");

	public static final CustomItem FLINT_AND_STEEL = new CustomItem(22, "Flint and steel", Material.FLINT_AND_STEEL, "");
	public static final CustomItem ASHES = new CustomItem(23, "Ashes", Material.SUGAR, "");
	
	/*Mining & Smithing: Pickaxes, Ores, & Smithing*/
	
	//Level required, precision, tier, luck
	public static final CustomItem WOODEN_PICKAXE = new CustomItemPickaxe(101, "Wooden pickaxe", Material.WOODEN_PICKAXE, 1, 0.0, 1, 0, "");
	public static final CustomItem COBBLESTONE_PICKAXE = new CustomItemPickaxe(102, "Cobblestone pickaxe", Material.STONE_PICKAXE, 10, 5.0, 2, 2, "");
	public static final CustomItem STONE_PICKAXE = new CustomItemPickaxe(103, "Stone pickaxe", Material.STONE_PICKAXE, 20, 5.0, 2, 2, "").addEnchantment(Enchantment.ARROW_FIRE);
	public static final CustomItem IRON_PICKAXE = new CustomItemPickaxe(104, "Iron pickaxe", Material.IRON_PICKAXE, 30, 7.5, 3, 4.5, "");
	public static final CustomItem STEEL_PICKAXE = new CustomItemPickaxe(105, "Steel pickaxe", Material.IRON_PICKAXE, 40, 10, 3, 5.5, "").addEnchantment(Enchantment.ARROW_DAMAGE);
	public static final CustomItem DIAMOND_PICKAXE = new CustomItemPickaxe(106, "Diamond pickaxe", Material.DIAMOND_PICKAXE, 50, 12.5, 4, 7, "");
	public static final CustomItem IRIDESCENT_PICKAXE = new CustomItemPickaxe(107, "Iridescent pickaxe", Material.DIAMOND_PICKAXE, 60, 10, 3, 7, "").addEnchantment(Enchantment.ARROW_DAMAGE);
	public static final CustomItem RADIANT_PICKAXE = new CustomItemPickaxe(108, "Radiant pickaxe", Material.DIAMOND_PICKAXE, 70, 10, 3, 7, "").addEnchantment(Enchantment.ARROW_DAMAGE);
	
	public static final CustomItem COBBLESTONE = new CustomItem(109, "Cobblestone", Material.COBBLESTONE, "");
	public static final CustomItem GRANITE = new CustomItem(110, "Granite", Material.GRANITE, "");
	public static final CustomItem ANDESITE = new CustomItem(111, "Andesite", Material.ANDESITE, "");
	public static final CustomItem DIORITE = new CustomItem(112, "Diorite", Material.DIORITE, "");
	public static final CustomItem COAL = new CustomItem(113, "Coal", Material.COAL, "Hmm, a non-renewable energy source!");
	public static final CustomItem IRON_ORE = new CustomItem(114, "Iron ore", Material.IRON_ORE, "");
	public static final CustomItem GOLD_ORE = new CustomItem(115, "Gold", Material.GOLD_ORE, "");
	public static final CustomItem LAPIS_LAZULI = new CustomItem(116, "Lapis lazuli", Material.LAPIS_LAZULI, "");
	public static final CustomItem REDSTONE = new CustomItem(117, "Redstone", Material.REDSTONE, "");
	public static final CustomItem EMERALD = new CustomItem(118, "Emerald", Material.EMERALD, "");
	public static final CustomItem DIAMOND = new CustomItem(119, "Diamond", Material.DIAMOND, "");
	public static final CustomItem QUARTZ = new CustomItem(120, "Quartz", Material.QUARTZ, "");
	public static final CustomItem OBSIDIAN = new CustomItem(121, "Obsidian", Material.OBSIDIAN, "");
	
	public static final CustomItem STONE = new CustomItem(122, "Stone", Material.STONE, "");
	
	public static final CustomItem IRON_NUGGET = new CustomItem(123, "Iron nugget", Material.IRON_NUGGET, "");
	public static final CustomItem IRON_INGOT = new CustomItem(124, "Iron ingot", Material.IRON_INGOT, "");
	public static final CustomItem STEEL_INGOT = new CustomItem(125, "Steel ingot", Material.IRON_INGOT, "").addEnchantment(Enchantment.LOOT_BONUS_BLOCKS);
	
	public static final CustomItem GOLD_NUGGET = new CustomItem(126, "Gold nugget", Material.GOLD_NUGGET, "");
	public static final CustomItem GOLD_INGOT = new CustomItem(127, "Gold ingot", Material.GOLD_INGOT, "");
	
	public static final CustomItem CALCIUM_CARBONATE = new CustomItem(128, "Calcium carbonate", Material.SUGAR, "");
	
	public static final CustomItem COW_HIDE = new CustomItem(129, "Cow hide", Material.LEATHER, "");
	public static final CustomItem LEATHER = new CustomItem(130, "Leather", Material.LEATHER, "Cruelty free!");
	public static final CustomItem IRIDESCENT_GEM = new CustomItem(131, "Iridescent gem", Material.DIAMOND, "Cruelty free!");
	public static final CustomItem RADIANT_GEM = new CustomItem(132, "Radiant gem", Material.DIAMOND, "Cruelty free!");
	
	/*Fishing: Fish, Cooked Fish, Fishing Rods, Bait*/
	
	public static final CustomItem FISHING_ROD = new CustomItem(201, "Fishing rod", Material.FISHING_ROD, "");
	
	public static final CustomItem SALMON = new CustomItem(211, "Salmon", Material.SALMON, "");
	public static final CustomItem COD = new CustomItem(212, "Cod", Material.COD, "");
	public static final CustomItem TROPICAL_FISH = new CustomItem(213, "Tropical fish", Material.TROPICAL_FISH, "");
	public static final CustomItem PUFFERFISH = new CustomItem(214, "Puffer fish", Material.PUFFERFISH, "");
	public static final CustomItem SQUID = new CustomItem(215, "Squid", Material.INK_SAC, "");
	public static final CustomItem TURTLE_EGG = new CustomItem(216, "Trutle egg", Material.TURTLE_EGG);
	public static final CustomItem SCUTE = new CustomItem(217, "Scute", Material.SCUTE, "Looks like turning off all those", "lights didn't help much.");
	
	public static final CustomItem FEATHER = new CustomItem(220, "Feather", Material.FEATHER, "Useful for fishing and making arrows.").setMaxStackSize(8);
	public static final CustomItem KELP = new CustomItem(221, "Kelp", Material.KELP, "Obtained while fishing");	
	
	//Heal amount
	public static final CustomItem COOKED_SALMON = new CustomItemFood(222, "Cooked salmon", Material.COOKED_SALMON, 2, "");
	public static final CustomItem COOKED_COD = new CustomItemFood(223, "Cooked cod", Material.COOKED_COD, 2, "");
	public static final CustomItem COOKED_TROPICAL_FISH = new CustomItemFood(224, "Cooked tropical fish", Material.TROPICAL_FISH, 2, "");
	public static final CustomItem COOKED_PUFFERFISH = new CustomItemFood(225, "Cooked puffer fish", Material.PUFFERFISH, 2, "");
	public static final CustomItem COOKED_SQUID = new CustomItemFood(226, "Cooked squid", Material.INK_SAC, 2, "");
	public static final CustomItem COOKED_TURTLE_EGG = new CustomItemFood(227, "Cooked turtle egg", Material.TURTLE_EGG, 2, "");
	
	/*Melee: Armor, Swords, Shields*/
	public static final CustomItem LEATHER_HELMET = CombatItem.fromFileName(301, "leather-helmet");
	public static final CustomItem LEATHER_CHESTPLATE = CombatItem.fromFileName(302, "leather-chestplate");
	public static final CustomItem LEATHER_LEGGINGS = CombatItem.fromFileName(303, "leather-leggings");
	public static final CustomItem LEATHER_BOOTS = CombatItem.fromFileName(304, "leather-boots");
	public static final CustomItem WOODEN_SWORD = CombatItem.fromFileName(305, "wooden-sword");
	
	public static final CustomItem HARD_LEATHER_HELMET = CombatItem.fromFileName(306, "hardleather-helmet");
	public static final CustomItem HARD_LEATHER_CHESTPLATE = CombatItem.fromFileName(307, "hardleather-chestplate");
	public static final CustomItem HARD_LEATHER_LEGGINGS = CombatItem.fromFileName(308, "hardleather-leggings");
	public static final CustomItem HARD_LEATHER_BOOTS = CombatItem.fromFileName(309, "hardleather-boots");
	public static final CustomItem COBBLESTONE_SWORD = CombatItem.fromFileName(310, "cobblestone-sword");
	
	public static final CustomItem CHAINMAIL_BOOTS = new CombatItem(311, "Chainmail boots", Material.CHAINMAIL_BOOTS, "");
	public static final CustomItem CHAINMAIL_HELMET = new CombatItem(312, "Chainmail helmet", Material.CHAINMAIL_HELMET, "");
	public static final CustomItem CHAINMAIL_LEGGINGS = new CombatItem(313, "Chainmail leggings", Material.CHAINMAIL_LEGGINGS, "");
	public static final CustomItem CHAINMAIL_CHESTPLATE = new CombatItem(314, "Chainmail chestplate", Material.CHAINMAIL_CHESTPLATE, "");
	public static final CustomItem STONE_SWORD = CombatItem.fromFileName(315, "stone-sword");
	
	public static final CustomItem IRON_BOOTS = new CombatItem(316, "Iron boots", Material.IRON_BOOTS, "");
	public static final CustomItem IRON_HELMET = new CombatItem(317, "Iron helmet", Material.IRON_HELMET, "");
	public static final CustomItem IRON_LEGGINGS = new CombatItem(318, "Iron leggings", Material.IRON_LEGGINGS, "");
	public static final CustomItem IRON_CHESTPLATE = new CombatItem(319, "Iron chestplate", Material.IRON_CHESTPLATE, "");
	public static final CustomItem IRON_SWORD = CombatItem.fromFileName(320, "iron-sword");
	
	public static final CustomItem STEEL_BOOTS = new CombatItem(321, "Steel boots", Material.IRON_BOOTS, "").addEnchantment(Enchantment.ARROW_FIRE);
	public static final CustomItem STEEL_HELMET = new CombatItem(322, "Steel helmet", Material.IRON_HELMET, "").addEnchantment(Enchantment.ARROW_FIRE);
	public static final CustomItem STEEL_LEGGINGS = new CombatItem(323, "Steel leggings", Material.IRON_LEGGINGS, "").addEnchantment(Enchantment.ARROW_FIRE);
	public static final CustomItem STEEL_CHESTPLATE = new CombatItem(324, "Steel chestplate", Material.IRON_CHESTPLATE, "").addEnchantment(Enchantment.ARROW_FIRE);
	public static final CustomItem STEEL_SWORD = CombatItem.fromFileName(325, "steel-sword");
	
	public static final CustomItem DIAMOND_BOOTS = new CombatItem(326, "Diamond boots", Material.DIAMOND_BOOTS, "");
	public static final CustomItem DIAMOND_HELMET = new CombatItem(327, "Diamond helmet", Material.DIAMOND_HELMET, "");
	public static final CustomItem DIAMOND_LEGGINGS = new CombatItem(328, "Diamond leggings", Material.DIAMOND_LEGGINGS, "");
	public static final CustomItem DIAMOND_CHESTPLATE = new CombatItem(329, "Diamond chestplate", Material.DIAMOND_CHESTPLATE, "");
	public static final CustomItem DIAMOND_SWORD = CombatItem.fromFileName(330, "diamond-sword");

	public static final CustomItem IRIDESCENT_BOOTS = new CombatItem(331, "Iridescent boots", Material.DIAMOND_BOOTS, "").addEnchantment(Enchantment.ARROW_FIRE);
	public static final CustomItem IRIDESCENT_HELMET = new CombatItem(332, "Iridescent helmet", Material.DIAMOND_HELMET, "").addEnchantment(Enchantment.ARROW_FIRE);
	public static final CustomItem IRIDESCENT_LEGGINGS = new CombatItem(333, "Iridescent leggings", Material.DIAMOND_LEGGINGS, "").addEnchantment(Enchantment.ARROW_FIRE);
	public static final CustomItem IRIDESCENT_CHESTPLATE = new CombatItem(334, "Iridescent chestplate", Material.DIAMOND_CHESTPLATE, "").addEnchantment(Enchantment.ARROW_FIRE);
	public static final CustomItem IRIDESCENT_SWORD = CombatItem.fromFileName(335, "iridescent-sword");
	
	public static final CustomItem RADIANT_BOOTS = new CombatItem(336, "Radiant boots", Material.DIAMOND_BOOTS, "").addEnchantment(Enchantment.ARROW_FIRE);
	public static final CustomItem RADIANT_HELMET = new CombatItem(337, "Radiant helmet", Material.DIAMOND_HELMET, "").addEnchantment(Enchantment.ARROW_FIRE);
	public static final CustomItem RADIANT_LEGGINGS = new CombatItem(338, "Radiant leggings", Material.DIAMOND_LEGGINGS, "").addEnchantment(Enchantment.ARROW_FIRE);
	public static final CustomItem RADIANT_CHESTPLATE = new CombatItem(339, "Radiant chestplate", Material.DIAMOND_CHESTPLATE, "").addEnchantment(Enchantment.ARROW_FIRE);
	public static final CustomItem RADIANT_SWORD = CombatItem.fromFileName(340, "radiant-sword");
	
	/*Farming: Shovels, Hoes, Seeds, & Produce*/
	
	public static final CustomItem WOODEN_HOE = new CustomItem(401, "Wooden hoe", Material.WOODEN_HOE, "");
	public static final CustomItem COBBLESTONE_HOE = new CustomItem(402, "Cobblestone hoe", Material.STONE_HOE, "");
	public static final CustomItem STONE_HOE = new CustomItem(403, "Stone hoe", Material.STONE_HOE, "").addEnchantment(Enchantment.ARROW_FIRE);
	public static final CustomItem IRON_HOE = new CustomItem(404, "Iron hoe", Material.IRON_HOE, "Error: Page not found");
	public static final CustomItem STEEL_HOE = new CustomItem(405, "Steel hoe", Material.IRON_HOE, "").addEnchantment(Enchantment.ARROW_DAMAGE);
	public static final CustomItem DIAMOND_HOE = new CustomItem(406, "Diamond hoe", Material.DIAMOND_HOE, "");
	public static final CustomItem IRIDESCENT_HOE = new CustomItem(407, "Iridescent hoe", Material.DIAMOND_HOE, "").addEnchantment(Enchantment.ARROW_DAMAGE);
	public static final CustomItem RADIANT_HOE = new CustomItem(408, "Radiant hoe", Material.DIAMOND_HOE, "").addEnchantment(Enchantment.ARROW_DAMAGE);
	
	public static final CustomItem WOODEN_SHOVEL = new CustomItem(409, "Wooden shovel", Material.WOODEN_SHOVEL, "");
	public static final CustomItem COBBLESTONE_SHOVEL = new CustomItem(410, "Cobblestone shovel", Material.STONE_SHOVEL, "");
	public static final CustomItem STONE_SHOVEL = new CustomItem(411, "Stone shovel", Material.STONE_SHOVEL, "").addEnchantment(Enchantment.ARROW_FIRE);
	public static final CustomItem IRON_SHOVEL = new CustomItem(412, "Iron shovel", Material.IRON_SHOVEL, "");
	public static final CustomItem STEEL_SHOVEL = new CustomItem(413, "Steel shovel", Material.IRON_SHOVEL, "").addEnchantment(Enchantment.ARROW_DAMAGE);
	public static final CustomItem DIAMOND_SHOVEL = new CustomItem(414, "Diamond shovel", Material.DIAMOND_SHOVEL, "");
	public static final CustomItem IRIDESCENT_SHOVEL = new CustomItem(415, "Iridescent shovel", Material.DIAMOND_SHOVEL, "").addEnchantment(Enchantment.ARROW_DAMAGE);
	public static final CustomItem RADIANT_SHOVEL = new CustomItem(416, "Radiant shovel", Material.DIAMOND_SHOVEL, "").addEnchantment(Enchantment.ARROW_DAMAGE);

	public static final CustomItem MELON = new CustomItem(417, "Melon", Material.MELON, "");
	public static final CustomItemFood MELON_SLICE = new CustomItemFood(418, "Melon", Material.MELON_SLICE, 5, "");
	public static final CustomItem DANDELION = new CustomItem(419, "Dandelion", Material.DANDELION, "");
	public static final CustomItem POPPY = new CustomItem(420, "Poppy", Material.POPPY, "");
	public static final CustomItem ALLIUM = new CustomItem(421, "Allium", Material.ALLIUM, "");
	public static final CustomItem BLUE_ORCHID = new CustomItem(422, "Blue orchid", Material.BLUE_ORCHID, "");
	
	/*Magic armor and weapons*/
	public static final CustomItem SPRUCE_WAND = new CombatItem(501, "Spruce wand", Material.STICK, "").setAccuracy(AttackStyle.MAGIC, 1.0).setStrength(AttackStyle.MAGIC, 1.0);
	public static final CustomItem BIRCH_WAND = new CombatItem(502, "Birch wand", Material.STICK, "").setAccuracy(AttackStyle.MAGIC, 1.0).setStrength(AttackStyle.MAGIC, 1.0);
	public static final CustomItem OAK_WAND = CombatItem.fromFileName(503, "oak-wand");
	public static final CustomItem JUNGLE_WAND = new CombatItem(504, "Jungle wand", Material.STICK, "").setAccuracy(AttackStyle.MAGIC, 1.0).setStrength(AttackStyle.MAGIC, 1.0);
	public static final CustomItem ACACIA_WAND = new CombatItem(505, "Acacia wand", Material.STICK, "").setAccuracy(AttackStyle.MAGIC, 1.0).setStrength(AttackStyle.MAGIC, 1.0);
	public static final CustomItem DARK_OAK_WAND = new CombatItem(506, "Dark oak wand", Material.STICK, "").setAccuracy(AttackStyle.MAGIC, 1.0).setStrength(AttackStyle.MAGIC, 1.0);
	//new wood type when it's released
	public static final CustomItem INFERNAL_WAND = new CombatItem(508, "Infernal wand", Material.BLAZE_ROD, "").setAccuracy(AttackStyle.MAGIC, 1.0).setStrength(AttackStyle.MAGIC, 1.0).addEnchantment(Enchantment.ARROW_FIRE);
	
	/*Quest items batch 1*/
	public static final CustomItem SUSPICIOUS_PACKAGE = new CustomItem(601, "Suspcious package", Material.SHULKER_BOX, "Seems perfectly legitimate");
	public static final CustomItemBook THE_COMMUNIST_MANIFESTO = new CustomItemBook(602, "The Communist Manifesto", Material.WRITTEN_BOOK, "A spectre is haunting Europe").setText(CustomItemBook.THE_COMMUNIST_MANIFESTO).setAuthor("Karl Marx");
	public static final CustomItem BRIGHTBRIAR_LETTER = new CustomItem(603, "Brightbriar letter", Material.PAPER, "I should take this to Brightbriar");
	public static final CustomItem LAYOFF_NOTICE = new CustomItem(603, "Layoff notice", Material.PAPER, "I have a bad feeling about", "delivering this...");
	
	/*Alchemy/Witchcraft*/
	public static final CustomItem SPIDER_EYE = new CustomItem(701, "Spider eye", Material.SPIDER_EYE, "");
	public static final CustomItem ROTTEN_FLESH = new CustomItem(702, "Rotten flesh", Material.ROTTEN_FLESH, "Could use some salt");
	
	/* Constructors */

	public CustomItem(int ID, String name, Material material, String... lore) {
		setID(ID);
		setName(name);
		setMaterial(material);
		String[] description = new String[lore.length];
		int counter = 0;
		for (String s : lore) {
			description[counter] = s;
			counter++;
			this.lore.add(s);
		}
		
		setDescription(description);
		items.add(this);
	}

	/* Setters and getters */

	public int getMaxStackSize() {
		return maxStackSize;
	}

	public CustomItem setMaxStackSize(int maxStackSize) {
		this.maxStackSize = maxStackSize;
		return this;
	}
	
	public String[] getDescription() {
		return description;
	}

	private void setDescription(String[] description) {
		this.description = description;
	}
	
	public int getID() {
		return ID;
	}

	public CustomItem setID(int iD) {
		ID = iD;
		return this;
	}

	public String getName() {
		return name;
	}

	public CustomItem setName(String name) {
		this.name = name;
		return this;
	}

	public Material getMaterial() {
		return material;
	}

	public CustomItem setMaterial(Material material) {
		this.material = material;
		return this;
	}

	public ArrayList<String> getLore() {
		ArrayList<String> output = new ArrayList<String>();
		//Header
		output.addAll(lore);
		
		if(this instanceof CombatItem) {
			CombatItem cmbt = (CombatItem) this;
			output.add(ChatColor.GRAY + "=======");
			output.addAll(cmbt.getCombatItemLore());
			output.add(ChatColor.GRAY + "=======");
		}
		output.add(ChatColor.GRAY + "ID: " + getID());
		return output;
	}

	public CustomItem setLore(ArrayList<String> lore) {
		this.lore = lore;
		return this;
	}
	
	public CustomItem setLore(String...lore) {
		ArrayList<String> arr = new ArrayList<String>();
		for(String s: lore) {
			arr.add(s);
		}
		return this;
	}
	

	public ItemStack getItemStack(int amount) {
		ItemStack stack = new ItemStack(getMaterial());
		ItemMeta meta = stack.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS);
		meta.setDisplayName(ChatColor.GOLD + getName());
		meta.setLore(getLore());
		meta.setUnbreakable(true);
		stack.setItemMeta(meta);
		stack.setAmount(amount);
		for(Enchantment e: enchantments.keySet()) {
			stack.addUnsafeEnchantment(e, enchantments.get(e));
		}

		return stack;
	}

	public ItemStack getItemStack() {
		return getItemStack(1);
	}

	/* Vanilla items to custom items and custom items to vanilla items */

	public static int getID(ItemStack stack) {
		try {
			List<String> lore = stack.getItemMeta().getLore();
			String s = ChatColor.stripColor(lore.get(lore.size() - 1));
			s = s.replaceAll("ID: ", "");

			return Integer.parseInt(s);
		} catch (Exception exc) {
			return -1;
		}
	}

	public static CustomItem getCustomItem(int ID) {
		for (CustomItem i : items) {
			if (i.getID() == ID)
				return i;
		}
		return null;
	}
	
	public static ArrayList<CustomItem> getItems(){
		return items;
	}

	public static CustomItem getCustomItem(ItemStack stack) {
		return getCustomItem(getID(stack));
	}

	@EventHandler
	public static void onInteract(PlayerInteractEvent evt) {
		
		if(evt.isCancelled() && evt.getAction() != Action.RIGHT_CLICK_AIR) return;
		
		if ((evt.getAction().equals(Action.RIGHT_CLICK_AIR) || evt.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
			CustomItem ci = CustomItem.getCustomItem(evt.getPlayer().getInventory().getItemInMainHand());
			if (ci != null) {

				ci.onRightClick(evt);

			}
		} 
	}
	
	//Intended to be overwritten
	public void onRightClick(PlayerInteractEvent evt) {

	}

	public Map<Enchantment, Integer> getEnchantments() {
		return enchantments;
	}
	
	public void setEnchantments(Map<Enchantment, Integer> enchantments) {
		this.enchantments = enchantments;
	}

	public CustomItem addEnchantment(Enchantment enchant, int lvl) {
		this.enchantments.put(enchant, lvl);
		return this;
	}
	
	public CustomItem addEnchantment(Enchantment enchant) {
		this.enchantments.put(enchant, 1);
		return this;
	}
	
}
