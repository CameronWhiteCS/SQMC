package squeeze.theorem.ui;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import squeeze.theorem.item.CustomItem;
import squeeze.theorem.item.CustomItemPickaxe;
import squeeze.theorem.skill.Ore;

public class UserInterfaceSkillguideMining extends MultiPageInterface {

	public UserInterfaceSkillguideMining(String title, int size) {
		super(title, size);
	
		for(Ore ore: Ore.getOres()) {
			addComponent(new UIComponent() {

				@Override
				public ItemStack getItemStack(Player player) {
					ItemStack stack = new ItemStack(ore.getMaterial());
					ItemMeta meta = stack.getItemMeta();
					meta.setDisplayName(ChatColor.GOLD + ore.getName());
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(ChatColor.GRAY + "=======");
					lore.addAll(ore.getLevelRequirementLore(player));
					lore.add(ChatColor.GRAY + "=======");
					lore.add(ChatColor.DARK_PURPLE + "Tier: " + ore.getTier());
					lore.add(ChatColor.DARK_PURPLE + "XP: " + ore.getXP());
					lore.add(ChatColor.DARK_PURPLE + "Success rate: " + ore.getSuccessRate() + "%");
					lore.add(ChatColor.DARK_PURPLE + "Respawn time: " + ore.getRespawnDelay() / 20 + "s");
					lore.add(ChatColor.DARK_PURPLE + "Vein: " + ore.isVein());
					
					meta.setLore(lore);
					stack.setItemMeta(meta);
					return stack;
				}

				@Override
				public void onClick(InventoryClickEvent evt) {}
				
			});
		}
		
		for(CustomItem ci: CustomItem.getItems()) {
			if(ci instanceof CustomItemPickaxe) {
				CustomItemPickaxe cip = (CustomItemPickaxe) ci;
				addComponent(new UIComponent() {

					@Override
					public ItemStack getItemStack(Player player) {
						ItemStack stack = cip.getItemStack();
						ItemMeta meta = stack.getItemMeta();
						ArrayList<String> lore = new ArrayList<String>();
						lore.add(ChatColor.GRAY + "=======");
						lore.addAll(cip.getLevelRequirementLore(player));
						lore.add(ChatColor.GRAY + "=======");
						lore.add(ChatColor.DARK_PURPLE + "Precision: " + cip.getSuccessRate());
						lore.add(ChatColor.DARK_PURPLE + "Tier: " + cip.getTier());
						lore.add(ChatColor.DARK_PURPLE + "Luck: " + cip.getLuck());
						
						
						meta.setLore(lore);
						stack.setItemMeta(meta);
						return stack;
					}

					@Override
					public void onClick(InventoryClickEvent evt) {}
					
				});
			}
		}
		
	}



}
