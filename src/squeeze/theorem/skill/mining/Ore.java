package squeeze.theorem.skill.mining;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import squeeze.theorem.item.CustomItem;
import squeeze.theorem.skill.LevelRequirements;
import squeeze.theorem.skill.Resource;
import squeeze.theorem.skill.Skill;
import squeeze.theorem.ui.UIComponent;

//TODO: Implement ore failure rates
public class Ore implements Resource, LevelRequirements, UIComponent {
	
	private static ArrayList<Ore> ores = new ArrayList<Ore>();
	public static ArrayList<Ore> getOres(){
		return ores;
	}
	
	public static Ore STONE = new Ore(Material.STONE, false, 1, 2, 100, 1, "Stone", CustomItem.COBBLESTONE.getItemStack(), 100L);
	public static Ore COAL_ORE = new Ore(Material.COAL_ORE, true, 2, 12.5,  100, 1, "Coal", CustomItem.COAL.getItemStack(), 45*20L);
	public static Ore IRON_ORE = new Ore(Material.IRON_ORE, true, 15, 35,  100, 2, "Iron", CustomItem.IRON_ORE.getItemStack(), 150*20L);
	public static Ore GRANITE = new Ore(Material.GRANITE, false, 23, 5, 100, 1, "Granite", CustomItem.GRANITE.getItemStack(), 30*20L);
	public static Ore LAPIS_ORE = new Ore(Material.LAPIS_ORE, true, 30, 150,  100, 2, "Lapis lazuli", CustomItem.LAPIS_LAZULI.getItemStack(), 300*20L);
	public static Ore DIORITE = new Ore(Material.DIORITE, false, 35, 35,  100, 1, "Diorite", CustomItem.DIORITE.getItemStack(), 60*20L);
	public static Ore GOLD_ORE = new Ore(Material.GOLD_ORE, true, 43, 100,  100, 3, "Gold", CustomItem.GOLD_ORE.getItemStack(), 900*20L);
	public static Ore ANDESITE = new Ore(Material.ANDESITE, false, 50, 6,  100, 1, "Andesite", CustomItem.ANDESITE.getItemStack(), 90*20L);	
	public static Ore REDSTONE_ORE = new Ore(Material.REDSTONE_ORE, true, 55, 200, 100, 3, "Redstone", CustomItem.REDSTONE.getItemStack(), 30*60*20L);
	public static Ore DIAMOND_ORE = new Ore(Material.DIAMOND_ORE, true, 65, 1000,  100, 3, "Diamond", CustomItem.DIAMOND.getItemStack(), 60*60*20L);
	public static Ore OBSIDIAN = new Ore(Material.OBSIDIAN, false, 75, 150,  100, 4, "Obsidian", CustomItem.OBSIDIAN.getItemStack(), 120*20L);
	public static Ore EMERALD_ORE = new Ore(Material.EMERALD_ORE, true, 80, 500, 100, 3, "Emerald", CustomItem.EMERALD.getItemStack(), 6*60*60*20L);
	public static Ore NEHTER_QUARTZ_ORE = new Ore(Material.NETHER_QUARTZ_ORE, true, 85, 150, 100, 1, "Quartz", CustomItem.QUARTZ.getItemStack(), 100L);
	
	private Material material;
	private double XP;
	private String name;
	private long respawnDelay;
	private ItemStack drop;
	private boolean vein;
	private double successRate;
	private int tier;
	private LinkedHashMap<Skill, Integer> requirements = new LinkedHashMap<Skill, Integer>();
	
	Ore(Material material, boolean vein, int levelRequired, double XP, double successRate, int tier, String name, ItemStack drop, long respawnDelay){
		setMaterial(material);
		addRequirement(Skill.mining, levelRequired);
		setXP(XP);
		setName(name);
		setRespawnDelay(respawnDelay);
		setDrop(drop);
		setVein(vein);
		setSuccessRate(successRate);
		setTier(tier);
		ores.add(this);
	}

	public void setXP(double xP) {
		XP = xP;
	}

	public double getXP() {
		return XP;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ItemStack getDrop() {
		return drop;
	}

	public void setDrop(ItemStack drop) {
		this.drop = drop;
	}

	public double getSuccessRate() {
		return successRate;
	}

	public void setSuccessRate(double successRate) {
		this.successRate = successRate;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public long getRespawnDelay() {
		return respawnDelay;
	}

	public void setRespawnDelay(long respawnDelay) {
		this.respawnDelay = respawnDelay;
	}

	public boolean isVein() {
		return vein;
	}

	public void setVein(boolean vein) {
		this.vein = vein;
	}

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	@Override
	public Map<Skill, Integer> getRequirements() {
		return requirements;
	}

	public void addRequirement(Skill s, int lvl) {
		requirements.put(s, lvl);
		
	}

	public static Ore getOreByMaterial(Material m) {
		for(Ore o: ores) {
			if(o.getMaterial() == m)
				return o;
		}
		return null;
	}
	
	@Override
	public int getLevelRequired(Skill s) {
		return requirements.get(s);
	}

	@Override
	public ItemStack getItemStack(Player player) {
		ItemStack stack = new ItemStack(this.material);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + this.name);
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "=======");
		lore.addAll(this.getLevelRequirementLore(player));
		lore.add(ChatColor.GRAY + "=======");
		lore.add(ChatColor.DARK_PURPLE + "Tier: " + this.tier);
		lore.add(ChatColor.DARK_PURPLE + "XP: " + this.XP);
		lore.add(ChatColor.DARK_PURPLE + "Success rate: " + this.successRate + "%");
		lore.add(ChatColor.DARK_PURPLE + "Respawn time: " + this.respawnDelay / 20 + "s");
		lore.add(ChatColor.DARK_PURPLE + "Vein: " + this.vein);
		meta.setLore(lore);
		stack.setItemMeta(meta);
		return stack;
	}

	@Override
	public void onClick(InventoryClickEvent evt) {

	}
	
}
