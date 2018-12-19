package squeeze.theorem.entity.uppkomst;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import squeeze.theorem.entity.GenericMob;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.item.DropTable;

public class Cow extends GenericMob {

	public Cow() {
		super("Cow", EntityType.COW);
		
		
		setRespawnDelay(300);
		addLocation(new Location(Bukkit.getWorld("world"), 4441.5, 106, -8569.5));
		addLocation(new Location(Bukkit.getWorld("world"), 4451.5, 106, -8479.5));
		addLocation(new Location(Bukkit.getWorld("world"), 4461.5, 106, -8478.5));
		addLocation(new Location(Bukkit.getWorld("world"), 4464.5, 106, -8489.5));
		addLocation(new Location(Bukkit.getWorld("world"), 4455.5, 99, -8499.5));
		addLocation(new Location(Bukkit.getWorld("world"), 4455.5, 99, -8517.5));
		addLocation(new Location(Bukkit.getWorld("world"), 4427.5, 99, -8503.5));
		
		setWanderRadius(50);
		setHealth(5);
		
		DropTable leather = new DropTable();
		leather.addDrop(CustomItem.LEATHER, 1);
		leather.addDrop(null, 1);
		addDropTable(leather);
		
	}

}
