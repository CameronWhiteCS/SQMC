package squeeze.theorem.skill;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public interface Resource extends LevelRequirements {
	
	public double getXP();
	
	public ItemStack getDrop();

	public String getName();

	public double getSuccessRate();
	
	public Material getMaterial();
	
	public default ItemStack getSkillGuideComponent(Player player, Skill skill) {
		ItemStack s = new ItemStack(getMaterial());
		s.setAmount(1);
		ItemMeta meta = s.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + getName());
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "-------");
	
		lore.addAll(getLevelRequirementLore(player));
		
		lore.add(ChatColor.GRAY + "-------");
	
		lore.add(ChatColor.DARK_PURPLE + "XP: " + getXP());
		meta.setLore(lore);
		s.setItemMeta(meta);
		return s;
	}
	
}
