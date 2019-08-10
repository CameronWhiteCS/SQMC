package squeeze.theorem.skill.witchcraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.mechanics.Cooldown;
import squeeze.theorem.skill.LevelRequirements;
import squeeze.theorem.skill.Skill;
import squeeze.theorem.ui.UIComponent;

public class Spell implements UIComponent, LevelRequirements {

	/*Static fields*/
	private static ArrayList<Spell> spells = new ArrayList<Spell>();
	
	public static Spell uppkomstTeleport = new SpellTeleport("Uppkomst teleport", "Teleports you to the island of Uppkomst", Material.SPRUCE_SAPLING, new Location(Bukkit.getWorld("world"), 4423, 19, -8697))
			.setCooldown(1800*20)
			.setNotifyCooldown(true)
			.addRequirement(Skill.witchcraft, 1);
	
	
	public static Spell arcanopulse = new SpellProjectile("Arcanopulse", "Channel your mana into a destructive orb", Material.NETHER_STAR)
			.setParticle(Particle.CRIT)
			.setAnimationRadius(0.5)
			.setAccuracy(1)
			.setDamage(1)
			.addRequirement(Skill.witchcraft, 1)
			.setCost(10)
			.setNotifyCooldown(false)
			.setCooldown(10);
	
	public static Spell electricPotential = new SpellZap("Electric Potential", "Electrifying!", Material.NETHER_STAR)
			.setLength(10)
			.setParticle(Particle.CRIT_MAGIC)
			.setDamage(1)
			.setAccuracy(1)
			.addRequirement(Skill.witchcraft, 5)
			.setCost(10);
	
	
	
	public static SpellNone none = new SpellNone();
	
	/*Fields*/
	private String name;
	private String description;
	private Material material;
	private double xp;
	private int cooldown;
	private HashMap<Skill, Integer> requirements = new HashMap<Skill, Integer>();
	private Cooldown cooldownTimer;
	private boolean notifyCooldown = false;
	private int cost;
	private Sound sound = Sound.ENTITY_ILLUSIONER_CAST_SPELL;
	private int volume = 25;
	
	/*Constructors*/
	public Spell(String name, String desc, Material m) {
		setName(name);
		setDescription(desc);
		setMaterial(m);
		cooldownTimer = new Cooldown("casting " + getName());
		spells.add(this);
	}
	
	/*Setters and getters*/
	public String getName() {
		return this.name;
	}

	public Spell setName(String name) {
		this.name = name;
		return this;
	}

	public Material getMaterial() {
		return material;
	}

	public Spell setMaterial(Material material) {
		this.material = material;
		return this;
	}

	public double getXP() {
		return xp;
	}

	public Spell setXP(double xp) {
		this.xp = xp;
		return this;
	}
	
	public String getDescription() {
		return description;
	}

	public Spell setDescription(String description) {
		this.description = description;
		return this;
	}

	public int getCooldown() {
		return cooldown;
	}

	public Spell setCooldown(int cooldown) {
		this.cooldown = cooldown;
		return this;
	}

	public Cooldown getCooldownTimer() {
		return cooldownTimer;
	}

	public Spell setCooldownTimer(Cooldown cooldownTimer) {
		this.cooldownTimer = cooldownTimer;
		return this;
	}
	
	/*UserInterface methods*/
	@Override
	public ItemStack getItemStack(Player player) {
		ItemStack stack = new ItemStack(getMaterial());
		ItemMeta meta = stack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.DARK_PURPLE + getName());
		lore.add(ChatColor.BLUE + getDescription());
		lore.add(ChatColor.GRAY + "=======");
		lore.addAll(this.getLevelRequirementLore(player));
		lore.add(ChatColor.GRAY + "=======");
		lore.add(ChatColor.DARK_PURPLE+ "XP: " + getXP());
		lore.add(ChatColor.DARK_PURPLE+ "Cooldown: " + (getCooldown() / 20) + "s");
		lore.add(ChatColor.DARK_PURPLE+ "Mana cost: " + getCost());
		
		if(this instanceof CombatSpell) {
			CombatSpell cs = (CombatSpell) this;
			lore.add(ChatColor.DARK_PURPLE + "Accuracy: " + cs.getAccuracy());
			lore.add(ChatColor.DARK_PURPLE + "Damage: " + cs.getDamage());
		}
		

		
		meta.setLore(lore);
		stack.setItemMeta(meta);
		return stack;
	}

	//Intended to be overwritten by individual spells, defaults to setting the spell
	public void onClick(InventoryClickEvent evt) {
		Player player = (Player) evt.getWhoClicked();
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
		int slot = player.getInventory().getHeldItemSlot();
		dat.getSessionData().setSpell(slot, this);
		player.sendMessage(ChatColor.GREEN + getName() + " assigned to slot " + (slot + 1) + ".");
		player.closeInventory();
	}

	/*LevelRequirements methods*/
	@Override
	public Map<Skill, Integer> getRequirements() {
		return requirements;
	}
	
	public Spell addRequirement(Skill s, int lvl) {
		requirements.put(s, lvl);
		return this;
	}
	
	/*Object methods*/
	public void cast(Entity entity) {
		entity.getWorld().playSound(entity.getLocation(), getSound(), this.getVolume(), 0);
		if(entity instanceof Player) {
			Cooldown.magic.addPlayer(entity.getUniqueId(), 10);
			cooldownTimer.addPlayer(entity.getUniqueId(), getCooldown());
			DataManager dataManager = DataManager.getInstance();
			PlayerData dat = dataManager.getPlayerData(entity.getUniqueId());
			dat.setMana(dat.getMana() - getCost());
			dat.awardXP(Skill.witchcraft, getXP());
		}
	
	}
	
	public boolean canCast(Entity e) {
		
		if(cooldownTimer.isOnCooldown(e.getUniqueId())) {
			if(e instanceof Player && notifyCooldown == true) cooldownTimer.sendCooldownMessage(e.getUniqueId());
			return false;
		}
		
		if(Cooldown.magic.isOnCooldown(e.getUniqueId())) {
			return false;
		}
		
		if(e instanceof Player) {
			DataManager dataManager = DataManager.getInstance();
			PlayerData dat = dataManager.getPlayerData(e.getUniqueId());
			if(dat.getMana() < getCost()) {
	
				return false;
			}
			
			if(!this.meetsRequirements((Player) e)){
				this.sendInsufficientLevelNotice((Player) e, "cast " + this.getName());
				return false;
			}
		}
		
		return true;
		
	}
	
	
	
	/*Misc*/
	public static ArrayList<Spell> getSpells(){
		return spells;
	}
	
	public static Spell getSpellByName(String name) {
		for(Spell s: getSpells()) {
			if(s.getName().equalsIgnoreCase(name)) {
				return s;
			}
		}
		
		return null;
	}

	public boolean isNotifyCooldown() {
		return notifyCooldown;
	}

	public Spell setNotifyCooldown(boolean notifyCooldown) {
		this.notifyCooldown = notifyCooldown;
		return this;
	}

	public int getCost() {
		return cost;
	}

	public Spell setCost(int cost) {
		this.cost = cost;
		return this;
	}

	public Sound getSound() {
		return sound;
	}

	public Spell setSound(Sound sound) {
		this.sound = sound;
		return this;
	}

	public int getVolume() {
		return volume;
	}

	public Spell setVolume(int volume) {
		this.volume = volume;
		return this;
	}

}
