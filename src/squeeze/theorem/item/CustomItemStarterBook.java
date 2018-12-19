package squeeze.theorem.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class CustomItemStarterBook extends CustomItem {

	public CustomItemStarterBook(int ID, String name, Material material, String... lore) {
		super(ID, name, material, lore);

	}
	
	@Override
	public ItemStack getItemStack(int amount) {
		
		ItemStack output = super.getItemStack(amount);
		
		BookMeta meta = (BookMeta) output.getItemMeta();
		meta.setTitle("Starter book");
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addPage("Welcome to SQMC, a minecraft MMORPG. Here are some things you should know:\n 1.) You can find a tutorial video using the /tutorial command.\n 2.) By connecting to our servers and/or using any services provided by SQMC, you agree to our terms of service");
		meta.addPage("Which can be found using the /tos commnad.\n 3.) If the music tracks lag for you, you can disable them using the /settings command. \n Good luck, and have fun in the world of SQMC!");
		
		
		output.setItemMeta(meta);
		
		return output;
		
	}

}
