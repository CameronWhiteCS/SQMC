package squeeze.theorem.bank;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.ui.UIComponent;

public class UIComponentBank implements UIComponent {

	/*Fields*/
	private int slot;

	/*Constructors*/
	/**
	 * 
	 * @param slot The slot of this component's associated BankEntry
	 */
	public UIComponentBank(int slot) {
		setSlot(slot);
	}

	/*Setters and getters*/
	/**
	 * 
	 * @param slot The slot of this component's associated BankEntry
	 */
	public void setSlot(int slot) {
		this.slot = slot;
	}
	
	/**
	 * 
	 * @return The slot of this component's associated BankEntry
	 */
	public int getSlot() {
		return this.slot;
	}
	
	/*Methods*/
	private ItemStack getEmptySlot() {
		ItemStack output = new ItemStack(Material.BARRIER);
		ItemMeta meta = output.getItemMeta();
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.GOLD + "Empty");
		lore.add(ChatColor.GREEN + "Slot: " + (slot + 1));
		meta.setLore(lore);
		output.setItemMeta(meta);
		return output;
	}
	
	private ItemStack getFilledSlot(BankEntry entry) {
		CustomItem ci = entry.getCustomItem();
		ItemStack output = ci.getItemStack();
		ItemMeta meta = new ItemStack(Material.STONE).getItemMeta();
		for(Enchantment e: ci.getEnchantments().keySet()) {
			meta.addEnchant(e, ci.getEnchantments().get(e), true);
		}
		List<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.GOLD + ci.getName());
		lore.add(ChatColor.GREEN + "Slot: " + (slot + 1));
		lore.add(ChatColor.GREEN + "Amount: " + entry.getAmount());
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
		meta.setLore(lore);
		output.setItemMeta(meta);
		return output;
	}
	
	/*Inherited methods*/
	@Override
	public ItemStack getItemStack(Player player) {
		
		PlayerData dat = DataManager.getPlayerData(player.getUniqueId());
		
		BankDistrict district = dat.getBankDistrict();
		if(district == null) return getEmptySlot();
		
		BankEntry entry = dat.getBankAccount().getBankEntry(district, slot);
		if(entry == null) return getEmptySlot();
		
		return getFilledSlot(entry);
	}
	
	//TODO: Add functionality, clean up.
	@Override
	public void onClick(InventoryClickEvent evt) {
		
		PlayerData dat = DataManager.getPlayerData(evt.getWhoClicked().getUniqueId());
		BankDistrict district = dat.getBankDistrict();
		if(district == null) return;
		
		//If depositing
		if(evt.getCursor().getType() != Material.AIR) {
			CustomItem ci = CustomItem.getCustomItem(evt.getCursor());
			if(ci == null) return;
			dat.getBankAccount().depositItem(district, evt.getCursor(), slot);
			return;
		}
		
		//If withdrawing
		BankEntry entry = dat.getBankAccount().getBankEntry(district, slot);
		if(entry == null) return;
		
		CustomItem ci = entry.getCustomItem();
		if(entry.getAmount() >= ci.getMaxStackSize()) {
			dat.getBankAccount().withdrawItem(district, slot, ci.getMaxStackSize());
			
		} else {
			dat.getBankAccount().withdrawItem(district, slot, entry.getAmount());
		}

	}
}
