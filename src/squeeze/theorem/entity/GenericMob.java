package squeeze.theorem.entity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.JSONArray;
import org.json.JSONObject;

import squeeze.theorem.combat.AttackStyle;
import squeeze.theorem.combat.CombatStats;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.item.DropTable;
import squeeze.theorem.item.Lootable;

public class GenericMob extends SQMCEntity implements CombatStats, Respawnable, Lootable, Boundable {

	/*Fields*/
	private List<Location> locations = new ArrayList<Location>();
	private double wanderRadius;
	private List<DropTable> dropTables = new ArrayList<DropTable>();
	private int respawnDelay = 0;
	private Map<AttackStyle, Double> accuracy = new HashMap<AttackStyle, Double>();
	private Map<AttackStyle, Double> strength = new HashMap<AttackStyle, Double>();
	private Map<AttackStyle, Double> defense = new HashMap<AttackStyle, Double>();
	private double health = 5;
	
	/*Constructors*/
	public GenericMob(String name, EntityType entityType) {
		super(name, entityType);
	}

	/*Boundable*/
	public void setWanderRadius(double radius) {
		this.wanderRadius = radius;
	}
	
	@Override
	public double getWanderRadius() {
		return this.wanderRadius;
	}
	
	/*Respawnable*/
	public void setRespawnDelay(int delay) {
		this.respawnDelay = delay;
	}
	
	@Override
	public int getRespawnDelay() {
		return this.respawnDelay;
	}
	
	/*CombatStats*/
	public void setStrength(AttackStyle style, double value) {
		strength.put(style, value);
	}
	
	@Override
	public double getStrength(AttackStyle style) {
		if(strength.containsKey(style)) return strength.get(style);
		
		return 0;
	}

	public void setDefense(AttackStyle style, double value) {
		defense.put(style, value);
	}
	
	@Override
	public double getDefense(AttackStyle style) {
		if(defense.containsKey(style)) return defense.get(style);
		
		return 0;
	}

	public void setAccuracy(AttackStyle style, double value) {
		accuracy.put(style, value);
	}
	
	@Override
	public double getAccuracy(AttackStyle style) {
		if(accuracy.containsKey(style)) return accuracy.get(style);
		
		return 0;
	}
	
	public void setHealth(double health) {
		this.health = health;
	}
	
	@Override
	public double getHealth() {
		return this.health;
	}
	
	/*Anchorable*/
	@Override
	public List<Location> getLocations() {
		return this.locations;
	}
	
	private void setLocations(List<Location> locs) {
		this.locations = locs;
	}
	
	public void addLocation(Location loc) {
		locations.add(loc);
	}
	
	/*Lootable*/
	@Override
	public List<ItemStack> getLoot(Player player) {
		List<ItemStack> output = new ArrayList<ItemStack>();
		
		for(DropTable table: getDropTables()) {
			ItemStack drop = table.getDrop();
			if(drop == null) continue;
			if(drop.getType() == Material.AIR) continue;
			if(drop.getAmount() <= 0) continue;			
			output.add(drop);
		}
		
		return output;
	}
	
	public List<DropTable> getDropTables(){
		return this.dropTables;
	}
	
	private void setDropTables(List<DropTable> dropTables) {
		this.dropTables = dropTables;
	}
	
	public void addDropTable(DropTable table) {
		dropTables.add(table);
	}
	
