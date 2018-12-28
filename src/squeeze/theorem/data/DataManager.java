package squeeze.theorem.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import squeeze.theorem.bank.BankAccount;
import squeeze.theorem.bank.BankDistrict;
import squeeze.theorem.bank.BankEntry;
import squeeze.theorem.combat.CombatMode;
import squeeze.theorem.config.ConfigManager;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.skill.Skill;
import squeeze.theorem.skill.witchcraft.Spellbook;

public class DataManager implements Listener, Runnable {
	/*Test commentt*/
	/*Static fields*/
	private static Map<UUID, PlayerData> players = new ConcurrentHashMap<UUID, PlayerData>();
	private static Map<UUID, PlayerData> prelogin = new ConcurrentHashMap<UUID, PlayerData>();
	
	private static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	private static final String HOST = ConfigManager.getMysqlHost();
	private static final int PORT = ConfigManager.getMysqlPort();
	private static final String USERNAME = ConfigManager.getMysqlUser();
	private static final String PASSWORD = ConfigManager.getMysqlPassword();
	private static final String DATABASE = ConfigManager.getMysqlDatabase();
	private static final String URL = String.format("jdbc:mysql://%s:%s/%s?useSSL=false", HOST, PORT, DATABASE);
	
	public  static void loadAllPlayers() {
		
		try {
			
			Connection conn = getConnection();

			for(Player p: Bukkit.getOnlinePlayers()) {
				UUID id = p.getUniqueId();
				if(isRegistered(id, conn)) {
					players.put(id, fromDatabase(id, conn));
				} else {
					players.put(id, new PlayerData(id));
				}
			}
			
			conn.close();
			
		} catch(Exception exc) {
			exc.printStackTrace();
		}
		
	}

	public static void saveAllPlayers() {
	
		try {
			
			Connection conn = getConnection();
			
			for(Player p: Bukkit.getOnlinePlayers()) {
				UUID id = p.getUniqueId();
				saveToDatabase(id, conn);
			}
			
			conn.close();
			
		} catch(Exception exc) {
			
		}
		
	}
	
	@EventHandler
	public static void onJoin(AsyncPlayerPreLoginEvent evt) {
		
		try {
			Connection conn = getConnection();
			if(conn == null) {
				
				evt.setKickMessage("Error loading player data");
				evt.setLoginResult(Result.KICK_OTHER);
				return;
			}
			
			PlayerData dat;
			UUID id = evt.getUniqueId();
			if(isRegistered(id, conn)) {
				dat = fromDatabase(id, conn);
			} else {
				dat = new PlayerData(id);
			}
			
			prelogin.put(evt.getUniqueId(), dat);
			
			conn.close();
			
		} catch(Exception exc) {
			
			exc.printStackTrace();
			evt.setKickMessage("Error loading player data.");
			evt.setLoginResult(Result.KICK_OTHER);
			
		}
		
	}

	@EventHandler
	public static void onJoin(PlayerJoinEvent evt) {
		
		Player player = evt.getPlayer();
		UUID id = player.getUniqueId();
		PlayerData dat;
		VanillaData vdat;
		
		try {
			players.put(id, prelogin.get(id));
			prelogin.remove(id);
			dat = getPlayerData(id);
			vdat = dat.getVanillaData();
		} catch(Exception exc) {
			evt.getPlayer().kickPlayer("Failed to load player data (connected while server was starting up!)");
			Bukkit.getLogger().log(Level.SEVERE, String.format("[SQMC] Failed to load player %s (uuid='%s') because they attempted to connect too early. Disconnecting.", player.getName(), player.getUniqueId()));
			evt.setJoinMessage(null);
			return;
		}
		
		evt.setJoinMessage(ChatColor.GREEN + "+" + ChatColor.GRAY + evt.getPlayer().getName());

		
		/*Location*/
		player.teleport(new Location(vdat.getWorld(), vdat.getX(), vdat.getY(), vdat.getZ(), vdat.getYaw(), vdat.getPitch()));
		player.setFoodLevel(vdat.getFoodLevel());
		player.setFireTicks(vdat.getFireTicks());
		player.setRemainingAir(vdat.getAir());
		player.setFallDistance(vdat.getFallDistance());
		
		/*Inventory*/
		Inventory inv = player.getInventory();
		inv.clear();
		Map<Integer, ItemStack> items = dat.getItems();
		for(int i: items.keySet()) {
			inv.setItem(i, items.get(i));
		}
	
	}
	
