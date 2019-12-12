package squeeze.theorem.config;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.json.JSONObject;

import squeeze.theorem.main.SQMC;

public abstract class ConfigManager {

	/*Constants*/
	private static final File CONFIG_FILE = new File(SQMC.getPlugin(SQMC.class).getDataFolder() + File.pathSeparator + "config.json");
	
	/*Fields*/
	private static String mysqlHost = "127.0.0.1";
	private static int mysqlPort = 3306;
	private static String mysqlUser = "root";
	private static String mysqlPassword = "";
	private static String mysqlDatabase = "sqmc";
	
	/*Setters and getters*/
	public static String getMysqlHost() {
		return mysqlHost;
	}

	public static int getMysqlPort() {
		return mysqlPort;
	}

	public static String getMysqlUser() {
		return mysqlUser;
	}

	public static String getMysqlPassword() {
		return mysqlPassword;
	}
	
	public static String getMysqlDatabase() {
		return mysqlDatabase;
	}
	
	/*Methods*/
	public static void loadConfig()  {
	
		try {
			if(CONFIG_FILE.exists()) {
				
				Scanner sc = new Scanner(CONFIG_FILE);
				String s = "";
				while(sc.hasNextLine()) {
					s += sc.nextLine();
				}
				sc.close();
				JSONObject config = new JSONObject(s);
				if(config.has("mysql-host")) mysqlHost = config.getString("mysql-host");
				if(config.has("mysql-port")) mysqlPort = config.getInt("mysql-port");
				if(config.has("mysql-user")) mysqlUser = config.getString("mysql-user");
				if(config.has("mysql-password")) mysqlPassword = config.getString("mysql-password");
				if(config.has("mysql-database")) mysqlDatabase = config.getString("mysql-database");
				
			} else {
				Bukkit.getLogger().log(Level.INFO, "[SQMC] config.json not found, assigning default configuration values.");
			}
		} catch(Exception exc) {
			
			Bukkit.getLogger().log(Level.INFO, "[SQMC] Failed to properly load the configuration file. Assigning default values.");
			exc.printStackTrace();
			
		}
		
	}
	
	public static void saveConfig() {
		
		try {
			if(!CONFIG_FILE.exists()) CONFIG_FILE.createNewFile();
			JSONObject config = new JSONObject();
			config.put("mysql-host", mysqlHost);
			config.put("mysql-port", mysqlPort);
			config.put("mysql-password", mysqlPassword);
			config.put("mysql-user", mysqlUser);
			config.put("mysql-database", mysqlDatabase);
			PrintWriter writer = new PrintWriter(CONFIG_FILE);
			writer.write(config.toString());
			writer.flush();
			writer.close();
		} catch (Exception exc) {
			Bukkit.getLogger().log(Level.SEVERE, "[SQMC] Failed to save the configuration file -- will not override previous config.json");
			exc.printStackTrace();
		}
		
	}
	
}
