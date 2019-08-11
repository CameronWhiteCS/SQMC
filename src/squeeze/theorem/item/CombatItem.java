package squeeze.theorem.item;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.json.JSONArray;
import org.json.JSONObject;

import squeeze.theorem.combat.AttackStyle;
import squeeze.theorem.combat.CombatStats;
import squeeze.theorem.main.SQMC;
import squeeze.theorem.skill.LevelRequirements;
import squeeze.theorem.skill.Skill;

public class CombatItem extends CustomItem implements CombatStats, LevelRequirements, Runnable {

	/* Fields */
	private HashMap<AttackStyle, Double> strengths = new HashMap<AttackStyle, Double>();
	private HashMap<AttackStyle, Double> defenses = new HashMap<AttackStyle, Double>();
	private HashMap<AttackStyle, Double> accuracies = new HashMap<AttackStyle, Double>();
	private Map<Skill, Integer> requirements = new HashMap<Skill, Integer>();

	private double health;
	private boolean wand = false;
	private boolean twoHanded = false;
	private Color color = null;
	
	private List<Material> leathers = new ArrayList<Material>() {
		private static final long serialVersionUID = -219940061017358432L;
		{
			add(Material.LEATHER_HELMET);
			add(Material.LEATHER_CHESTPLATE);
			add(Material.LEATHER_LEGGINGS);
			add(Material.LEATHER_BOOTS);
		}
		
	}; 

	/* Constructors */
	public CombatItem(int ID, String name, Material material, String... desc) {
		super(ID, name, material, desc);
	}
	
