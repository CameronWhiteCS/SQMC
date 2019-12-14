package squeeze.theorem.skill.firemaking;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mule;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import squeeze.theorem.entity.EntityManager;
import squeeze.theorem.entity.SQMCEntity;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.item.Lootable;
import squeeze.theorem.skill.LevelRequirements;
import squeeze.theorem.skill.Skill;

public class SQMCEntityFire extends SQMCEntity implements Lootable, LevelRequirements {
	
	/*Fields*/
	private CustomItem customItem;
	private LinkedHashMap<Skill, Integer> requirements = new LinkedHashMap<Skill, Integer>();
	private List<ItemStack> loot = new ArrayList<ItemStack>();
	private double XP;
	private long burnTime;
	private double healthPerMinute;
	private double hungerPerMinute;

	public SQMCEntityFire(String name, CustomItem customItem, int levelRequired, double Xp, long burnTime, double health, double hunger) {
		super(name, EntityType.MULE);
		EntityManager.getInstance().getFires().add(this);
		requirements.put(Skill.firemaking, levelRequired);
		setXP(Xp);
		setBurnTime(burnTime);
		setHealthPerMinute(health);
		setHungerPerMinute(hunger);
		setCustomItem(customItem);
		loot.add(CustomItem.ASHES.getItemStack());

	}

	@Override
	public void onSpawn(LivingEntity entity) {

		entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 3600, 1));
		entity.setTicksLived(1);
		entity.setAI(false);
		entity.setSilent(true);

		Mule mule = (Mule) entity;
		mule.setInvulnerable(true);
		mule.setAdult();
		mule.setFireTicks(100000000);
		
	}

	public CustomItem getCustomItem() {
		return customItem;
	}

	public void setCustomItem(CustomItem customItem) {
		this.customItem = customItem;
	}

	public double getXP() {
		return XP;
	}

	public void setXP(double xP) {
		XP = xP;
	}

	public long getBurnTime() {
		return burnTime;
	}

	public void setBurnTime(long burnTime) {
		this.burnTime = burnTime;
	}

	public double getHealthPerMinute() {
		return healthPerMinute;
	}

	public void setHealthPerMinute(double healthPerMinute) {
		this.healthPerMinute = healthPerMinute;
	}

	public double getHungerPerMinute() {
		return hungerPerMinute;
	}

	public void setHungerPerMinute(double hungerPerMinute) {
		this.hungerPerMinute = hungerPerMinute;
	}

	public ItemStack getSkillGuideComponent(Player player) {
		ItemStack stack = new ItemStack(getCustomItem().getItemStack().getType());
		ItemMeta meta = stack.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.GOLD + getCustomItem().getName());
		lore.add(ChatColor.GRAY + "-------");

		lore.addAll(this.getLevelRequirementLore(player));
		
		lore.add(ChatColor.GRAY + "-------");
		lore.add(ChatColor.DARK_PURPLE + "XP: " + getXP());
		lore.add(ChatColor.DARK_PURPLE + "Duration: " + getBurnTime() / 20 + "s");
		lore.add(ChatColor.DARK_PURPLE + "HP/min: " + getHealthPerMinute());
		lore.add(ChatColor.DARK_PURPLE + "Hunger/min: " + getHungerPerMinute());

		meta.setLore(lore);
		stack.setItemMeta(meta);

		return stack;

		
		
	}

	@Override
	public Map<Skill, Integer> getRequirements(){
		return requirements;
	}

	@Override
	public List<ItemStack> getLoot(Player player) {
		return this.loot;
	}
	
}
