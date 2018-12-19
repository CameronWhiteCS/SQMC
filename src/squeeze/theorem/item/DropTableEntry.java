package squeeze.theorem.item;

public class DropTableEntry {

	private CustomItem customItem;
	private double weight;
	private int min;
	private int max;
	
	public DropTableEntry(CustomItem item, double weight, int min, int max) {
		this.customItem = item;
		this.weight = weight;
		this.min = min;
		this.max = max;
	}
	
	public DropTableEntry(CustomItem customItem, double weight) {
		this.customItem = customItem;
		this.weight = weight;
		this.min = 1;
		this.max = 1;
	}

	public CustomItem getCustomItem() {
		return customItem;
	}

	public double getWeight() {
		return weight;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}
	
}
