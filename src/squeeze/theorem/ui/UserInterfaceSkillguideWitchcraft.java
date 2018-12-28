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
import squeeze.theorem.skill.witchcraft.Spell;

public class UserInterfaceSkillguideWitchcraft extends MultiPageInterface {

	public UserInterfaceSkillguideWitchcraft(String title, int size) {
		super(title, size);
		//Equipment
		for(CustomItem ci: CustomItem.getItems()) {
			if(ci instanceof CombatItem) {
				CombatItem cmbt = (CombatItem) ci;
				if(cmbt.getLevelRequired(Skill.witchcraft) > 0) {
					
					addComponent(new UIComponent() {

						@Override
						public ItemStack getItemStack(Player player) {
							ItemStack stack = cmbt.getItemStack();
							ItemMeta meta = stack.getItemMeta();
							ArrayList<String> lore = new ArrayList<String>();
							
							lore.addAll(cmbt.getLevelRequirementLore(player));
							lore.add(ChatColor.GRAY + "=======");
							lore.addAll(cmbt.getCombatItemLore());
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
		//Spells
		for(Spell spell: Spell.getSpells()) {
			addComponent(new UIComponent() {

				@Override
				public ItemStack getItemStack(Player player) {
					return spell.getItemStack(player);
				}

				@Override
				public void onClick(InventoryClickEvent evt) {}
				
			});
		}
	}

}
