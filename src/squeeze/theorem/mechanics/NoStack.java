package squeeze.theorem.mechanics;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import squeeze.theorem.item.CustomItem;

public class NoStack implements Runnable, Listener {

	private static NoStack instance = new NoStack();
	
	private NoStack() {
		
	}
	
	@Override
	public void run() {
		for(Player player: Bukkit.getOnlinePlayers()) {
			noStack(player);
			infStack(player);
		}
	}
	
	
	/*NoStack methods*/
	private void noStack(Player player) {
		//List of overflow items
		List<ItemStack> toGive = new ArrayList<ItemStack>();
		
		//iterate through inventory and add overflow items to list
		for (int i = 0; i <= 40; i++) {
			ItemStack stack = player.getInventory().getItem(i);
			CustomItem ci = CustomItem.getCustomItem(stack);
			if (ci == null) continue;
			if(ci.isInfinitelyStackable()) continue;
			if (stack.getAmount() > ci.getMaxStackSize()) {
				
				int diff = stack.getAmount() - ci.getMaxStackSize();
				ItemStack clone = ci.getItemStack(diff);
				stack.setAmount(ci.getMaxStackSize());
				player.getInventory().setItem(i, stack);
				toGive.add(clone);
			}

		}

		//if overflow items were found, give them to the player one at a time
		if(toGive.isEmpty() == false) {
			for(ItemStack i: toGive) {
				giveItem(i, player);
			}
		}
			
		
	}
	
	private void giveItem(ItemStack stack, Player player) {
		
		for(int i = 0; i <= 35; i++) {
			
			ItemStack itr = player.getInventory().getItem(i);
			CustomItem ci = CustomItem.getCustomItem(itr);
			if(ci == null) {
				player.getInventory().setItem(i, stack);
				stack.setAmount(0);
			}
		}
		
		if(stack.getAmount() > 0) player.getWorld().dropItem(player.getLocation(), stack);
		
	}
	
	/*InfStack methods*/
	private void infStack(Player player) {
		Inventory inv = player.getInventory();
		for (int i = 0; i <= 40; i++) {
			ItemStack stack = inv.getItem(i);
			CustomItem ci = CustomItem.getCustomItem(stack);
			if(ci == null) continue;
			if(ci.isInfinitelyStackable() == false) continue;
			int stacks = getStacks(ci, inv);
			if(stacks <= 1 && stack.getAmount() < 2) continue;
			int count = getTotalCount(ci, inv);
			removeAll(ci, inv);
			inv.setItem(i, ci.getItemStack(count));
		}
	}

	private int getStacks(CustomItem customItem, Inventory inv) {
		int output = 0;
		for (int i = 0; i <= 40; i++) {
			CustomItem ci = CustomItem.getCustomItem(inv.getItem(i));
			if(ci == customItem) output++;
		}
		return output;
	}
	
	private int getTotalCount(CustomItem customItem, Inventory inv) {
		int output = 0;
		for (int i = 0; i <= 40; i++) {
			CustomItem ci = CustomItem.getCustomItem(inv.getItem(i));
			if(ci != customItem) continue;
			output += CustomItem.getCount(inv.getItem(i));
		}
		return output;
	}
	
	private void removeAll(CustomItem customItem, Inventory inv) {
		for(int i = 0; i <= 40; i++) {
			CustomItem ci = CustomItem.getCustomItem(inv.getItem(i));
			if(ci != customItem) continue;
			inv.setItem(i, new ItemStack(Material.AIR));
		}
	}

	/*Events*/
	@EventHandler
	private void onPickup(EntityPickupItemEvent evt) {
		if(evt.getEntity() instanceof Player == false) return;
		Player player = (Player) evt.getEntity();
		if(player.getInventory().firstEmpty() == -1) evt.setCancelled(true);
		
	}
	
	public static NoStack getInstance() {
		return instance;
	}

}
