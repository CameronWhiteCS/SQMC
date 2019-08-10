package squeeze.theorem.ui;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.item.CustomItem;

public class UIComponentShop implements UIComponent {
	
	/*Fields*/
	private CustomItem item;
	private int amount = 1;
	private int buyPrice = 0;
	private int sellPrice = 0;
	
	
	/*Constructors*/
	public UIComponentShop(CustomItem item, int buyPrice, int sellPrice) {
		setItem(item);
		setBuyPrice(buyPrice);
		setSellPrice(sellPrice);
	}
	
	public UIComponentShop(CustomItem item, int buyPrice, int sellPrice, int amount) {
		setItem(item);
		setBuyPrice(buyPrice);
		setSellPrice(sellPrice);
		setAmount(amount);
	}
	
	/*Setters and getters*/
	public CustomItem getItem() {
		return item;
	}

	public void setItem(CustomItem item) {
		this.item = item;
	}
	
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public int getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	public int getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}
	
	private boolean canAfford(Player player) {
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
		if(dat.getBalance() >= buyPrice * amount) return true;
		return false;
	}
	
	private void buy(Player player) {
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
		Inventory inv = player.getInventory();
		
		if(inv.firstEmpty() != -1) {
			dat.setBalance(dat.getBalance() - buyPrice * amount);
			player.getInventory().addItem(getItem().getItemStack(amount));
			return;
		}
		
		if(inv.firstEmpty() == -1) {
			for(ItemStack stack: inv.getContents()) {
				if(CustomItem.getCustomItem(stack) != null) {
					if(CustomItem.getCustomItem(stack) == getItem()) {
						if(stack.getMaxStackSize() - stack.getAmount() >= amount) {
							dat.setBalance(dat.getBalance() - buyPrice * amount);
							player.getInventory().addItem(getItem().getItemStack(amount));
							return;
						}
					}
				}
			}
				
		}
		
		player.closeInventory();
		player.sendMessage(ChatColor.RED + "You don't have enough room in your inventory to complete this transaction.");
		
	}
	
	/*Inherited methods*/
	@Override
	public ItemStack getItemStack(Player player) {
		ItemStack stack = new ItemStack(getItem().getMaterial());
		ItemMeta meta = stack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		
		meta.setDisplayName(ChatColor.GOLD + getItem().getName());
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		if(canAfford(player)) {
			lore.add(ChatColor.GREEN + "Price: " + buyPrice * amount + " (" + buyPrice + ") \u2713");
		} else {
			lore.add(ChatColor.RED + "Price: " + buyPrice * amount+ " (" + buyPrice + ") \u2715");
		}
		
		lore.add(ChatColor.GREEN + "Amount: " + amount);
		lore.add(ChatColor.GREEN + "Sell price: " + sellPrice);
		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		return stack;
		
	}

	@Override
	public void onClick(InventoryClickEvent evt) {
		Player player = (Player) evt.getWhoClicked();
		
		ItemStack cursor = evt.getCursor();
		
		if(cursor.getType() == Material.AIR) {
			
			if(canAfford(player)) {
				buy(player);
			} else {
				player.sendMessage(ChatColor.RED + "You can't afford that item.");
				player.closeInventory();
			}
			
		} else {
			CustomItem ci = CustomItem.getCustomItem(cursor);
			if(ci == null || ci != this.item) return;
			int amount = CustomItem.getCount(cursor);
			cursor.setAmount(0);
			DataManager dataManager = DataManager.getInstance();
			PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
			dat.addBalance(amount * sellPrice);
		}
		
	
		
	}

}