	@EventHandler
	public static void onQuit(PlayerQuitEvent evt) {
		Player player = evt.getPlayer();
		UUID id = player.getUniqueId();
		Connection conn;
		
		try {
			conn = getConnection();
		} catch(Exception exc) {
			Bukkit.getLogger().log(Level.SEVERE, String.format("[SQMC] Failed to save player %s (uuid='%s') to the database -- failed to establish connection to MySQL DB.", player.getName(), id.toString()));
			return;
		}
		
		try {
			saveToDatabase(id, conn);
			players.remove(id);
		} catch(SQLException exc) {
			Bukkit.getLogger().log(Level.SEVERE, String.format("[SQMC] Failed to save player %s (uuid='%s') to the database -- SQLException", player.getName(), id.toString()));
			exc.printStackTrace();
			evt.setQuitMessage(ChatColor.RED + "-" + ChatColor.GRAY + evt.getPlayer().getName());
		} catch(PlayerNotLoadedException exc) {
			Bukkit.getLogger().log(Level.SEVERE, String.format("[SQMC] Failed to save player %s (uuid='%s') to database -- PlayerNotLoadedException", player.getName(), id.toString()));
			evt.setQuitMessage(null);
			return;
		}
			
		evt.setQuitMessage(ChatColor.RED + "-" + ChatColor.GRAY + evt.getPlayer().getName());
		
		
	}
	
	//TODO Refactor
	public static boolean isRegistered(UUID id, Connection con) {
	
		try {
			
			String query = "SELECT * FROM skills WHERE uuid='$UUID';";
			query = query.replace("$UUID", id.toString());
			
			ResultSet set = con.createStatement().executeQuery(query);
			while(set.next()) {
				return true;
			}
			
			return false;
			
		} catch(Exception exc) {
			
			exc.printStackTrace();
			return true;
			
		}
		
	}

