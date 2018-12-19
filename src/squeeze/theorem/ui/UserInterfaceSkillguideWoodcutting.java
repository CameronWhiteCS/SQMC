package squeeze.theorem.ui;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import squeeze.theorem.item.CustomItem;
import squeeze.theorem.item.CustomItemAxe;
import squeeze.theorem.skill.woodcutting.Tree;

public class UserInterfaceSkillguideWoodcutting extends MultiPageInterface {

	public UserInterfaceSkillguideWoodcutting(String title, int size) {
		super(title, size);
	
		for(Tree tree: Tree.getTrees()) {
			addComponent(new UIComponent() {

				@Override
				public ItemStack getItemStack(Player player) {
					ItemStack stack = new ItemStack(tree.getMaterial());
					ItemMeta meta = stack.getItemMeta();
					meta.setDisplayName(ChatColor.GOLD + tree.getName());
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(ChatColor.GRAY + "=======");
					lore.addAll(tree.getLevelRequirementLore(player));
					lore.add(ChatColor.GRAY + "=======");
					lore.add(ChatColor.DARK_PURPLE + "XP: " + tree.getXP());
					lore.add(ChatColor.DARK_PURPLE + "Respawn time: " + tree.getRespawnDelay() / 20 + "s");
					lore.add(ChatColor.DARK_PURPLE + "Success rate: " + tree.getSuccessRate() + "%");
					
					
					meta.setLore(lore);
					stack.setItemMeta(meta);
					return stack;
				}

				@Override
				public void onClick(InventoryClickEvent evt) {}
				
			});
		}
		
		for(CustomItem ci: CustomItem.getItems()) {
			if(ci instanceof CustomItemAxe) {
				CustomItemAxe cia = (CustomItemAxe) ci;
				addComponent(new UIComponent() {

					@Override
					public ItemStack getItemStack(Player player) {
						ItemStack stack = cia.getItemStack();
						ItemMeta meta = stack.getItemMeta();
						ArrayList<String> lore = new ArrayList<String>();
						lore.add(ChatColor.GRAY + "=======");
						lore.addAll(cia.getLevelRequirementLore(player));
						lore.add(ChatColor.GRAY + "=======");
						lore.add(ChatColor.DARK_PURPLE + "Precision: " + cia.getSuccessRate());
						lore.add(ChatColor.DARK_PURPLE + "Luck: " + cia.getLuck());
						
						
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
