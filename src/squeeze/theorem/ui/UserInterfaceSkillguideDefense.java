package squeeze.theorem.ui;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import squeeze.theorem.item.CombatItem;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.skill.Skill;

public class UserInterfaceSkillguideDefense extends ChestInterface {

	public UserInterfaceSkillguideDefense(String title) {
		super(title);
		for(CustomItem ci: CustomItem.getItems()) {
			if(ci instanceof CombatItem) {
				CombatItem cmbt = (CombatItem) ci;
				if(cmbt.getLevelRequired(Skill.defense) > 0) {
					
					addComponent(new UIComponent() {

						@Override
						public ItemStack getItemStack(Player player) {
							ItemStack stack = cmbt.getItemStack();
							ItemMeta meta = stack.getItemMeta();
							ArrayList<String> lore = new ArrayList<String>();
							lore.add(ChatColor.GRAY + "=======");
							lore.add(cmbt.getLevelRequirementLore(player, Skill.defense));
							
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

}