	@Override
	public ItemStack getItemStack(int amount) {
		ItemStack output = super.getItemStack(amount);
		ItemMeta meta = output.getItemMeta();
		
		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier("generic.armor", 0.0, Operation.MULTIPLY_SCALAR_1));
		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier("generic.armorToughness", 0.0, Operation.MULTIPLY_SCALAR_1));
		output.setItemMeta(meta);
		
		if(leathers.contains(output.getType()) && getColor() != null) {
			LeatherArmorMeta lam = (LeatherArmorMeta) meta;
			lam.setColor(getColor());
			output.setItemMeta(lam);
		}
	
		return output;
		
	}

	/* Setters and getters */

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public boolean isWand() {
		return wand;
	}

	public void setWand(boolean wand) {
		this.wand = wand;
	}
	
	public boolean isTwoHanded() {
		return twoHanded;
	}

	public void setTwoHanded(boolean twoHanded) {
		this.twoHanded = twoHanded;
	}
	
	@Override
	public Map<Skill, Integer> getRequirements() {
		return this.requirements;
	}

	public CombatItem addRequirement(Skill s, int lvl) {
		requirements.put(s, lvl);
		return this;
	}

	@Override
	public int getLevelRequired(Skill s) {
		if(requirements.containsKey(s)) return requirements.get(s);
		return 0;
	}

	@Override
	public double getStrength(AttackStyle style) {
		if(strengths.containsKey(style)) return strengths.get(style);
		return 0.0;
	}

	@Override
	public double getDefense(AttackStyle style) {
		if(defenses.containsKey(style)) return defenses.get(style);
		return 0.0;
	}
	
	public CombatItem setStrength(AttackStyle style, Double amount) {
		strengths.put(style, amount);
		return this;
	}
	
	public CombatItem setDefense(AttackStyle style, Double amount) {
		defenses.put(style, amount);
		return this;
	}

	public CombatItem setAccuracy(AttackStyle style, Double amount) {
		accuracies.put(style, amount);
		return this;
	}
	
	@Override
	public double getAccuracy(AttackStyle style) {
		if(accuracies.containsKey(style)) return accuracies.get(style);
		return 0.0;
	}
	
	//Enforces level requirements on armor
	@Override
	public void run() {
		
	
		
		for(Player player: Bukkit.getOnlinePlayers()) {
			
			ArrayList<ItemStack> armor = new ArrayList<ItemStack>();
			for(ItemStack stack: player.getInventory().getArmorContents()) {
				armor.add(stack);
			}
			armor.add(player.getInventory().getItemInOffHand());
			
			for(ItemStack stack: armor) {
				
				CustomItem ci = CustomItem.getCustomItem(stack);
				if(ci == null) continue;
				if(ci instanceof CombatItem == false) continue;
				CombatItem combatItem = (CombatItem) ci;
				if(!combatItem.meetsRequirements(player)) {
					combatItem.sendInsufficientLevelNotice(player, "equip " + combatItem.getName());
					player.closeInventory();
					player.getWorld().dropItemNaturally(player.getLocation(), stack);
					stack.setAmount(0);
				}
				
			}
				
		}
		
	}


	@Override
	public double getHealth() {
		return this.health;
	}
	
	public CombatItem setHealth(double health) {
		this.health = health;
		return this;
	}

	public static CombatItem fromJSON(int ID, JSONObject obj) {
		
		/*Initialize the variables*/
		String name = "";
		Material material = Material.STONE;
		Map<Skill, Integer> requirements = new LinkedHashMap<Skill, Integer>();
		Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
		double health = 0;
		String[] lore = new String[1];
		double meleeStrength = 0, rangedStrength = 0, magicStrength = 0;
		double meleeAccuracy = 0, rangedAccuracy = 0, magicAccuracy = 0;
		double meleeDefense = 0, rangedDefense = 0, magicDefense = 0;
		boolean wand = false;
		boolean twoHanded = false;
		Color color = null;
		
		
		/*Assign values to the variables*/
		if(obj.has("melee-accuracy")) meleeAccuracy = obj.getDouble("melee-accuracy");
		if(obj.has("ranged-accuracy")) rangedAccuracy = obj.getDouble("ranged-accuracy");
		if(obj.has("magic-accuracy")) magicAccuracy = obj.getDouble("magic-accuracy");
		if(obj.has("melee-strength")) meleeStrength = obj.getDouble("melee-strength");
		if(obj.has("ranged-strength")) rangedStrength = obj.getDouble("ranged-strength");
		if(obj.has("magic-strength")) magicStrength = obj.getDouble("magic-strength");
		if(obj.has("melee-defense")) meleeDefense = obj.getDouble("melee-defense");
		if(obj.has("ranged-defense")) rangedDefense = obj.getDouble("ranged-defense");
		if(obj.has("magic-defense")) magicDefense = obj.getDouble("magic-defense");
		if(obj.has("health")) health = obj.getDouble("health");
		if(obj.has("name")) name = obj.getString("name");
		if(obj.has("wand")) wand = obj.getBoolean("wand");
		if(obj.has("two-handed")) twoHanded = (obj.getBoolean("two-handed"));
		
		if(obj.has("color")) {
			JSONArray colorArr = obj.getJSONArray("color");
			int r = colorArr.getInt(0);
			int g = colorArr.getInt(1);
			int b = colorArr.getInt(2);
			color = Color.fromRGB(r, g, b);
		}
		
		
		if(obj.has("material")){
			String materialString = obj.getString("material");
			for(Material m: Material.values()) {
				if(m.toString().equalsIgnoreCase(materialString)) {
					material = m;
					break;
				}
			}
		}
		
		if(obj.has("lore")) {
			JSONArray arr = obj.getJSONArray("lore");
			lore = new String[arr.length()];
			for(int i = 0; i < arr.length(); i++) {
				lore[i] = (String) arr.get(i);
			}
		}
		
		if(obj.has("requirements")) {
			JSONObject requirementsObject = obj.getJSONObject("requirements");
			for(Skill s: Skill.getSkills()) {
				if(requirementsObject.has(s.getName().toLowerCase())) {
					requirements.put(s, requirementsObject.getInt(s.getName().toLowerCase()));
				}
			}
		}
		
		if(obj.has("enchantments")) {
			JSONObject jsonEnchantments = obj.getJSONObject("enchantments");
			for(Enchantment e: Enchantment.values()) {
				String s = e.getKey().toString().replace("minecraft:", "").toUpperCase();
				if(jsonEnchantments.has(s)) {
					enchantments.put(e, jsonEnchantments.getInt(s));
				}
			}
		}
		
		/*Create and return the CustomItem*/
		CombatItem ci = new CombatItem(ID, name, material, lore);
	
		ci.setStrength(AttackStyle.MELEE, meleeStrength);
		ci.setStrength(AttackStyle.RANGED, rangedStrength);
		ci.setStrength(AttackStyle.MAGIC, magicStrength);
		ci.setDefense(AttackStyle.MELEE, meleeDefense);
		ci.setDefense(AttackStyle.RANGED, rangedDefense);
		ci.setDefense(AttackStyle.MAGIC, magicDefense);
		ci.setAccuracy(AttackStyle.MELEE, meleeAccuracy);
		ci.setAccuracy(AttackStyle.RANGED, rangedAccuracy);
		ci.setAccuracy(AttackStyle.MAGIC, magicAccuracy);
		ci.setHealth(health);
		ci.setWand(wand);
		ci.setRequirements(requirements);
		ci.setTwoHanded(twoHanded);
		ci.setEnchantments(enchantments);
		ci.setColor(color);
		
		
		return ci;
		
	}
	
	private void setRequirements(Map<Skill, Integer> requirements) {
		this.requirements = requirements;
	}

	public static CustomItem fromFileName(int ID, String fileName) {
		
		try {
			File file = new File(SQMC.getPlugin(SQMC.class).getDataFolder() + "/res/combatitem/" + fileName + ".json");
			Scanner sc = new Scanner(file);
			String s = "";
			while(sc.hasNextLine()) {
				s += sc.nextLine();
			}
			sc.close();
			JSONObject obj = new JSONObject(s);
			return fromJSON(ID, obj);
		} catch(IOException exc) {
			exc.printStackTrace();
			return null;
		}
		
	}

	public List<String> getCombatItemLore(){
		
		ArrayList<String> lore = new ArrayList<String>();
		
		if(getStrength(AttackStyle.MELEE) != 0) lore.add(ChatColor.RED + "Melee strength: " + ChatColor.GRAY + getStrength(AttackStyle.MELEE));
		if(getAccuracy(AttackStyle.MELEE) != 0) lore.add(ChatColor.RED + "Melee accuracy: " + ChatColor.GRAY + getAccuracy(AttackStyle.MELEE));
		if(getDefense(AttackStyle.MELEE) != 0) lore.add(ChatColor.RED + "Melee defense: " + ChatColor.GRAY + getDefense(AttackStyle.MELEE));
		
		if(getStrength(AttackStyle.RANGED) != 0) lore.add(ChatColor.GREEN + "Ranged strength: " + ChatColor.GRAY + getStrength(AttackStyle.RANGED));
		if(getAccuracy(AttackStyle.RANGED) != 0) lore.add(ChatColor.GREEN + "Ranged accuracy: " + ChatColor.GRAY + getAccuracy(AttackStyle.RANGED));
		if(getDefense(AttackStyle.RANGED) != 0) lore.add(ChatColor.GREEN + "Ranged defense: " + ChatColor.GRAY + getDefense(AttackStyle.RANGED));
		
		if(getStrength(AttackStyle.MAGIC) != 0) lore.add(ChatColor.DARK_PURPLE + "Magic strength: " + ChatColor.GRAY + getStrength(AttackStyle.MAGIC));
		if(getAccuracy(AttackStyle.MAGIC) != 0) lore.add(ChatColor.DARK_PURPLE + "Magic accuracy: " + ChatColor.GRAY + getAccuracy(AttackStyle.MAGIC));
		if(getDefense(AttackStyle.MAGIC) != 0) lore.add(ChatColor.DARK_PURPLE + "Magic defense: " + ChatColor.GRAY + getDefense(AttackStyle.MAGIC));

		if(getHealth() != 0) lore.add(ChatColor.RED + "HP: " + getHealth());
		
		return lore;
		
	}
	
}
