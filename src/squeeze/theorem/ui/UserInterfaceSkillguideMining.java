package squeeze.theorem.ui;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import squeeze.theorem.item.CustomItem;
import squeeze.theorem.item.CustomItemPickaxe;
import squeeze.theorem.skill.mining.Ore;

public class UserInterfaceSkillguideMining extends ChestInterface {

	public UserInterfaceSkillguideMining(String title) {
		super(title);
	
		for(Ore ore: Ore.getOres()) {
			addComponent(ore);
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
