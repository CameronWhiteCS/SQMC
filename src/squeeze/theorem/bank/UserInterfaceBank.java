package squeeze.theorem.bank;

import org.bukkit.entity.Player;

import squeeze.theorem.data.BankAccount;
import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.ui.MultiPageInterface;

public class UserInterfaceBank extends MultiPageInterface {

	public UserInterfaceBank() {
		super("Bank", 45);
		for(int i = 0; i <= BankAccount.MAX_SLOTS - 1; i++) {
			addComponent(new UIComponentBank((i)));
		}
	}
	
	@Override
	public String getTitle(Player player) {
		PlayerData dat = DataManager.getPlayerData(player.getUniqueId());
		BankDistrict dist = dat.getBankDistrict();
		String name = dist.toString().toLowerCase();
		name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
		return String.format(name + " Bank (%s)", dat.getSessionData().getUIpage() + 1);
	}
	 
}
