package squeeze.theorem.bank;

import squeeze.theorem.item.CustomItem;

public class BankEntry {

	/*Fields*/
	private CustomItem customItem;
	private int amount;
	private BankDistrict district;
	private int slot;
	
	/*Constructors*/
	public BankEntry(BankDistrict district, CustomItem ci, int amount, int slot) {
		setDistrict(district);
		setCustomItem(ci);
		setAmount(amount);
	}
	
	/*Setters and getters*/
	public CustomItem getCustomItem() {
		return customItem;
	}
	public void setCustomItem(CustomItem customItem) {
		this.customItem = customItem;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public BankDistrict getDistrict() {
		return district;
	}
	public void setDistrict(BankDistrict district) {
		this.district = district;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}
	
}
