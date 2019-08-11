package squeeze.theorem.ui;

import org.bukkit.entity.Player;

import squeeze.theorem.item.CustomItem;

public class UserInterfaceShop extends ChestInterface {
	
	/*Static fields*/
	public static enum ShopType{BUY, SELL}
	
	public UserInterfaceShop(String title) {
		super(title);
		
	}
	
	public UserInterfaceShop addItem(CustomItem item, int buyPrice, int sellPrice) {
		this.addComponent(new UIComponentShop(item, buyPrice, sellPrice));
		return this;
	}
	
	public UserInterfaceShop addItem(CustomItem item, int buyPrice, int sellPrice, int amount) {
		this.addComponent(new UIComponentShop(item, buyPrice, sellPrice, amount));
		return this;
	}

	@Override
	public String getTitle(Player player) {
		// TODO Auto-generated method stub
		return null;
	}

}
