package squeeze.theorem.bank;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.item.CustomItem;

/**
 * An instance of the BankAccount class resides within the PlayerData class and houses methods related to storing, depositing, and 
 * withdrawing items.
 * @author SqueezeTheorem
 *
 */
public class BankAccount {

	/*Static fields*/
	public static final int MAX_SLOTS = 450;
	
	/*Fields*/
	private PlayerData dat;
	private Map<BankDistrict, BankEntry[]> banks = new HashMap<BankDistrict, BankEntry[]>();
	
	/*Constructors*/
	/**
	 * 
	 * @param dat The owner of this bank account
	 */
	public BankAccount(PlayerData dat) {
		setPlayerData(dat);
		for(BankDistrict d: BankDistrict.values()) {
			banks.put(d, new BankEntry[MAX_SLOTS]);
		}
	}
	
	/**
	 * 
	 * @param player The owner of this bank account
	 */
	public BankAccount(Player player) {
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player);
		setPlayerData(dat);
		for(BankDistrict d: BankDistrict.values()) {
			banks.put(d, new BankEntry[MAX_SLOTS]);
		}
	}
	
	/*Setters and getters*/
	private void setPlayerData(PlayerData dat) {
		this.dat = dat;
	}
	
	/**
	 * 
	 * @return The owner of this bank account
	 */
	public PlayerData getPlayerData() {
		return dat;
	}
	
	/**
	 * 
	 * @return The owner of this bank account.
	 */
	public Player getPlayer() {
		return getPlayerData().getPlayer();
	}
	
	/*Methods*/
	/**
	 * Depositing an ItemStack using this operation places it into this bank account and automatically reduces the count of the ItemStack to 0.
	 * This operation will succeed even if an item already exists in the specified slot. 
	 * @param district The bank district where the deposited item is to reside
	 * @param stack The ItemStack to be deposited. 
	 * @param slot The slot in the bank where this item should be deposited. 
	 */
	public void depositItem(BankDistrict district, ItemStack stack, int slot){
		BankEntry[] bank = this.banks.get(district);
		CustomItem ci = CustomItem.getCustomItem(stack);
		if(ci == null) return;
		for(BankEntry b: bank) {
			if(b == null) continue;
			if(b.getDistrict() != district) continue;
			if(b.getCustomItem() == ci) {
				b.setAmount(b.getAmount() + CustomItem.getCount(stack));
				stack.setAmount(0);
				return;
			}
		}

			if(bank[slot] != null) return;
			bank[slot] = new BankEntry(district, ci, CustomItem.getCount(stack), slot);
			stack.setAmount(0);
	}
	
	/**
	 * 
	 * @param district The BankDistrict of the BankEntry being looked up.
	 * @param slot The slot of the BankEntry being returned.
	 * @return The BankEntry associated with the specified slot. Can return null.
	 */
	public BankEntry getBankEntry(BankDistrict district, int slot) {
		BankEntry[] bank = this.banks.get(district);
		if(bank.length - 1 < slot) return null;
		return bank[slot];
	}
	
	/**
	 * This operation withdraws the BankEntry at the specified slot and attempts to place the withdrawn item into the account owner's
	 * inventory. If the owner's inventory is full, the output will be dropped to the ground instead. If the specified entry is either null or
	 * non-existent, the method will terminate prematurely. 
	 * @param district The BankDistrict of this operation.
	 * @param slot The slot of the BankEntry to be withdrawn.
	 * @param amount How much of the item to be withdrawn
	 */
	public void withdrawItem(BankDistrict district, int slot, int amount) {
		BankEntry[] bank = banks.get(district);
		BankEntry entry = bank[slot];
		if(entry == null) return;
		CustomItem ci = entry.getCustomItem();
		if(entry.getAmount() >= amount) {
			entry.setAmount(entry.getAmount() - amount);
			getPlayerData().giveItem(ci, amount);
		} else {
			getPlayerData().giveItem(ci, entry.getAmount());
			entry.setAmount(0);
		}
		
		if(entry.getAmount() <= 0) {
			bank[slot] = null;
			banks.put(district, bank);
		}
		
	}
	
}
