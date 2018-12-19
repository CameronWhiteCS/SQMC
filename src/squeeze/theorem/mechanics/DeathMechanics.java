package squeeze.theorem.mechanics;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;

public class DeathMechanics implements Listener {

	private static Random RNG = new Random();
	
	@EventHandler
	public static void onDeath(PlayerDeathEvent evt) {
	
		/*Send custom death message & cancel vanilla death broadcast*/
		int roll = RNG.nextInt(4);
		String text = "";
	
		if(roll == 0) text = "Oh death, where is thy sting?";
		if(roll == 1) text = "Never fear the grave.";
		if(roll == 2) text = "Oh dear, you are dead!";
		if(roll == 3) text = "So, it all comes down to this?";
		
		evt.getEntity().sendMessage(ChatColor.RED + text);
		evt.setDeathMessage(null);
		
		//halve balance
		PlayerData dat = DataManager.getPlayerData(evt.getEntity().getUniqueId());
		dat.setBalance(dat.getBalance() / 2);
		
	}
	
}
