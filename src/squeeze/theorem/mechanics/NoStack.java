package squeeze.theorem.mechanics;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import squeeze.theorem.item.CustomItem;

public class NoStack implements Runnable, Listener {

	@Override
	public void run() {

		for (Player player : Bukkit.getOnlinePlayers()) {

			//List of overflow items
			List<ItemStack> toGive = new ArrayList<ItemStack>();
			
			//iterate through inventory and add overflow items to list
			for (int i = 0; i <= 40; i++) {
				ItemStack stack = player.getInventory().getItem(i);
				CustomItem ci = CustomItem.getCustomItem(stack);
				if (ci == null) continue;
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

	}
	
	private static void giveItem(ItemStack stack, Player player) {
		
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

	@EventHandler
	private static void onPickup(EntityPickupItemEvent evt) {
		
		if(evt.getEntity() instanceof Player == false) return;
		
		Player player = (Player) evt.getEntity();
		if(player.getInventory().firstEmpty() == -1) evt.setCancelled(true);
		
	}

}
