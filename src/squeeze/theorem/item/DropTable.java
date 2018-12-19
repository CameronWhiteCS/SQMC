package squeeze.theorem.item;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.inventory.ItemStack;

public class DropTable {

	/*Fields*/
	private ArrayList<DropTableEntry> drops = new ArrayList<DropTableEntry>();
	
	/*Constructors*/
	public DropTable() {
		
	}
	
	public DropTable(ArrayList<DropTableEntry> drops) {
		this.drops = drops;
	}
	
	/*Setters and getters*/
	public ArrayList<DropTableEntry> getHashMap(){
		return this.drops;
	}
	
	
	/*Methods*/
	public DropTable addDrop(CustomItem item, double weight, int min, int max) {
		DropTableEntry entry = new DropTableEntry(item, weight, min, max);
		drops.add(entry);
		return this;
	}
	
	public DropTable addDrop(CustomItem item, double weight) {
		DropTableEntry entry = new DropTableEntry(item, weight);
		drops.add(entry);
		return this;
	}
	
	public ItemStack getDrop() {
		
		double totalWeight = 0.0;
		
		//Calculate total weight
		for(DropTableEntry dte: drops) {
			totalWeight = totalWeight + dte.getWeight();
		}
		
		//Determine random item
		double roll = Math.random() * totalWeight;
		for(DropTableEntry dte: drops) {
			roll = roll - dte.getWeight();
			if(roll <= 0) {
				Random RNG = new Random();
				int amount = RNG.nextInt(dte.getMax() - dte.getMin() + 1) + dte.getMin();
				
				if(dte.getCustomItem() == null) return null;
				return dte.getCustomItem().getItemStack(amount);
				
			}
		}
		
		return null;
	}
	
}
