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
	public UIComponentBank(int slot) {
		setSlot(slot);
	}
	
	@Override
	public ItemStack getItemStack(Player player) {
		
		PlayerData dat = DataManager.getPlayerData(player.getUniqueId());
		BankDistrict district = dat.getBankDistrict();
		if(district == null) {
			ItemStack output = new ItemStack(Material.BARRIER);
			ItemMeta meta = output.getItemMeta();
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.GOLD + "Empty");
			lore.add(ChatColor.GREEN + "Slot: " + (slot + 1));
			meta.setLore(lore);
			output.setItemMeta(meta);
			return output;
		}
		
		BankEntry entry = dat.getBankEntry(district, slot);
		if(entry == null) {
			ItemStack output = new ItemStack(Material.BARRIER);
			ItemMeta meta = output.getItemMeta();
			List<String> lore = new ArrayList<String>();
			meta.setDisplayName(ChatColor.GOLD + "Empty");
			lore.add(ChatColor.GREEN + "Slot: " + (slot + 1));
			meta.setLore(lore);
			output.setItemMeta(meta);
			return output;
		}
		
		CustomItem ci = entry.getCustomItem();
		ItemStack output = entry.getCustomItem().getItemStack();
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

	@Override
	public void onClick(InventoryClickEvent evt) {
		
		PlayerData dat = DataManager.getPlayerData(evt.getWhoClicked().getUniqueId());
		BankDistrict district = dat.getBankDistrict();
		if(district == null) return;
		
		//If depositing
		if(evt.getCursor().getType() != Material.AIR) {
			CustomItem ci = CustomItem.getCustomItem(evt.getCursor());
			if(ci == null) return;
			dat.depositItem(district, evt.getCursor(), slot);
			return;
		}
		
		//If withdrawing
		BankEntry entry = dat.getBankEntry(district, slot);
		if(entry == null) return;
		
		CustomItem ci = entry.getCustomItem();
		if(entry.getAmount() >= ci.getMaxStackSize()) {
			dat.withdrawItem(district, slot, ci.getMaxStackSize());
			
		} else {
			dat.withdrawItem(district, slot, entry.getAmount());
		}

	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

}