	public static GenericMob fromJSON(JSONObject obj) {
		
		try {
			
			/*Initialize Variables*/
			EntityType type = EntityType.VILLAGER;
			String name = "";
			String prefix = "";
			String suffix = "";
			double meleeStrength = 0, rangedStrength = 0, magicStrength = 0;
			double meleeAccuracy = 0, rangedAccuracy = 0, magicAccuracy = 0;
			double meleeDefense = 0, rangedDefense = 0, magicDefense = 0;
			CustomItem mainhand = null, offhand = null, helmet = null, chestplate = null, leggings = null, boots = null;
			double wanderRadius = 0;
			double health = 1;
			int respawnDelay = 0;
			List<Location> locs = new ArrayList<Location>();
			List<DropTable> dropTables = new ArrayList<DropTable>();
			
			
			/*Assign values to variables*/
			String entityTypeString = obj.getString("entity-type");
			for(EntityType t: EntityType.values()) {
				if(t.toString().toLowerCase().equalsIgnoreCase(entityTypeString)) type = t;
			}
			
			if(obj.has("name")) name = obj.getString("name");
			if(obj.has("prefix")) prefix = obj.getString("prefix");
			if(obj.has("suffix")) suffix = obj.getString("suffix");
			
			if(obj.has("wander-radius")) wanderRadius = obj.getDouble("wander-radius");
			if(obj.has("respawn-delay")) respawnDelay = obj.getInt("respawn-delay");
			
			if(obj.has("melee-accuracy")) meleeAccuracy = obj.getDouble("melee-accuracy");
			if(obj.has("ranged-accuracy")) rangedAccuracy = obj.getDouble("ranged-accuracy");
			if(obj.has("magic-accuracy")) magicAccuracy = obj.getDouble("magic-accuracy");
			if(obj.has("melee-strength")) meleeStrength = obj.getDouble("melee-strength");
			if(obj.has("ranged-strength")) rangedStrength = obj.getDouble("ranged-strength");
			if(obj.has("magic-strength")) magicStrength = obj.getDouble("magic-strength");
			if(obj.has("melee-defense")) meleeDefense = obj.getDouble("melee-defense");
			if(obj.has("ranged-defense")) rangedDefense = obj.getDouble("ranged-defense");
			if(obj.has("magic-defense")) magicDefense = obj.getDouble("magic-defense");
			if(obj.has("health")) health = obj.getDouble("health");
			
			if(obj.has("main-hand")) mainhand = CustomItem.getCustomItem(obj.getInt("main-hand"));
			if(obj.has("off-hand")) offhand = CustomItem.getCustomItem(obj.getInt("off-hand"));
			if(obj.has("helmet")) helmet = CustomItem.getCustomItem(obj.getInt("helmet"));
			if(obj.has("chestplate")) chestplate = CustomItem.getCustomItem(obj.getInt("chestplate"));
			if(obj.has("leggings")) leggings = CustomItem.getCustomItem(obj.getInt("leggings"));
			if(obj.has("boots")) boots = CustomItem.getCustomItem(obj.getInt("boots"));
			
			if(obj.has("locations")) {
				JSONArray jsonLocations = obj.getJSONArray("locations");
				for(Object o: jsonLocations) {
					JSONObject jsonLoc = (JSONObject) o;
					World world = Bukkit.getWorld(jsonLoc.getString("world"));
					double x = jsonLoc.getDouble("x");
					double y = jsonLoc.getDouble("y");
					double z = jsonLoc.getDouble("z");
					locs.add(new Location(world, x, y, z));
				}
			}
			
			if(obj.has("drop-tables")) {
				JSONArray jsonDropTables = obj.getJSONArray("drop-tables");
				for(Object o: jsonDropTables) {
					DropTable dropTable = new DropTable();
					JSONArray dropTableEntries = (JSONArray) o;
						for(Object o2: dropTableEntries) {
							JSONObject entry = (JSONObject) o2;
							int id = 1, min = 1, max = 1;
							double weight = 1;
							if(entry.has("item-id")) id = entry.getInt("item-id");
							if(entry.has("min")) min = entry.getInt("min");
							if(entry.has("max")) max = entry.getInt("max");
							if(entry.has("weight")) weight = entry.getDouble("weight");
							dropTable.addDrop(CustomItem.getCustomItem(id), weight, min, max);
						}
					dropTables.add(dropTable);
				}
				
			}
			
			/*Assign variables to mob*/
			GenericMob mob = new GenericMob(name, type);
			mob.setSuffix(suffix);
			mob.setPrefix(prefix);
			mob.setStrength(AttackStyle.MELEE, meleeStrength);
			mob.setStrength(AttackStyle.RANGED, rangedStrength);
			mob.setStrength(AttackStyle.MAGIC, magicStrength);
			mob.setDefense(AttackStyle.MELEE, meleeDefense);
			mob.setDefense(AttackStyle.RANGED, rangedDefense);
			mob.setDefense(AttackStyle.MAGIC, magicDefense);
			mob.setAccuracy(AttackStyle.MELEE, meleeAccuracy);
			mob.setAccuracy(AttackStyle.RANGED, rangedAccuracy);
			mob.setAccuracy(AttackStyle.MAGIC, magicAccuracy);
			mob.setDropTables(dropTables);
			mob.setLocations(locs);
			mob.setWanderRadius(wanderRadius);
			mob.setRespawnDelay(respawnDelay);
			mob.setHealth(health);
			mob.setMainhand(mainhand);
			mob.setOffhand(offhand);
			mob.setHelmet(helmet);
			mob.setChestplate(chestplate);
			mob.setLeggings(leggings);
			mob.setBoots(boots);
			
			return mob;
			
			
		} catch(Exception exc) {
			Bukkit.getLogger().log(Level.SEVERE, "[SQMC] Failed to create GenericMob via JSON.");
			exc.printStackTrace();
			return null;
		}
		
	}
	
	public static GenericMob fromFileName(String fileName) {
		
		InputStream stream = GenericMob.class.getResourceAsStream("/genericmob/" + fileName + ".json");
		Scanner sc = new Scanner(stream);
		String s = "";
		while(sc.hasNextLine()) {
			s += sc.nextLine();
		}
		sc.close();
		JSONObject obj = new JSONObject(s);
		return fromJSON(obj);
		
	}
	
}
