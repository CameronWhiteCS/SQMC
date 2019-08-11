package squeeze.theorem.ui;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import squeeze.theorem.entity.SQMCEntity;
import squeeze.theorem.skill.SQMCEntityFire;

public class UserInterfaceSkillguideFiremaking extends ChestInterface {

	public UserInterfaceSkillguideFiremaking(String title) {
		super(title);
		
		for (SQMCEntityFire fire : SQMCEntity.getFires()) {

			addComponent(new UIComponent() {

				@Override
				public ItemStack getItemStack(Player player) {

					ItemStack stack = new ItemStack(fire.getCustomItem().getItemStack());
					ItemMeta meta = stack.getItemMeta();
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(ChatColor.GRAY + "=======");
					lore.addAll(fire.getLevelRequirementLore(player));
					lore.add(ChatColor.GRAY + "=======");

					lore.add(ChatColor.DARK_PURPLE + "XP: " + fire.getXP());
					lore.add(ChatColor.DARK_PURPLE + "Duration: " + fire.getBurnTime() / 20 + "s");
					lore.add(ChatColor.DARK_PURPLE + "HP/Min: " + fire.getHealthPerMinute());
					lore.add(ChatColor.DARK_PURPLE + "Stamina/Min: " + fire.getHungerPerMinute());

					meta.setLore(lore);
					stack.setItemMeta(meta);
					return stack;
				}

				@Override
				public void onClick(InventoryClickEvent evt) {
					
				}

			});

		}

	}

}
