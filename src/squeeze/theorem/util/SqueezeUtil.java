package squeeze.theorem.util;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SqueezeUtil {

	public static ItemStack generateItem(Material material, int amount, String title, String...lore) {
		ItemStack stack = new ItemStack(material, amount);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(title);
		meta.setLore(Arrays.asList(lore));
		stack.setItemMeta(meta);
		return stack;
	}
	
}
