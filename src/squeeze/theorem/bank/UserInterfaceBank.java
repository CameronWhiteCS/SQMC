package squeeze.theorem.bank;

import org.bukkit.entity.Player;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.ui.ChestInterface;

/**
 * 
 * @author SqueezeTheorem
 *
 */
public class UserInterfaceBank extends ChestInterface {

	public UserInterfaceBank() {
		super("Bank");
		for(int i = 0; i <= BankAccount.MAX_SLOTS - 1; i++) {
			addComponent(new UIComponentBank((i)));
		}
	}
	
	@Override
	public String getTitle(Player player) {
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
		BankDistrict dist = dat.getBankDistrict();
		String name = dist.toString().toLowerCase();
		name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
		return name + " Bank";
	}
	 
}
