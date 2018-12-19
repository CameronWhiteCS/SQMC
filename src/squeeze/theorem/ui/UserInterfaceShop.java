package squeeze.theorem.ui;

import squeeze.theorem.item.CustomItem;

public class UserInterfaceShop extends MultiPageInterface {
	
	/*Static fields*/
	public static enum ShopType{BUY, SELL}
	
	public UserInterfaceShop(String title, int size) {
		super(title, size);
		
	}
	
	public UserInterfaceShop addItem(CustomItem item, int buyPrice, int sellPrice) {
		this.addComponent(new UIComponentShop(item, buyPrice, sellPrice));
		return this;
	}
	
	public UserInterfaceShop addItem(CustomItem item, int buyPrice, int sellPrice, int amount) {
		this.addComponent(new UIComponentShop(item, buyPrice, sellPrice, amount));
		return this;
	}

}
