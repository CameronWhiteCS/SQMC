package squeeze.theorem.config;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.json.JSONObject;

import squeeze.theorem.main.SQMC;

public class ConfigManager {

	/*Constants*/
	private static final File CONFIG_FILE = new File(SQMC.getPlugin(SQMC.class).getDataFolder() + "/config.json");
	private static ConfigManager instance = new ConfigManager();
	
	/*Fields*/
	private String mysqlHost = "127.0.0.1";
	private int mysqlPort = 3306;
	private String mysqlUser = "root";
	private String mysqlPassword = "";
	private String mysqlDatabase = "sqmc";
	private int craftingDelay = 100;
	boolean save = false;
	
	private ConfigManager() {
		
	}
	
	/*Methods*/
	public void loadConfig()  {
	
		try {
			if(CONFIG_FILE.exists()) {
				
				Scanner sc = new Scanner(CONFIG_FILE);
				String s = "";
				while(sc.hasNextLine()) {
					s += sc.nextLine();
				}
				sc.close();
				
				JSONObject config = new JSONObject(s);
				
				if(config.has("mysql-host")) {
					mysqlHost = config.getString("mysql-host");	
				} else {
					save = true;
				}
				
				if(config.has("mysql-port")) {
					mysqlPort = config.getInt("mysql-port");
				} else {
					save = true;
				}
				
				if(config.has("mysql-user")) {
					mysqlUser = config.getString("mysql-user");	
				} else {
					save = true;
				}
				
				if(config.has("mysql-password")) {
					mysqlPassword = config.getString("mysql-password");
				} else {
					save = true;
				}
				
				if(config.has("mysql-database")) {
					mysqlDatabase = config.getString("mysql-database");
				} else {
					save = true;
				}
				
				if(config.has("crafting-delay")) {
					craftingDelay = config.getInt("crafting-delay");
				} else {
					save = true;
				}
				
			} else {
				Bukkit.getLogger().log(Level.INFO, "[SQMC] config.json not found, assigning default configuration values.");
				save = true;
			}
		} catch(Exception exc) {
			
			Bukkit.getLogger().log(Level.INFO, "[SQMC] Failed to properly load the configuration file. Assigning default values.");
			exc.printStackTrace();
			
		}
		
	}
	
	public void saveConfig() {
		if(save) {
			try {
				if(!CONFIG_FILE.exists()) CONFIG_FILE.createNewFile();
				JSONObject config = new JSONObject();
				config.put("mysql-host", mysqlHost);
				config.put("mysql-port", mysqlPort);
				config.put("mysql-password", mysqlPassword);
				config.put("mysql-user", mysqlUser);
				config.put("mysql-database", mysqlDatabase);
				config.put("crafting-delay", "" + craftingDelay);
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
	
	/*Setters and getters*/
	public int getCraftingDelay() {
		return this.craftingDelay;
	}
	
	public String getMysqlHost() {
		return mysqlHost;
	}

	public int getMysqlPort() {
		return mysqlPort;
	}

	public String getMysqlUser() {
		return mysqlUser;
	}

	public String getMysqlPassword() {
		return mysqlPassword;
	}
	
	public String getMysqlDatabase() {
		return mysqlDatabase;
	}
	
	public static ConfigManager getInstance() {
		return instance;
	}
	
}
