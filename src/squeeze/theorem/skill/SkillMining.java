package squeeze.theorem.skill;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

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
import squeeze.theorem.ui.ChestInterface;

public class SkillMining extends Skill implements Listener {

	/* Fields */

	private Random RNG = new Random();
	
	/* Constructors */

	public SkillMining() {
		this.setMaterial(Material.STONE_PICKAXE);
		setName("Mining");
	}

	@Override
	public ChestInterface getSkillGuide(Player player) {
		return ChestInterface.skillguideMining;
	}

	@EventHandler(priority=EventPriority.LOW)
	public void onBreak(BlockBreakEvent evt) {
		if (evt.isCancelled() || evt.getPlayer().getGameMode() == GameMode.CREATIVE) return; 
			for (Ore o : Ore.getOres()) {
				if (evt.getBlock().getType().equals(o.getMaterial())) {
		

						Player player = evt.getPlayer();

						// Pickaxe check
						String noPickaxe = ChatColor.RED + "You need an pickaxe to mine ores!";
						CustomItem pickaxe = CustomItem.getCustomItem(player.getInventory().getItemInMainHand());
						if (pickaxe == null) {
							player.sendMessage(noPickaxe);
							evt.setCancelled(true);
							return;
						}
						
						if (pickaxe instanceof CustomItemPickaxe == false) {
							player.sendMessage(noPickaxe);
							evt.setCancelled(true);
							return;
						}
						
						//Tier check
						if (pickaxe instanceof CustomItemPickaxe) {
							CustomItemPickaxe cip = (CustomItemPickaxe) pickaxe;
							
							if(cip.getTier() < o.getTier()) {
								player.sendMessage(ChatColor.RED + "You'll need a higher tier pickaxe to harvest this.");
								evt.setCancelled(true);
								return;
							}
							
						}

						// Level checks
						if (!o.meetsRequirements(player)){
							o.sendInsufficientLevelNotice(player, "mine " + o.getName());
							evt.setCancelled(true);
							return;
						}

						//Axe level check
						if (pickaxe instanceof CustomItemPickaxe) {
							CustomItemPickaxe cip = (CustomItemPickaxe) pickaxe;
							if (!cip.meetsRequirements(player)){
								cip.sendInsufficientLevelNotice(player, "use " + cip.getName());
								evt.setCancelled(true);
								return;
							}
						}

						//Calling of custom event
						
						ConcurrentHashMap<Location, Material> blocks = new ConcurrentHashMap<Location, Material>();
						blocks.put(evt.getBlock().getLocation(), evt.getBlock().getType());
						blocks = getVein(blocks, o);
						
						//Call custom event
						SQMC.getPlugin(SQMC.class).getServer().getPluginManager().callEvent(new OreMineEvent(player, o, blocks, (CustomItemPickaxe) pickaxe));
						evt.setCancelled(true);

					
				}
			}
		
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onMine(OreMineEvent evt) {
		if(!evt.isCancelled()) {
			
			ConcurrentHashMap<Location, Material> blocks = evt.getBlocks();
			Ore ore = evt.getOre();
			Player player = evt.getPlayer();
			DataManager dataManager = DataManager.getInstance();
			PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
			CustomItemPickaxe pickaxe = evt.getPickaxe();
			ItemStack drop = new ItemStack(ore.getDrop());
			
			respawnVeinInFuture(blocks, ore);
			destroyTree(blocks);

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

	/* Logic methods */

	private boolean didFail(PlayerData dat, Ore o, CustomItemPickaxe pickaxe) {

		double successChance = o.getSuccessRate() + dat.getLevel(Skill.mining) * 0.1 + pickaxe.getSuccessRate();

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
	public ConcurrentHashMap<Location, Material> getVein(ConcurrentHashMap<Location, Material> locs, Ore ore) {

		if(!ore.isVein())
			return locs;
		
		int size = locs.size();

		for (Location loc : locs.keySet()) {
			for (int x = -1; x <= 1; x++) {
				for (int y = -1; y <= 1; y++) {
					for (int z = -1; z <= 1; z++) {
						
						
						Location newLoc = new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z);
						Material oreMaterial = ore.getMaterial();
						Material material = newLoc.getBlock().getType();
						
						if (oreMaterial.equals(material)) {
							locs.put(newLoc, newLoc.getBlock().getType());
						}
						
					}
				}
			}
		}

		if (locs.size() == size) {
			return locs;
		} else {
			return getVein(locs, ore);
		}

	}

	public void respawnVeinInFuture(ConcurrentHashMap<Location, Material> tree, Ore o) {

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SQMC.getPlugin(SQMC.class), new Runnable() {

			@Override
			public void run() {

				for (Location loc : tree.keySet()) {
					loc.getBlock().setType(tree.get(loc));
				}

			}

		}, o.getRespawnDelay());

	}

	public void destroyTree(ConcurrentHashMap<Location, Material> tree) {
		for (Location loc : tree.keySet()) {
			loc.getBlock().setType(Material.BARRIER);
		}
	}

}
