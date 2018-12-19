package squeeze.theorem.skill.fishing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.event.SQMCPlayerFishEvent;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.skill.Skill;
import squeeze.theorem.ui.UserInterface;

//TODO Clean up some methods
public class SkillFishing extends Skill implements Listener {

	public SkillFishing () {
		setMaterial(Material.FISHING_ROD);
		setName("Fishing");
		
	}
	
	@Override
	public UserInterface getSkillGuide(Player player) {
		return UserInterface.skillguideFishing;
	}
	
	@EventHandler
	public void onFish(PlayerFishEvent evt) {
		
		if(!evt.isCancelled() && evt.getState() == State.CAUGHT_FISH) {
			
			Player player = evt.getPlayer();
			
			//Bait check
			
			//TODO: Offhand support
			if(!player.getInventory().containsAtLeast(CustomItem.FEATHER.getItemStack(), 1)) {
				
				player.sendMessage(ChatColor.RED + "You need feathers to go fly fishing.");
				evt.setCancelled(true);
				return;
			}
			
			//Fishing rod check
			
			if(player.getInventory().getItemInMainHand() == null) {
				
				player.sendMessage(ChatColor.RED + "You need a fishing rod to go fly fishing.");
				evt.setCancelled(true);
				return;
			}
			
			if(player.getInventory().getItemInMainHand().getType() == Material.AIR) {
				player.sendMessage(ChatColor.RED + "You need a fishing rod to go fly fishing.");
				evt.setCancelled(true);
				return;
			}
			
			if(CustomItem.getCustomItem(player.getInventory().getItemInMainHand()) != CustomItem.FISHING_ROD) {
				player.sendMessage(ChatColor.RED + "You need a fishing rod to go fly fishing");
				evt.setCancelled(true);
				return;
			}
			
			//Fire event
			
			evt.setExpToDrop(0);
			
			Random RNG = new Random();
			
			List<SQMCEntityFish> possibleFish = new ArrayList<SQMCEntityFish>();
			for(SQMCEntityFish f: SQMCEntityFish.getFish()) {
				if(f.meetsRequirements(player)) possibleFish.add(f);
			}
			
			SQMCEntityFish fish = possibleFish.get(RNG.nextInt(possibleFish.size()));
			Bukkit.getPluginManager().callEvent(new SQMCPlayerFishEvent(evt, fish));
			
		}
		
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onCustomFish(SQMCPlayerFishEvent evt) {
		
		Item item = (Item) evt.getVanillaEvent().getCaught();
		Player player = evt.getPlayer();
		PlayerData dat = DataManager.getPlayerData(player.getUniqueId());
		
		item.setItemStack(CustomItem.KELP.getItemStack());

		SQMCEntityFish fish = evt.getSQMCEntityFish();
		LivingEntity entity = (LivingEntity) fish.spawn(item.getLocation());
		entity.setHealth(0.5);
		entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 3600, 255));
		item.addPassenger(entity);
		dat.awardXP(Skill.fishing, evt.getXP());
		
		for(ItemStack stack: player.getInventory().getContents()) {
			if(stack == null) continue;
			if(stack.getType() == Material.AIR) continue;
			if(stack.equals(CustomItem.FEATHER.getItemStack(stack.getAmount()))) {
				stack.setAmount(stack.getAmount() - 1);
				break;
			}
		}
		
		
		
		
	}
	
}