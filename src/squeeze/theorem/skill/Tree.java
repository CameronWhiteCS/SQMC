package squeeze.theorem.skill;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import squeeze.theorem.item.CustomItem;

public class Tree implements Resource {

	private static ArrayList<Tree> trees = new ArrayList<Tree>();
	
	public static final Tree SPRUCE = new Tree(1, 5, 100, 1, 300L, "Spruce", CustomItem.SPRUCE_LOG.getItemStack(), Material.SPRUCE_LOG, Material.SPRUCE_WOOD);
	public static final Tree BIRCH = new Tree(15, 11, 60, 1, 300L, "Birch", CustomItem.BIRCH_LOG.getItemStack(), Material.BIRCH_LOG, Material.BIRCH_WOOD);
	public static final Tree OAK = new Tree(30, 67.5, 30, 2, 300L, "Oak", CustomItem.OAK_LOG.getItemStack(), Material.OAK_LOG, Material.OAK_WOOD);
	public static final Tree JUNGLE = new Tree(45, 100, 5, 2, 300L, "Jungle", CustomItem.JUNGLE_LOG.getItemStack(), Material.JUNGLE_LOG, Material.JUNGLE_WOOD);
	public static final Tree ACACIA = new Tree(60, 175, -20, 3, 300L, "Acacia", CustomItem.ACACIA_LOG.getItemStack(), Material.ACACIA_LOG, Material.ACACIA_WOOD);
	public static final Tree DARK_OAK = new Tree(75, 250, -42, 3, 300L, "Dark oak", CustomItem.DARK_OAK_LOG.getItemStack(), Material.DARK_OAK_LOG, Material.DARK_OAK_WOOD);
	
	@SuppressWarnings("serial")
	ArrayList<Material> leaves = new ArrayList<Material>() {
		{
			add(Material.OAK_LEAVES);
			add(Material.BIRCH_LEAVES);
			add(Material.SPRUCE_LEAVES);
			add(Material.JUNGLE_LEAVES);
			add(Material.ACACIA_LEAVES);
			add(Material.DARK_OAK_LEAVES);
		}
	};
	
	private double xp;
	private String name;
	private long respawnDelay;
	private ItemStack drop;
	private ArrayList<Material> materials = new ArrayList<Material>();
	private double successRate;

	private LinkedHashMap<Skill, Integer> requirements = new LinkedHashMap<Skill, Integer>();

	public Tree(int levelRequired, double xp, double successRate, int tier, long respawnDelay, String name, ItemStack drop, Material... materials) {
		addRequirement(Skill.woodcutting, levelRequired);
		setXP(xp);
		setName(name);
		setRespawnDelay(respawnDelay);
		setDrop(drop);
		setSuccessRate(successRate);
		for (Material m : materials) {
			this.materials.add(m);
		}
		trees.add(this);
	}

	public double getXP() {
		return xp;
	}

	public void setXP(double xp) {
		this.xp = xp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getRespawnDelay() {
		return respawnDelay;
	}

	public void setRespawnDelay(long respawnDelay) {
		this.respawnDelay = respawnDelay;
	}

	public ItemStack getDrop() {
		return this.drop;
	}

	public void setDrop(ItemStack drop) {
		this.drop = drop;
	}

	public ArrayList<Material> getMaterials() {
		return materials;
	}

	public double getSuccessRate() {
		return successRate;
	}

	public void setSuccessRate(double successRate) {
		this.successRate = successRate;
	}

	@Override
	public Material getMaterial() {
		return getMaterials().get(0);
	}
	
	public static ArrayList<Tree> getTrees() {
		return trees;
	}
	
	public boolean isTree(BlockBreakEvent evt) {

		Location loc = evt.getBlock().getLocation();
		World world = loc.getWorld();
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();

		// Verify leaves

		for (int i = y; i <= 256; i++) {
			Material m = world.getBlockAt(new Location(world, x, i, z)).getType();
			if (leaves.contains(m)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public Map<Skill, Integer> getRequirements() {
		return requirements;
	}

	public void addRequirement(Skill s, int lvl) {
		requirements.put(s, lvl);
		
	}

	@Override
	public int getLevelRequired(Skill s) {
		return requirements.get(s);
	}

}