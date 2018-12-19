package squeeze.theorem.mechanics;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Cooldown implements Runnable {

	/* Static fields */
	private static ArrayList<Cooldown> cooldowns = new ArrayList<Cooldown>();
	private static NumberFormat formatter = new DecimalFormat("#0.0");
	public static Cooldown food = new Cooldown("eating food");
	public static Cooldown magic = new Cooldown("casting a spell");
	public static Cooldown pickpocketing = new Cooldown("picking a pocket");
	
	/* Fields */
	private String message;
	private HashMap<UUID, Integer> players = new HashMap<UUID, Integer>();

	/* Constructors */
	public Cooldown(String message) {
		cooldowns.add(this);
		setMessage(message);
	}

	/* Methods */
	public void addPlayer(UUID id, int ticks) {
		players.put(id, ticks);
	}

	public void removePlayer(UUID id) {
		players.remove(id);
	}

	public int getTicksLeft(UUID id) {
		if (players.containsKey(id))
			return players.get(id);

		return 0;
	}

	public boolean isOnCooldown(UUID id) {
		return getTicksLeft(id) > 0;
	}
	
	public void sendCooldownMessage(UUID id) {
		Bukkit.getPlayer(id).sendMessage(ChatColor.RED + "You need to wait " + formatter.format(getTicksLeft(id) * 0.05) + " seconds before " + message + " again.");
	}
	
	public void sendCooldownMessage(Player player) {
		sendCooldownMessage(player.getUniqueId());
	}

	/* Setters and getters */
	public HashMap<UUID, Integer> getPlayers() {
		return this.players;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/* Runnable */
	@Override
	public void run() {
		for (Cooldown cooldown : getCooldowns()) {
			for (UUID id : cooldown.getPlayers().keySet()) {

				cooldown.getPlayers().put(id, cooldown.getPlayers().get(id) - 1);
				
				if(cooldown.getPlayers().get(id) <= 0) players.remove(id);

			}
		}
	}

	public static ArrayList<Cooldown> getCooldowns() {
		return cooldowns;
	}

}
