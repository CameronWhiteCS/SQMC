package squeeze.theorem.skill.conjuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import squeeze.theorem.animation.AnimationCircle;
import squeeze.theorem.combat.AttackStyle;
import squeeze.theorem.combat.CombatStats;
import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.entity.EntityManager;
import squeeze.theorem.entity.SQMCEntity;
import squeeze.theorem.main.SQMC;
import squeeze.theorem.mechanics.Cooldown;
import squeeze.theorem.skill.LevelRequirements;
import squeeze.theorem.skill.Skill;
import squeeze.theorem.ui.UIComponent;

public class Familiar extends SQMCEntity implements LevelRequirements, UIComponent, CombatStats {
	
	protected static AnimationCircle summoningAnimation = new AnimationCircle(Particle.CLOUD, 1, Math.PI / 8);
	protected static AnimationCircle despawnAnimation = new AnimationCircle(Particle.CLOUD, 1, Math.PI / 8);
	
	private Map<Skill, Integer> skillRequirements = new HashMap<Skill, Integer>();
	private double summonXp;
	private Material material;
	private Cooldown cooldown = new Cooldown("summoning that creature");
	private int cooldownTicks;
	private int lifespan;
	private double health;
	
	public Familiar(String name, EntityType entityType) {
		super(name, entityType);
	}
	
	@Override
	public ItemStack getItemStack(Player player) {
		ItemStack output = new ItemStack(material);
		ItemMeta meta = output.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + ChatColor.stripColor(this.getDisplayName()));
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "------");
		lore.addAll(this.getLevelRequirementLore(player));
		lore.add(ChatColor.GRAY + "------");
		lore.add(ChatColor.DARK_PURPLE + "XP: " + summonXp);
		lore.add(ChatColor.DARK_PURPLE + "Cooldown: " + (double) cooldownTicks / 20D + "s");
		lore.add(ChatColor.DARK_PURPLE + "Lifespan: " + (double) lifespan / 20D + "s");
		lore.add(ChatColor.DARK_PURPLE + "Health: " + health);
		meta.setLore(lore);
		output.setItemMeta(meta);
		return output;
	}
	
	public void summon(Player player) {
		LivingEntity le = EntityManager.getInstance().spawn(this, player.getLocation());
		if(le instanceof Tameable) {
			Tameable tameable = (Tameable) le;
			tameable.setOwner(player);
		}
		onSummon(player, le);
		despawn(le, player);
	}
	
	/*Intended to be overwritten*/
	public void onSummon(Player player, LivingEntity le) {
		
	}
	
	private void despawn(LivingEntity e, Player player) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(SQMC.getPlugin(SQMC.class), new Runnable() {
			@Override
			public void run() {
				despawnAnimation.animate(e);
				e.remove();
			}}, lifespan);
	}
	
	@Override
	public void onSpawn(LivingEntity le) {
		World world = le.getWorld();
		double x = le.getLocation().getX();
		double y = le.getLocation().getY() + 0.1;
		double z = le.getLocation().getZ();
		summoningAnimation.animate(new Location(world, x, y, z));
	}
	
	@Override
	public void onClick(InventoryClickEvent evt) {
		Player player = (Player) evt.getWhoClicked();
		PlayerData dat = DataManager.getInstance().getPlayerData(player);
		
		if(!this.meetsRequirements(player)) {
			this.sendInsufficientLevelNotice(player, "to summon that creature");
			return;
		}
		
		if(cooldown.isOnCooldown(player.getUniqueId())) {
			cooldown.sendCooldownMessage(player);
			player.closeInventory();
			return;
		}
		
		player.closeInventory();
		dat.awardXP(Skill.conjuration, summonXp);
		summon(player);
		player.playSound(player.getLocation(), Sound.ENTITY_EVOKER_PREPARE_SUMMON, 1, 1);
		cooldown.addPlayer(player.getUniqueId(), cooldownTicks);
		
	}

	@Override
	public Map<Skill, Integer> getRequirements() {
		return skillRequirements;
	}
	
	public void addRequirement(Skill s, int level) {
		this.skillRequirements.put(s, level);
	}
	
	public void setSummonXp(double xp) {
		this.summonXp = xp;
	}
	
	public double getSummonXp() {
		return this.summonXp;
	}
	
	public void setMaterial(Material m) {
		this.material = m;
	}
	
	public Material getMaterial() {
		return this.material;
	}
	
	public void setCooldown(int cooldown) {
		this.cooldownTicks = cooldown;
	}
	
	public int getCooldown() {
		return this.cooldownTicks;
	}
	
	public void setLifespan(int lifespan) {
		this.lifespan = lifespan;
	}
	
	public int getLifespan() {
		return this.lifespan;
	}
	
	public void setHealth(double health) {
		this.health = health;
	}
	
	@Override
	public double getHealth() {
		return this.health;
	}

	@Override
	public double getStrength(AttackStyle style) {
		return 0;
	}

	@Override
	public double getDefense(AttackStyle style) {
		return 0;
	}

	@Override
	public double getAccuracy(AttackStyle style) {
		return 0;
	}
	
}