	//TODO: Make asyc
	public static void saveToDatabase(UUID id, Connection conn) throws SQLException, PlayerNotLoadedException {
		
			Statement statement = conn.createStatement();
			PlayerData dat = getPlayerData(id);
			
			if(dat == null) throw new PlayerNotLoadedException(Bukkit.getPlayer(id));
			
			Player player = dat.getPlayer();
			
			/*ERASE*/
			String query = String.format("DELETE FROM flags WHERE uuid='%s';", id.toString());
			statement.executeUpdate(query);
			
			query = String.format("DELETE FROM inventory WHERE uuid='%s';", id.toString());
			statement.executeUpdate(query);
			
			query = String.format("DELETE FROM settings WHERE uuid='%s';", id.toString());
		
			statement.executeUpdate(query);
			
			query = String.format("DELETE FROM skills WHERE uuid='%s';", id.toString());
			statement.executeUpdate(query);
			
			query = String.format("DELETE FROM vanilladata WHERE uuid='%s';", id.toString());
			statement.executeUpdate(query);
			
			query = String.format("DELETE FROM playerdata WHERE uuid='%s';", id.toString());
			statement.executeUpdate(query);
			
			query = String.format("DELETE FROM bank WHERE uuid='%s';", id.toString());
			statement.executeUpdate(query);
			
			/*REWRITE*/
			
			/*Flags*/
			for(String f: dat.getFlags()) {
				statement.executeUpdate("INSERT IGNORE INTO flags(uuid, flag) VALUES ('$UUID', '$FLAG');"
						.replace("$UUID", id.toString())
						.replace("$FLAG", f));
			}
			
			
			
			/*Inventory*/
			query = String.format("DELETE FROM inventory WHERE uuid='%s';", id.toString());
			statement.executeUpdate(query);
			
			Inventory inv = player.getInventory();			
			CustomItem first = null;
			
			query = "INSERT INTO inventory (uuid, id, slot, amount) values";
			for(int i = 0; i <= 40; i++) {
				ItemStack stack = inv.getItem(i);
				if(stack == null) continue;
				CustomItem ci = CustomItem.getCustomItem(stack);
				if(ci == null) continue;
				if(first == null) first = ci;
				query += String.format("('%s', '%s', '%s', '%s'),", id.toString(), ci.getID(), i, CustomItem.getCount(stack));
			}
			query = query.substring(0, query.length() - 1);
			query += ";";
			
			if(first != null) statement.executeUpdate(query);
			
			
			/*Settings*/
			
			query = "INSERT IGNORE INTO settings(uuid, spellbook, combatmode, hud, music, xpbars) VALUES('$UUID', '$SPELLBOOK', '$COMBATMODE', $HUD, $MUSIC, $XPBARS);";
					query = query.replace("$UUID", dat.getUUID().toString());
					query = query.replace("$SPELLBOOK", dat.getSpellbook().getIdentifier());
					query = query.replace("$COMBATMODE", dat.getCombatMode().toString().toLowerCase());
					query = query.replace("$HUD", (dat.getHud() ? 1 : 0) + "");
					query = query.replace("$MUSIC", (dat.getMusic() ? 1 : 0) + "");
					query = query.replace("$XPBARS", (dat.showXPBars() ? 1 : 0) + "");
					
			statement.executeUpdate(query);
			
			/*Skills*/
			query = "INSERT IGNORE INTO skills(uuid, mining, smithing, woodcutting, firemaking, fishing, cooking, strength, ranged, witchcraft, defense, hitpoints, larceny) VALUES('$UUID', $MINING, $SMITHING, $WOODCUTTING, $FIREMAKING, $FISHING, $COOKING, $STRENGTH, $RANGED, $WITCHCRAFT, $DEFENSE, $HITPOINTS, $LARCENY);";
			for(Skill s: Skill.getSkills()) {
				String st = "$" + s.getName().toUpperCase();
				query = query.replace(st, dat.getXP(s) + "");
			}
			query = query.replace("$UUID", id.toString());
			statement.executeUpdate(query);
			
			
			
			/*VanillaData*/
			query = "INSERT IGNORE INTO vanilladata(uuid, x, y, z, pitch, yaw, health, foodlevel, air, fireticks, falldistance, world) VALUES('$UUID', $LOC_X, $LOC_Y, $LOC_Z, $PITCH, $YAW, $HEALTH, $FOODLEVEL, $AIR, $FIRETICKS, $FALLDISTANCE, '$WORLD');"
					.replace("$UUID", id.toString())
					.replace("$WORLD", player.getWorld().getName())
					.replace("$LOC_X", player.getLocation().getX() + "")
					.replace("$LOC_Y", player.getLocation().getY() + "")
					.replace("$LOC_Z", player.getLocation().getZ() + "")
					.replace("$YAW", player.getLocation().getYaw() + "")
					.replace("$PITCH", player.getLocation().getPitch() + "")
					.replace("$HEALTH", player.getHealth() + "")
					.replace("$FOODLEVEL", player.getFoodLevel() + "")
					.replace("$AIR", player.getRemainingAir() + "")
					.replace("$FIRETICKS", player.getFireTicks() + "")
					.replace("$FALLDISTANCE", player.getFallDistance() + "");
			
			statement.executeUpdate(query);
			
			/*'playerdata' table in MySQL db -- misc account info*/
			query = String.format("INSERT IGNORE INTO playerdata(uuid, balance) VALUES('%s', '%s');", dat.getUUID().toString(), dat.getBalance());
			statement.executeUpdate(query);
			
			/*Bank*/
			query = "INSERT INTO bank(uuid, district, id, amount, slot) VALUES";
			String appended = "";
			
			for(BankDistrict dist: BankDistrict.values()) {
				for(int i = 0; i <= BankAccount.MAX_SLOTS - 1; i++) {
					BankEntry entry = dat.getBankAccount().getBankEntry(dist, i);
					if(entry == null) continue;
					appended += String.format("('%s', '%s', '%s', '%s', '%s'),", dat.getUUID().toString(), dist.toString().toLowerCase(), entry.getCustomItem().getID(), entry.getAmount(), entry.getSlot());
				}
			}
			
			if(appended != "") {
				query += appended;
				query = query.substring(0, query.length() - 1);
				query += ";";
				statement.executeUpdate(query);
			}
			
		
		
	}
	
	public static PlayerData getPlayerData(UUID id) {
		if(players.containsKey(id)) return players.get(id);
		return null;
	}
	
	public static PlayerData getPlayerData(Player player) {
		return getPlayerData(player.getUniqueId());
	}
	
