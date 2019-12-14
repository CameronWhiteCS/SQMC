package squeeze.theorem.skill;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.skill.firemaking.SkillFiremaking;
import squeeze.theorem.skill.mining.SkillMining;
import squeeze.theorem.skill.witchcraft.SkillWitchcraft;
import squeeze.theorem.skill.woodcutting.SkillWoodcutting;
import squeeze.theorem.ui.ChestInterface;

public abstract class Skill {

	/* Static fields */

	private static ArrayList<Skill> skills = new ArrayList<Skill>();
	public static SkillMining mining = new SkillMining();
	public static SkillSmithing smithing = new SkillSmithing();
	public static SkillWoodcutting woodcutting = new SkillWoodcutting();
	public static SkillFiremaking firemaking = new SkillFiremaking();
	public static SkillFishing fishing = new SkillFishing();
	public static SkillCooking cooking = new SkillCooking();
	public static SkillStrength strength = new SkillStrength();
	public static SkillRanged ranged = new SkillRanged();
	public static SkillWitchcraft witchcraft = new SkillWitchcraft();
	public static SkillDefense defense = new SkillDefense();
	public static SkillHitpoints hitpoints = new SkillHitpoints();
	public static SkillLarceny larceny = new SkillLarceny();
	public static SkillConjuration conjuration = new SkillConjuration();
	
	public Skill() {
		skills.add(this);
	}
	

	/* Fields */

	private Material material;
	private String name = "";

	/* Setters and getters */
	public void setMaterial(Material material) {
		this.material = material;
	}

	public Material getMaterial() {
		return material;
	}

	public static ArrayList<Skill> getSkills() {
		return skills;
	}

	public ItemStack getDisplayIcon(UUID id) {

		ItemStack output = new ItemStack(getMaterial());
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(id);
		output.setAmount(1);

		ItemMeta meta = output.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.setDisplayName(ChatColor.GOLD + getProperName());
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "-------");
		
		DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
		df.setMaximumFractionDigits(340); // 340 = DecimalFormat.DOUBLE_FRACTION_DIGITS

		
		
		lore.add(ChatColor.GREEN + "Level: " + df.format(dat.getLevel(this)));
		lore.add(ChatColor.GRAY + "-------");
		lore.add(ChatColor.DARK_PURPLE + "XP: " + df.format(dat.getXP(this)));
		meta.setLore(lore);

		output.setItemMeta(meta);

		return output;
	}

	public String getProperName() {

		return getName().substring(0, 1).toUpperCase() + getName().substring(1, getName().length()).toLowerCase();

	}

	// Intended to be overwritten
	public abstract ChestInterface getSkillGuide(Player player);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void onLevelUp(int initial, int fnl) {
		
	}
	
}
