package squeeze.theorem.mechanics;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class TimeMechanics implements Runnable {

	@Override
	public void run() {
		
		for(World w: Bukkit.getWorlds()) {
			w.setTime(w.getTime() + 1);
		}
		
	}

	
	
}
