package squeeze.theorem.skill;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.event.TreeChopEvent;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.item.CustomItemAxe;
import squeeze.theorem.main.SQMC;
import squeeze.theorem.ui.UserInterface;

public class SkillWoodcutting extends Skill implements Listener {

	/* Fields */

	private Random RNG = new Random();
	
	/* Constructors */

	public SkillWoodcutting() {
		this.setMaterial(Material.STONE_AXE);
		setName("Woodcutting");
	}

	@Override
	public UserInterface getSkillGuide(Player player) {
		return UserInterface.skillguideWoodcutting;
	}

	@EventHandler(priority=EventPriority.LOW)
	public void onBreak(BlockBreakEvent evt) {
		
		//Cancel if creative
		if(evt.isCancelled() || evt.getPlayer().getGameMode() == GameMode.CREATIVE) return;
		
		Block block = evt.getBlock();
		World world = block.getWorld();
		
		//Cancel if redstone block
		if(world.getBlockAt(block.getLocation().getBlockX(), 0, block.getLocation().getBlockZ()).getType() == Material.REDSTONE_BLOCK) return;
		
			for (Tree t : Tree.getTrees()) {
				if (!t.getMaterials().contains(evt.getBlock().getType())) continue;
				if (t.isTree(evt)) {

					Player player = evt.getPlayer();


					// Axe check
					String noAxe = ChatColor.RED + "You need an axe to cut trees!";
					CustomItem axe = CustomItem.getCustomItem(player.getInventory().getItemInMainHand());
					if (axe == null) {
						player.sendMessage(noAxe);
						evt.setCancelled(true);
						return;
					}
						
					if (axe instanceof CustomItemAxe == false) {
						player.sendMessage(noAxe);
						evt.setCancelled(true);
						return;
					}

					// Level checks
					if (!t.meetsRequirements(player)){
						t.sendInsufficientLevelNotice(player, "chop " + t.getName() + "s");
						evt.setCancelled(true);
						return;
					}

					//Axe level check
					if (axe instanceof CustomItemAxe) {
						CustomItemAxe cia = (CustomItemAxe) axe;
						if (!cia.meetsRequirements(player)){
							cia.sendInsufficientLevelNotice(player, "use " + cia.getName());
							evt.setCancelled(true);
							return;
						}
					}

					//Calling of custom event
						
					ConcurrentHashMap<Location, Material> blocks = new ConcurrentHashMap<Location, Material>();
					blocks.put(evt.getBlock().getLocation(), evt.getBlock().getType());
					blocks = getTree(blocks, t);
						
					//Call custom event
						
					SQMC.getPlugin(SQMC.class).getServer().getPluginManager().callEvent(new TreeChopEvent(player, t, blocks, (CustomItemAxe) axe));
					
					evt.setCancelled(true);
					
					}
				
			}
		
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onChop(TreeChopEvent evt) {
		if(!evt.isCancelled()) {
			
			ConcurrentHashMap<Location, Material> blocks = evt.getBlocks();
			Tree tree = evt.getTree();
			Player player = evt.getPlayer();
			DataManager dataManager = DataManager.getInstance();
			PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
			CustomItemAxe axe = evt.getAxe();
			ItemStack drop = new ItemStack(tree.getDrop());
			
			regrowTreeInFuture(blocks, tree);
			destroyTree(blocks);

			// Fail check (for xp and drop)
			if (didFail(dat, tree, (CustomItemAxe) axe)) {
				player.sendMessage(
						ChatColor.RED + "You fail to properly cut the tree, damaging the logs in the process.");
				return;
			}
			
			//Luck check for double drop
			double luck = axe.getLuck();
			double luckRoll = new Random().nextDouble() * 100;
			if(luck > luckRoll) {
				drop.setAmount(drop.getAmount() * 2);
				evt.getPlayer().sendMessage(ChatColor.GREEN + "You find yourself particularly lucky, and manage to harvest twice as many resources.");
			}
			

			// Award XP and items
			player.getInventory().addItem(drop);
			dat.awardXP(Skill.woodcutting, evt.getXP());
			
		}
	}

	/* Logic methods */

	private boolean didFail(PlayerData dat, Tree t, CustomItemAxe axe) {

		double successChance = t.getSuccessRate() + dat.getLevel(Skill.woodcutting) * 0.1 + axe.getSuccessRate();

		double roll = RNG.nextDouble() * 100;
		if (roll > successChance) {
			return true;
		}

		return false;
	}

	/*
	 * This is a rather complex recursive method. Basically, once a block being
	 * broken is determined to definitely be a tree, the block's location and its
	 * material is used to create a Map entry. That map entry, alongside the Log
	 * type being used for comparison, is then fed into this function, which, in
	 * turn, iterates over all its constituent entries to find nearby, unregistered
	 * tree blocks. The output is a HashMap<Location, Material> representing a tree
	 * prior to it being destroyed.
	 */
	public ConcurrentHashMap<Location, Material> getTree(ConcurrentHashMap<Location, Material> locs, Tree tree) {

		int size = locs.size();

		for (Location loc : locs.keySet()) {
			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					for (int z = -1; z <= 1; z++) {
						
						
						Location newLoc = new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
						ArrayList<Material> treeMaterials = tree.getMaterials();
						Material material = newLoc.getBlock().getType();
						
						if (treeMaterials.contains(material) && !locs.contains(newLoc)) {
							locs.put(newLoc, newLoc.getBlock().getType());
						}

					}
				}
			}
		}

		if (locs.size() == size) {
			return locs;
		} else {
			return getTree(locs, tree);
		}

	}

	public void regrowTreeInFuture(ConcurrentHashMap<Location, Material> tree, Tree t) {

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SQMC.getPlugin(SQMC.class), new Runnable() {

			@Override
			public void run() {

				for (Location loc : tree.keySet()) {
					loc.getBlock().setType(tree.get(loc));
				}

			}

		}, t.getRespawnDelay());

	}

	public void destroyTree(ConcurrentHashMap<Location, Material> tree) {
		for (Location loc : tree.keySet()) {
			loc.getBlock().setType(Material.BARRIER);
		}
	}

}
