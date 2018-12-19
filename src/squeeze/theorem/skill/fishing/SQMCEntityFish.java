package squeeze.theorem.skill.fishing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.entity.SQMCEntity;
import squeeze.theorem.item.DropTable;
import squeeze.theorem.item.Lootable;
import squeeze.theorem.skill.LevelRequirements;
import squeeze.theorem.skill.Skill;

public class SQMCEntityFish extends SQMCEntity implements Lootable, LevelRequirements {

	/*Static fields*/
	public static SQMCEntityFishingSalmon salmon = new SQMCEntityFishingSalmon();
	public static SQMCEntityFishingCod cod = new SQMCEntityFishingCod();
	public static SQMCEntityFishingTropicalFish tropicalFish = new SQMCEntityFishingTropicalFish();
	public static SQMCEntityFishingPufferfish pufferFish = new SQMCEntityFishingPufferfish();
	public static SQMCEntityFishingSquid squid = new SQMCEntityFishingSquid();
	public static SQMCEntityFishingTurtle turtle = new SQMCEntityFishingTurtle();
	public static SQMCEntityFishingSeaCow seaCow = new SQMCEntityFishingSeaCow();
	private static List<SQMCEntityFish> fish = new ArrayList<SQMCEntityFish>() {
		private static final long serialVersionUID = -9136756596120624239L;
			{
				add(salmon);
				add(cod);
				add(tropicalFish);
				add(pufferFish);
				add(squid);
				add(turtle);
				add(seaCow);
			}};
	
	/*Fields*/
	private int levelRequired;
	private double XP;
	private Material material;
	private DropTable dropTable = new DropTable();
	
	public SQMCEntityFish(String name, EntityType entityType) {
		super(name, entityType);
		
	}
	
	/*Setters and getters*/
	public Material getMaterial() {
		return material;
	}

	public SQMCEntityFish setMaterial(Material material) {
		this.material = material;
		return this;
	}

	public int getLevelRequired() {
		return levelRequired;
	}

	public SQMCEntityFish setLevelRequired(int levelRequired) {
		this.levelRequired = levelRequired;
		return this;
	}

	public double getXP() {
		return XP;
	}

	public SQMCEntityFish setXP(double XP) {
		this.XP = XP;
		return this;
	}
	
	public SQMCEntityFish setDropTable(DropTable table) {
		this.dropTable = table;
		return this;
	}
	
	public DropTable getDropTable() {
		return this.dropTable;
	}
	
	/*Methods*/
	public ItemStack getSkillGuideComponent(Player player) {
		PlayerData dat = DataManager.getPlayerData(player.getUniqueId());
		ItemStack output = new ItemStack(getMaterial());
		ItemMeta meta = output.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + getName());
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

		ArrayList<String> lore = new ArrayList<String>();

		lore.add(ChatColor.GRAY + "-------");

		if (dat.getLevel(Skill.fishing) >= getLevelRequired()) {
			lore.add(ChatColor.GREEN + "Level " + getLevelRequired() + " Fishing" + " \u2713");
		} else {
			lore.add(ChatColor.RED + "Level " + getLevelRequired() + " Fishing" + " \u2715");
		}

		lore.add(ChatColor.GRAY + "-------");
		
		lore.add(ChatColor.DARK_PURPLE + "XP: " + getXP());

		meta.setLore(lore);
		output.setItemMeta(meta);
		return output;
	}

	@Override
	public Map<Skill, Integer> getRequirements() {
		HashMap<Skill, Integer> output = new HashMap<Skill, Integer>();
		output.put(Skill.fishing, getLevelRequired());
		
		return output;
	}
	
	public static List<SQMCEntityFish> getFish(){
		return fish;
	}

	@Override
	public List<ItemStack> getLoot(Player player) {
		List<ItemStack> loot = new ArrayList<ItemStack>();
		loot.add(getDropTable().getDrop());
		return loot;
	}

}
