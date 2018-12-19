package squeeze.theorem.ui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface UIComponent {
	
	public ItemStack getItemStack(Player player);
	
	public void onClick(InventoryClickEvent evt);

}
