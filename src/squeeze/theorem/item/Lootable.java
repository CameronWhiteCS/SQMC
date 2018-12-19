package squeeze.theorem.item;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Lootable {

	public List<ItemStack> getLoot(Player player);
	
	public default void dropLoot(Player player, Location loc) {
		List<ItemStack> drops = getLoot(player);
		World world = loc.getWorld();
		for(ItemStack stack: drops) {
			
			world.dropItem(loc, stack);
		}
	}
	
}
