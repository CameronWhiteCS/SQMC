package squeeze.theorem.skill;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

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
import squeeze.theorem.ui.ChestInterface;

public class SkillWoodcutting extends Skill implements Listener {

	/* Fields */

	private Random RNG = new Random();
	
	/* Constructors */

	public SkillWoodcutting() {
		this.setMaterial(Material.STONE_AXE);
		setName("Woodcutting");
	}

	@Override
	public ChestInterface getSkillGuide(Player player) {
		return ChestInterface.skillguideWoodcutting;
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
						
	
					Map<Location, Material> blocks = getTree(evt.getBlock().getLocation(), t);
						
					//Call custom event
						
					SQMC.getPlugin(SQMC.class).getServer().getPluginManager().callEvent(new TreeChopEvent(player, t, blocks, (CustomItemAxe) axe));
					
					evt.setCancelled(true);
					
					}
				
			}
		
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onChop(TreeChopEvent evt) {
		if(!evt.isCancelled()) {
			
			Map<Location, Material> blocks = evt.getBlocks();
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

	/**
	 * Performs a breadth first search to build a tree from a given location and
	 * material set provided by the tree object.
	 * 
	 * @param loc - A location in the tree to be felled/rebuilt. Assumes location is part of a valid tree.
	 * @param tree - Tree object to use as a material set.
	 * @return
	 */
	public Map<Location, Material> getTree(Location loc, Tree tree) {

		HashMap<Location, Material> output = new HashMap<Location, Material>();
		output.put(loc, loc.getBlock().getType());
		
		Queue<Location> queue = new LinkedList<Location>();
		queue.add(loc);
		
		while(!queue.isEmpty()) {
			Location current = queue.remove();
			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {
					for(int k = -1; k <= 1; k++) {
						Location temp = new Location(current.getWorld(), current.getX() + i, current.getY() + j, current.getZ() + k);
						if(!output.containsKey(temp) && tree.getMaterials().contains(temp.getBlock().getType())) {
							output.put(temp, temp.getBlock().getType());
							queue.add(temp);
						}
					}
				}
			}
		}
		
		
		return output;

	}

	public void regrowTreeInFuture(Map<Location, Material> tree, Tree t) {

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SQMC.getPlugin(SQMC.class), new Runnable() {

			@Override
			public void run() {

				for (Location loc : tree.keySet()) {
					loc.getBlock().setType(tree.get(loc));
				}

			}

		}, t.getRespawnDelay());

	}

	public void destroyTree(Map<Location, Material> tree) {
		for (Location loc : tree.keySet()) {
			loc.getBlock().setType(Material.BARRIER);
		}
	}

}
