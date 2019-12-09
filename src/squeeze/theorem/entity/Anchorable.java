package squeeze.theorem.entity;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;

public interface Anchorable {

	public List<Location> getLocations();
	
	public default List<Location> getLocationsWithin(Chunk c) {
	
		List<Location> output = new ArrayList<Location>();
		
		for(Location loc: getLocations()) {
			
			if(loc.getChunk().equals(c)) output.add(loc);
			
		}
		
		return output;
	}
	
	
	
}
