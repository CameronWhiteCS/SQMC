package squeeze.theorem.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import squeeze.theorem.combat.CombatManager;
import squeeze.theorem.command.SQMCCommand;
import squeeze.theorem.config.ConfigManager;
import squeeze.theorem.data.DataManager;
import squeeze.theorem.entity.EntityManager;
import squeeze.theorem.item.CombatItem;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.mechanics.Cooldown;
import squeeze.theorem.mechanics.DeathMechanics;
import squeeze.theorem.mechanics.NoStack;
import squeeze.theorem.mechanics.TimeMechanics;
import squeeze.theorem.quest.Quest;
import squeeze.theorem.recipe.RecipeManager;
import squeeze.theorem.recipe.SQMCRecipe;
import squeeze.theorem.region.Region;
import squeeze.theorem.skill.Skill;
import squeeze.theorem.ui.InterfaceManager;

public class SQMC extends JavaPlugin {
	
	/*Static fields*/
	public static final String VERSION = "Indev 0.16d";
	
	@Override
	public void onEnable() {
		ConfigManager.loadConfig();
		Region.onEnable();
		DataManager dataManager = DataManager.getInstance();
		dataManager.loadAllPlayers();
		registerSkills();
		registerQuests();
		registerMisc();
		enforceVanillaConfiguration();
		despawnMobs();
	}

	@Override
	public void onDisable() {
		
		DataManager dataManager = DataManager.getInstance();
		dataManager.saveAllPlayers();
		ConfigManager.saveConfig();
		executePendingTasks();
		
	}

	private void executePendingTasks() {
		for(BukkitTask t: Bukkit.getScheduler().getPendingTasks()) {
			if(t.getOwner() == this) {
				((Runnable) t).run();
				t.cancel();
			}
		}
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		for(SQMCCommand command: SQMCCommand.getCommands()) {
			if(command.getLabel().equalsIgnoreCase(cmd.getLabel())) {
				
				if(command.getPermission() != null) {
					if(!sender.hasPermission(command.getPermission())) {
						
						sender.sendMessage(ChatColor.RED + "I'm sorry, but you don't have permission to use that command.");
						return true;
					}
				}

				command.onCommand(sender, args);
			}
		}
		
		return true;

	}
	
	private void registerSkills() {
		for(Skill s: Skill.getSkills()) {
			if(s instanceof Listener) Bukkit.getPluginManager().registerEvents((Listener) s, this);
		}
		
		Bukkit.getPluginManager().registerEvents(EntityManager.getInstance(), this);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, Skill.firemaking, 0, 20);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, Skill.cooking, 0, 20);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, Skill.hitpoints, 0, 1);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, Skill.witchcraft, 0, 2);
	}
	
	private void registerQuests() {
		for(Quest q: Quest.getQuests()) {
			if(q instanceof Listener) Bukkit.getPluginManager().registerEvents(((Listener) q), this);
		}
	}
	
	private void enforceVanillaConfiguration() {
		
		Bukkit.clearRecipes();
		
		for(World w: Bukkit.getWorlds()) {
			w.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
			w.setGameRule(GameRule.DO_FIRE_TICK, false);
			w.setGameRule(GameRule.MOB_GRIEFING, false);
			w.setGameRule(GameRule.DO_ENTITY_DROPS, false);
			w.setGameRule(GameRule.DO_MOB_SPAWNING, false);
			w.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
			w.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false);
			w.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
		}	
		
	}
	
	private void registerMisc() {
		
		Bukkit.getPluginManager().registerEvents(DataManager.getInstance(), this);
		Bukkit.getPluginManager().registerEvents(SQMCRecipe.WOODEN_SHOVEL, this);
		Bukkit.getPluginManager().registerEvents(CustomItem.WOODEN_AXE, this); 
		Bukkit.getPluginManager().registerEvents(InterfaceManager.getInstance(), this);
		Bukkit.getPluginManager().registerEvents(CombatManager.getInstance(), this);
		Bukkit.getPluginManager().registerEvents(new DeathMechanics(), this);
		Bukkit.getPluginManager().registerEvents(Region.uppkomst, this);
		Bukkit.getPluginManager().registerEvents(RecipeManager.getInstance(), this);
		Bukkit.getPluginManager().registerEvents(new NoStack(), this);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, DataManager.getInstance(), 0, 1);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, RecipeManager.getInstance(), 0, 100);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, Cooldown.food, 0, 1);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new TimeMechanics(), 0, 20);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, EntityManager.getInstance(), 0, 5);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, CombatManager.getInstance(), 0, 1);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new NoStack(), 0, 1);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, (CombatItem) CustomItem.LEATHER_BOOTS, 0, 1);
	}
	
	private void despawnMobs() {
		for(Chunk c: Bukkit.getWorld("world").getLoadedChunks()) {
			
			Bukkit.getPluginManager().callEvent(new ChunkLoadEvent(c, false) );
			
		}
	}
}