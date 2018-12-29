package squeeze.theorem.bank;

import squeeze.theorem.item.CustomItem;

/**
 * The BankEntry class is a Java representation of a database row from the 'bank' table.
 * @author SqueezeTheorem
 *
 */
public class BankEntry {

	/*Fields*/
	private CustomItem customItem;
	private int amount;
	private BankDistrict district;
	private int slot;
	
	/*Constructors*/
	/**
	 * 
	 * @param district The Bank district where this BankEntry is to reside.
	 * @param customItem The CustomItem associated with this slot.
	 * @param amount The amount of the CustomItem present in this BankEntry.
	 * @param slot THe slot in which this BankEntry resides.
	 */
	public BankEntry(BankDistrict district, CustomItem customItem, int amount, int slot) {
		setDistrict(district);
		setCustomItem(customItem);
		setAmount(amount);
		setSlot(slot);
	}
	
	/*Setters and getters*/
	/**
	 * 
	 * @return This bank entry's CustomItem
	 */
	public CustomItem getCustomItem() {
		return customItem;
	}
	
	/**
	 * 
	 * @param customItem The CustomItem to be associated with this BankEntry.
	 */
	public void setCustomItem(CustomItem customItem) {
		this.customItem = customItem;
	}
	
	/**
	 * 
	 * @return The number of items stored in this BankEntry
	 */
	public int getAmount() {
		return amount;
	}
	
	/**
	 * 
	 * @param amount The number of items to be stored in this BankEntry.
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	/**
	 * 
	 * @return The BankDistrict associated with this BankEntry
	 */
	public BankDistrict getDistrict() {
		return district;
	}
	
	/**
	 * 
	 * @param district The BankDistrict of this BankEntry.
	 */
	public void setDistrict(BankDistrict district) {
		this.district = district;
	}

	/**
	 * 
	 * @return The slot of this BankEntry
	 */
	public int getSlot() {
		return slot;
	}

	/**
	 * 
	 * @param slot The slot of this BankEntry.
	 */
	public void setSlot(int slot) {
		this.slot = slot;
	}
	
}