	public void run() {
		for(PlayerData dat: getOnlinePlayers()) {
			dat.updateScoreboard();
			dat.updateXPBars();
		}
	}
	
	public static Collection<PlayerData> getOnlinePlayers(){
		return players.values();
	}
	
	public static PlayerData fromDatabase(UUID id, Connection conn) {

		try {
			PlayerData dat = new PlayerData(id);
			Statement statement = conn.createStatement();
			ResultSet set;
			
			/*Flags*/
			String query = String.format("SELECT * FROM flags WHERE uuid='%s';", id.toString());
		
			List<String> flags = new ArrayList<String>();
			set = statement.executeQuery(query);
			while(set.next()) {
				flags.add(set.getString("flag"));
			}
			dat.setFlags(flags);
			
			/*Inventory*/
			query = String.format("SELECT * FROM inventory WHERE uuid='%s';", id.toString());
			set = statement.executeQuery(query);
			Map<Integer, ItemStack> items = new ConcurrentHashMap<Integer, ItemStack>();
			while(set.next()) {
				CustomItem ci = CustomItem.getCustomItem(set.getInt("id"));
				int amount = set.getInt("amount");
				int slot = set.getInt("slot");
				ItemStack stack = ci.getItemStack(amount);
				items.put(slot, stack);
			}
			dat.setItems(items);
			
			/*Settings*/
			query = String.format("SELECT * FROM settings WHERE uuid='%s';", id.toString());
			set = statement.executeQuery(query);
			while(set.next()) {
				dat.setHud(set.getBoolean("hud"));
				dat.setMusic(set.getBoolean("music"));
				dat.setCombatMode(CombatMode.fromString(set.getString("combatmode")));
				dat.setShowXPBars(set.getBoolean("xpbars"));
				Spellbook book = Spellbook.fromString(set.getString("spellbook"));
				if(book != null) dat.setSpellbook(book);
				break;
			}
			
			/*Skills*/
			query = String.format("SELECT * FROM skills WHERE uuid='%s';", id.toString());
			set = statement.executeQuery(query);
			while(set.next()) {
				for(Skill s: Skill.getSkills()) {
					dat.setXP(s, set.getDouble(s.getName().toLowerCase()));
				}
				break;
			}
			
			/*Vanilla Data*/
			VanillaData vanillaData = new VanillaData();
			query = String.format( "SELECT * FROM vanilladata WHERE uuid='%s';", id.toString());
			set = statement.executeQuery(query);
			while(set.next()) {
				vanillaData.setX(set.getDouble("x"));
				vanillaData.setY(set.getDouble("y"));
				vanillaData.setZ(set.getDouble("z"));
				vanillaData.setPitch(set.getFloat("pitch"));
				vanillaData.setYaw(set.getFloat("yaw"));
				vanillaData.setWorld(Bukkit.getWorld(set.getString("world")));
				vanillaData.setHealth(set.getDouble("health"));
				vanillaData.setFoodLevel(set.getInt("foodlevel"));
				vanillaData.setAir(set.getInt("air"));
				vanillaData.setFireTicks(set.getInt("fireticks"));
				vanillaData.setFallDistance(set.getFloat("falldistance"));
				dat.setVanillaData(vanillaData);
			}
			
			/*'playerdata' table in MySQL db -- misc account info*/
			
			query = String.format("SELECT * FROM playerdata WHERE uuid='%s';", id.toString());
			set = statement.executeQuery(query);
			while(set.next()) {
				dat.setBalance(set.getInt("balance"));
			}
		
			
			/*Bank*/
			query = String.format("SELECT * FROM bank WHERE uuid='%s';", id.toString());
			set = statement.executeQuery(query);
			while(set.next()) {
				
				String districtString = set.getString("district");
				for(BankDistrict d: BankDistrict.values()) {
					if(d.toString().equalsIgnoreCase(districtString)) {
						dat.getBankAccount().depositItem(d, CustomItem.getCustomItem(set.getInt("id")).getItemStack(set.getInt("amount")), set.getInt("slot"));
					}
				}
				
			}
			
			return dat;
			
		} catch(Exception exc) {
			exc.printStackTrace();
			return null;
		}
		
	}
	
	private static Connection getConnection() throws ClassNotFoundException, SQLException  {
		Connection con = null;
		Class.forName(DRIVER_CLASS);
		con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		return con;
}
	
}
