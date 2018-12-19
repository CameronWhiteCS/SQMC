package squeeze.theorem.ui;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.skill.Skill;

public class UIComponentSkillIcon implements UIComponent {

	private static NumberFormat formatter = new DecimalFormat("#0.0");
	
	private Skill skill;
	
	public UIComponentSkillIcon(Skill skill) {
		setSkill(skill);
	}
	
	@Override
	public ItemStack getItemStack(Player player) {
		ItemStack output = new ItemStack(skill.getMaterial());
		ItemMeta meta = output.getItemMeta();
		PlayerData dat = DataManager.getPlayerData(player.getUniqueId());
		
		
		ArrayList<String> lore = new ArrayList<String>();
		meta.setDisplayName(ChatColor.GOLD + skill.getName());
		lore.add(ChatColor.GRAY + "-------");
		lore.add(ChatColor.GREEN + "Level: " + dat.getLevel(getSkill()));
		lore.add(ChatColor.GRAY + "-------");
		lore.add(ChatColor.DARK_PURPLE + "XP: " + formatter.format(dat.getXP(getSkill())));
		
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.setLore(lore);
		output.setItemMeta(meta);
		return output;
	}

	@Override
	public void onClick(InventoryClickEvent evt) {
		String name = ChatColor.stripColor(evt.getCurrentItem().getItemMeta().getDisplayName()).toLowerCase();
		Player player = (Player) evt.getWhoClicked();
		for(Skill s: Skill.getSkills()) {
			if(name.equals(ChatColor.stripColor(s.getName().toLowerCase()))) {
				s.getSkillGuide(player).open(player);
			}
		}
		
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	
}
