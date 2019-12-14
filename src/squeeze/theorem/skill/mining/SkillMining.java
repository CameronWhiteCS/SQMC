package squeeze.theorem.skill.mining;

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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.event.OreMineEvent;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.item.CustomItemPickaxe;
import squeeze.theorem.main.SQMC;
import squeeze.theorem.skill.Skill;
import squeeze.theorem.ui.ChestInterface;

public class SkillMining extends Skill implements Listener {

	private Random RNG = new Random();
	
	public SkillMining() {
		this.setMaterial(Material.STONE_PICKAXE);
		setName("Mining");
	}

	@Override
	public ChestInterface getSkillGuide(Player player) {
		return ChestInterface.skillguideMining;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onBreak(BlockBreakEvent evt) {
		Ore ore = Ore.getOreByMaterial(evt.getBlock().getType());
		if (evt.isCancelled() || evt.getPlayer().getGameMode() == GameMode.CREATIVE || ore == null) {
			return;
		}

		Player player = evt.getPlayer();

		// Pickaxe check
		CustomItem pickaxe = CustomItem.getCustomItem(player.getInventory().getItemInMainHand());
		if (pickaxe == null || pickaxe instanceof CustomItemPickaxe == false) {
			player.sendMessage(ChatColor.RED + "You need an pickaxe to mine ores!");
			evt.setCancelled(true);
			return;
		}

		// Tier check
		CustomItemPickaxe cip = (CustomItemPickaxe) pickaxe;
		if (cip.getTier() < ore.getTier()) {
			player.sendMessage(ChatColor.RED + "You'll need a higher tier pickaxe to harvest this.");
			evt.setCancelled(true);
			return;
		}

		// Level checks
		if (!ore.meetsRequirements(player)) {
			ore.sendInsufficientLevelNotice(player, "mine " + ore.getName());
			evt.setCancelled(true);
			return;
		}

		// Axe level check
		if (!cip.meetsRequirements(player)) {
			cip.sendInsufficientLevelNotice(player, "use " + cip.getName());
			evt.setCancelled(true);
			return;
		}

		Map<Location, Material> blocks = getVein(evt.getBlock().getLocation(), ore);

		SQMC.getPlugin(SQMC.class).getServer().getPluginManager()
				.callEvent(new OreMineEvent(player, ore, blocks, (CustomItemPickaxe) pickaxe));
		evt.setCancelled(true);
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onMine(OreMineEvent evt) {
		if(!evt.isCancelled()) {
			
			Map<Location, Material> blocks = evt.getBlocks();
			Ore ore = evt.getOre();
			Player player = evt.getPlayer();
			DataManager dataManager = DataManager.getInstance();
			PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
			CustomItemPickaxe pickaxe = evt.getPickaxe();
			ItemStack drop = new ItemStack(ore.getDrop());
			
			respawnVeinInFuture(blocks, ore);
			destroyVein(blocks);

			// Fail check (for xp and drop)
			if (didFail(dat, ore, (CustomItemPickaxe) pickaxe)) {
				player.sendMessage(
						ChatColor.RED + "You fail to properly cut the tree, damaging the logs in the process.");
				return;
			}

			//Luck check for double drop
			double luck = pickaxe.getLuck();
			double luckRoll = new Random().nextDouble() * 100;
			if(luck > luckRoll) {
				drop.setAmount(drop.getAmount() * 2);
				evt.getPlayer().sendMessage(ChatColor.GREEN + "You find yourself particularly lucky, and manage to harvest twice as many resources.");
			}
			
			// Award XP and items
			player.getInventory().addItem(drop);
			dat.awardXP(Skill.mining, evt.getXP());
			
		}
	}

	private boolean didFail(PlayerData dat, Ore o, CustomItemPickaxe pickaxe) {
		double successChance = o.getSuccessRate() + dat.getLevel(Skill.mining) * 0.1 + pickaxe.getSuccessRate();
		double roll = RNG.nextDouble() * 100;
		if (roll > successChance) {
			return true;
		}
		return false;
	}

	/**
	 * Performs a breadth first search to identify an ore vein given an input location and ore
	 * @param loc Any location in the ore vein
	 * @param ore The ore corresponding to the vein to be identified
	 * @return A map of locations and materials identifying the ore vein 
	 */
	public Map<Location, Material> getVein(Location loc, Ore ore) {
		HashMap<Location, Material> output = new HashMap<Location, Material>();
		output.put(loc, loc.getBlock().getType());
		Queue<Location> queue = new LinkedList<Location>();
		queue.add(loc);
		while(!queue.isEmpty() && queue.size() < 1000) {
			Location current = queue.remove();
			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {
					for(int k = -1; k <= 1; k++) {
						Location temp = new Location(current.getWorld(), current.getX() + i, current.getY() + j, current.getZ() + k);
						if(!output.containsKey(temp) && ore.getMaterial() == temp.getBlock().getType()) {
							output.put(temp, temp.getBlock().getType());
							queue.add(temp);
						}
					}
				}
			}
		}
		return output;
	}

	public void respawnVeinInFuture(Map<Location, Material> tree, Ore o) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SQMC.getPlugin(SQMC.class), new Runnable() {
			@Override
			public void run() {
				for (Location loc : tree.keySet()) {
					loc.getBlock().setType(tree.get(loc));
				}
			}
		}, o.getRespawnDelay());
	}

	public void destroyVein(Map<Location, Material> vein) {
		for (Location loc : vein.keySet()) {
			loc.getBlock().setType(Material.BARRIER);
		}
	}

}
