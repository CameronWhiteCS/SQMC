package squeeze.theorem.bank;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import squeeze.theorem.data.PlayerData;
import squeeze.theorem.item.CustomItem;

public class BankAccount {

	/*Static fields*/
	public static final int MAX_SLOTS = 450;
	
	/*Fields*/
	private PlayerData dat;
	private Map<BankDistrict, BankEntry[]> banks = new HashMap<BankDistrict, BankEntry[]>();
	
	/*Setters and getters*/
	public PlayerData getPlayerData() {
		return dat;
	}

	public void setPlayerData(PlayerData dat) {
		this.dat = dat;
	}
	
	/*Constructors*/
	public BankAccount(PlayerData dat) {
		setPlayerData(dat);
		for(BankDistrict d: BankDistrict.values()) {
			banks.put(d, new BankEntry[MAX_SLOTS]);
		}
	}
	
	/*Methods*/
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
	
	public BankEntry getBankEntry(BankDistrict district, int slot) {
		BankEntry[] bank = this.banks.get(district);
		if(bank.length - 1 < slot) return null;
		return bank[slot];
	}
	
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
