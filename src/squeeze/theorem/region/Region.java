package squeeze.theorem.region;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerSet;
import org.json.JSONArray;
import org.json.JSONObject;

import squeeze.theorem.audio.SQMCSong;
import squeeze.theorem.bank.BankDistrict;
import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.main.SQMC;

public class Region implements Listener {

	/*Static fields*/
	private static DynmapAPI dynmapAPI = (DynmapAPI) Bukkit.getServer().getPluginManager().getPlugin("dynmap");
	private static MarkerSet markerSet = dynmapAPI.getMarkerAPI().createMarkerSet("sqmc.markerset", "regions", dynmapAPI.getMarkerAPI().getMarkerIcons(), false);
	private static ArrayList<Region> regions = new ArrayList<Region>();
	public static Region uppkomst = parse("uppkomst");
		
	
	/*Fields*/
	private ArrayList<Cuboid> cuboids = new ArrayList<Cuboid>();
	private String name;
	private String description;
	private int Color;
	private SQMCSong song;
	private BankDistrict bankDistrict;
	
	/*Constructors*/
	public Region(String name, String description) {
		setName(name);
		setDescription(description);
		regions.add(this);
	}
	
	public ArrayList<Cuboid> getCuboids() {
		return cuboids;
	}

	/*Setters and getters*/
	
	public BankDistrict getBankDistrict() {
		return bankDistrict;
	}

	public void setBankDistrict(BankDistrict bankDistrict) {
		this.bankDistrict = bankDistrict;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getColor() {
		return Color;
	}

	public Region setColor(int color) {
		Color = color;
		return this;
	}
	
	public Region setSong(SQMCSong s) {
		this.song = s;
		return this;
	}
	
	public SQMCSong getSong() {
		return this.song;
	}

	public Region addCuboid(Cuboid boid) {
		cuboids.add(boid);
		return this;
	}
	
	public Region addCuboid(Location loc1, Location loc2) {
		cuboids.add(new Cuboid(loc1, loc2));
		return this;
	}
	
	public Region addCuboid(World world, double x1, double y1, double z1, double x2, double y2, double z2) {
		this.addCuboid(new Cuboid(world, x1, y1, z1, x2, y2, z2));
		return this;
	}
	
	public Region addCuboid(String world, double x1, double y1, double z1, double x2, double y2, double z2) {
		this.addCuboid(new Cuboid(world, x1, y1, z1, x2, y2, z2));
		return this;
	}
	
	public static List<Region> getRegions(){
		return regions;
	}
	
	/*Methods*/
	public boolean contains(Location loc) {
		for(Cuboid boid: getCuboids()) {
			if(boid.contains(loc)) return true;
		}
		return false;
	}
	
	/*Dynmap markers*/
	public static void onEnable() {
		addMarkers();	
	}
	
	public static Region fromJSON(JSONObject o) {
		JSONObject jsonRegion = (JSONObject) o;
		
		String name = jsonRegion.getString("name");
		String desc = jsonRegion.getString("desc");
		Region region = new Region(name, desc);
		
		if(jsonRegion.has("color")) region.setColor((int) Long.parseLong(jsonRegion.getString("color"), 16));
		if(jsonRegion.has("song")) region.setSong(SQMCSong.getSong(jsonRegion.getString("song")));
		
		if(jsonRegion.has("cuboids")) {
			JSONArray jsonCuboids = jsonRegion.getJSONArray("cuboids");
			for(Object o2: jsonCuboids) {
				JSONObject jsonCuboid = (JSONObject) o2;
				double x1 = jsonCuboid.getDouble("x1");
				double y1 = jsonCuboid.getDouble("y1");
				double z1 = jsonCuboid.getDouble("z1");
				double x2 = jsonCuboid.getDouble("x2");
				double y2 = jsonCuboid.getDouble("y2");
				double z2 = jsonCuboid.getDouble("z2");
				World world = Bukkit.getWorld(jsonCuboid.getString("world"));
				Cuboid boid = new Cuboid(world, x1, y1, z1, x2, y2, z2);
				if(jsonCuboid.has("song")) boid.setSong(SQMCSong.getSong(jsonCuboid.getString("song")));
				region.addCuboid(boid);
			}
		}
		
		if(jsonRegion.has("banking-district")) {
			String districtString = jsonRegion.getString("banking-district");
			for(BankDistrict d: BankDistrict.values()) {
				if(d.toString().equalsIgnoreCase(districtString)) {
					region.setBankDistrict(d);
					break;
				}
			}
		}

		return region;
	}
	
	public static Region parse(String filename) {
		
		try {
			
			File file = new File(SQMC.getPlugin(SQMC.class).getDataFolder() + "/res/region/" + filename + ".json");
			
			Scanner sc = new Scanner(file);
			String s = "";
			while(sc.hasNext()) {
				s += sc.nextLine();
			}
			sc.close();
			return fromJSON(new JSONObject(s));
		} catch(IOException exc) {
			exc.printStackTrace();
			return null;
		}
	}

	private static void addMarkers() {
		for(Region r: regions) {
			for(int i = 0; i < r.getCuboids().size(); i++) {
				 AreaMarker am = markerSet.createAreaMarker(r.getName() + "." + i, "Dungeon1", false, Bukkit.getWorld("world").getName(), new double[100], new double[100], false);
				 Cuboid boid = r.getCuboids().get(i);
			       double[] d1 = {boid.getLowerX(), boid.getUpperX()};
			       double[] d2 = {boid.getLowerZ(), boid.getUpperZ()};
			       am.setCornerLocations(d1, d2);
			       am.setDescription("<b>" + r.getName() + "</b><br>" + r.getDescription());
			       if(boid.getColor() == -1) am.setFillStyle(0.22, r.getColor());
			       if(boid.getColor() == -1) am.setLineStyle(0, 0.22, r.getColor());
			       if(boid.getColor() != -1) am.setFillStyle(0.22, boid.getColor());
			       if(boid.getColor() != -1) am.setLineStyle(0, 0.22, boid.getColor());
			}
		}
	}

	/*Events*/
	@EventHandler
	public static void onEnter(PlayerMoveEvent evt) {
		
		Location to = evt.getTo();
		for(Region r: getRegions()) {
			
			for(Cuboid boid: r.getCuboids()) {
				if(boid.contains(to) && boid.getSong() != null) {
					DataManager dataManager = DataManager.getInstance();
					PlayerData dat = dataManager.getPlayerData(evt.getPlayer().getUniqueId());
					dat.playSong(boid.getSong());
					return;
				}
			}
			
			if(r.contains(to) && r.getSong() != null) {
				DataManager dataManager = DataManager.getInstance();
				PlayerData dat = dataManager.getPlayerData(evt.getPlayer().getUniqueId());
				dat.playSong(r.getSong());
				return;
			}
		}
		
	}
	
}
