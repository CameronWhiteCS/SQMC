package squeeze.theorem.ui;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import squeeze.theorem.skill.fishing.SQMCEntityFish;

public class UserInterfaceSkillguideFishing extends ChestInterface {

	public UserInterfaceSkillguideFishing(String title) {
		super(title);
		for (SQMCEntityFish fish : SQMCEntityFish.getFish()) {

			addComponent(new UIComponent() {

				@Override
				public ItemStack getItemStack(Player player) {
					ItemStack stack = new ItemStack(fish.getMaterial());
					ItemMeta meta = stack.getItemMeta();
					ArrayList<String> lore = new ArrayList<String>();
					lore.add(ChatColor.GRAY + "=======");
					lore.addAll(fish.getLevelRequirementLore(player));
					lore.add(ChatColor.GRAY + "=======");
					lore.add(ChatColor.DARK_PURPLE + "XP: " + fish.getXP());

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
