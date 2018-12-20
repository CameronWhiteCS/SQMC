package squeeze.theorem.recipe;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.JSONArray;
import org.json.JSONObject;

import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.data.SessionData;
import squeeze.theorem.event.SQMCRecipeEvent;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.skill.LevelRequirements;
import squeeze.theorem.skill.Skill;
import squeeze.theorem.skill.cooking.recipe.RecipeCookedCod;
import squeeze.theorem.skill.cooking.recipe.RecipeCookedPufferfish;
import squeeze.theorem.skill.cooking.recipe.RecipeCookedSalmon;
import squeeze.theorem.skill.cooking.recipe.RecipeCookedSquid;
import squeeze.theorem.skill.cooking.recipe.RecipeCookedTropicalFish;
import squeeze.theorem.ui.UserInterface;

public class SQMCRecipe implements Listener, LevelRequirements {

	/* Static fields */

	// The following ArrayList must be initialized prior to any instance of
	// CustomRecipe
	private static ArrayList<SQMCRecipe> recipes = new ArrayList<SQMCRecipe>();
	
	private static HashMap<CustomItem, ArrayList<CustomItem>> substitutes = new HashMap<CustomItem, ArrayList<CustomItem>>(){
		private static final long serialVersionUID = 1742301123063001029L;

	{
		put(CustomItem.OAK_PLANKS, new ArrayList<CustomItem>() {
			private static final long serialVersionUID = -3000508525723108403L;
		{
			add(CustomItem.BIRCH_PLANKS);
			add(CustomItem.SPRUCE_PLANKS);
			add(CustomItem.JUNGLE_PLANKS);
			add(CustomItem.ACACIA_PLANKS);
			add(CustomItem.DARK_OAK_PLANKS);
		}});
		
		put(CustomItem.OAK_LOG, new ArrayList<CustomItem>() {
			private static final long serialVersionUID = -3000508525723108403L;
		{
			add(CustomItem.BIRCH_LOG);
			add(CustomItem.SPRUCE_LOG);
			add(CustomItem.JUNGLE_LOG);
			add(CustomItem.ACACIA_LOG);
			add(CustomItem.DARK_OAK_LOG);
		}});
		
	}};

	/*Tier 1 smithing recipes*/
	
	public static final SQMCRecipe WOODEN_PICKAXE = fromFile("wooden-pickaxe");
	
	public static final SQMCRecipe WOODEN_SWORD = new SQMCRecipe()
			.setOutput(CustomItem.WOODEN_SWORD)
			.addRequirement(Skill.smithing, 2)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.OAK_LOG, 2)
			.setRecipeType(RecipeType.SMITHING_ANVIL)
			.setAcceptsSubstitutes(true);
	
	public static SQMCRecipe WOODEN_AXE = new SQMCRecipe()
			.setOutput(CustomItem.WOODEN_AXE)
			.addRequirement(Skill.smithing, 3)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.OAK_LOG, 3)
			.setRecipeType(RecipeType.SMITHING_ANVIL)
			.setAcceptsSubstitutes(true);
	
	public static SQMCRecipe WOODEN_SHOVEL = new SQMCRecipe()
			.setOutput(CustomItem.WOODEN_SHOVEL)
			.addRequirement(Skill.smithing, 4)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.OAK_LOG, 1)
			.setRecipeType(RecipeType.SMITHING_ANVIL)
			.setAcceptsSubstitutes(true);
	
	public static SQMCRecipe WOODEN_HOE = new SQMCRecipe()
			.setOutput(CustomItem.WOODEN_HOE)
			.addRequirement(Skill.smithing, 5)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.OAK_LOG, 2)
			.setRecipeType(RecipeType.SMITHING_ANVIL)
			.setAcceptsSubstitutes(true);
	
	public static SQMCRecipe LEATHER_BOOTS = new SQMCRecipe()
			.setOutput(CustomItem.LEATHER_BOOTS)
			.addRequirement(Skill.smithing, 6)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.LEATHER, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe LEATHER_HELMET = new SQMCRecipe()
			.setOutput(CustomItem.LEATHER_HELMET)
			.addRequirement(Skill.smithing, 7)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.LEATHER, 5)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe LEATHER_LEGGINGS = new SQMCRecipe()
			.setOutput(CustomItem.LEATHER_LEGGINGS)
			.addRequirement(Skill.smithing, 8)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.LEATHER, 7)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe LEATHER_CHESTPLATE = new SQMCRecipe()
			.setOutput(CustomItem.LEATHER_CHESTPLATE)
			.addRequirement(Skill.smithing, 9)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.LEATHER, 8)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	/*Tier 2 smithing recipes*/
	
	public static final SQMCRecipe COBBLESTONE_PICKAXE = new SQMCRecipe()
			.setOutput(CustomItem.COBBLESTONE_PICKAXE)
			.addRequirement(Skill.smithing, 11)
			.setXP(Skill.smithing, 9.0)
			.addInput(CustomItem.COBBLESTONE, 3)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static final SQMCRecipe COBBLESTONE_SWORD = new SQMCRecipe()
			.setOutput(CustomItem.COBBLESTONE_SWORD)
			.addRequirement(Skill.smithing, 12)
			.setXP(Skill.smithing, 6.0)
			.addInput(CustomItem.COBBLESTONE, 2)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe COBBLESTONE_AXE = new SQMCRecipe()
			.setOutput(CustomItem.COBBLESTONE_AXE)
			.addRequirement(Skill.smithing, 13)
			.setXP(Skill.smithing, 9.0)
			.addInput(CustomItem.COBBLESTONE, 3)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe COBBLESTONE_SHOVEL = new SQMCRecipe()
			.setOutput(CustomItem.COBBLESTONE_SHOVEL)
			.addRequirement(Skill.smithing, 14)
			.setXP(Skill.smithing, 3.0)
			.addInput(CustomItem.COBBLESTONE, 1)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe COBBLESTONE_HOE = new SQMCRecipe()
			.setOutput(CustomItem.COBBLESTONE_HOE)
			.addRequirement(Skill.smithing, 15)
			.setXP(Skill.smithing, 6.0)
			.addInput(CustomItem.COBBLESTONE, 2)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe HARD_LEATHER_BOOTS = new SQMCRecipe()
			.setOutput(CustomItem.HARD_LEATHER_BOOTS)
			.addRequirement(Skill.smithing, 16)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.LEATHER, 4)
			.addInput(CustomItem.IRON_NUGGET, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe HARD_LEATHER_HELMET = new SQMCRecipe()
			.setOutput(CustomItem.HARD_LEATHER_HELMET)
			.addRequirement(Skill.smithing, 17)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.LEATHER, 5)
			.addInput(CustomItem.IRON_NUGGET, 5)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe HARD_LEATHER_LEGGINGS = new SQMCRecipe()
			.setOutput(CustomItem.HARD_LEATHER_LEGGINGS)
			.addRequirement(Skill.smithing, 18)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.LEATHER, 7)
			.addInput(CustomItem.IRON_NUGGET, 7)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe HARD_LEATHER_CHESTPLATE = new SQMCRecipe()
			.setOutput(CustomItem.HARD_LEATHER_CHESTPLATE)
			.addRequirement(Skill.smithing, 19)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.LEATHER, 8)
			.addInput(CustomItem.IRON_NUGGET, 8)
			.setRecipeType(RecipeType.SMITHING_ANVIL);

	/*Tier 3 smithing recipes*/
	
	public static final SQMCRecipe STONE_PICKAXE = new SQMCRecipe()
			.setOutput(CustomItem.STONE_PICKAXE)
			.addRequirement(Skill.smithing, 21)
			.setXP(Skill.smithing, 2.0)
			.addInput(CustomItem.STONE, 3)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static final SQMCRecipe STONE_SWORD = new SQMCRecipe()
			.setOutput(CustomItem.STONE_SWORD)
			.addRequirement(Skill.smithing, 22)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.STONE, 2)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe STONE_AXE = new SQMCRecipe()
			.setOutput(CustomItem.STONE_AXE)
			.addRequirement(Skill.smithing, 23)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.STONE, 3)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe STONE_SHOVEL = new SQMCRecipe()
			.setOutput(CustomItem.STONE_SHOVEL)
			.addRequirement(Skill.smithing, 24)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.STONE, 1)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe STONE_HOE = new SQMCRecipe()
			.setOutput(CustomItem.STONE_HOE)
			.addRequirement(Skill.smithing, 25)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.STONE, 2)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe CHAINMAIL_BOOTS = new SQMCRecipe()
			.setOutput(CustomItem.CHAINMAIL_BOOTS)
			.addRequirement(Skill.smithing, 26)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.IRON_NUGGET, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe CHAINMAIL_HELMET = new SQMCRecipe()
			.setOutput(CustomItem.CHAINMAIL_HELMET)
			.addRequirement(Skill.smithing, 27)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.IRON_NUGGET, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe CHAINMAIL_LEGGINGS = new SQMCRecipe()
			.setOutput(CustomItem.CHAINMAIL_LEGGINGS)
			.addRequirement(Skill.smithing, 28)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.IRON_NUGGET, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe CHAINMAIL_CHESTPLATE = new SQMCRecipe()
			.setOutput(CustomItem.CHAINMAIL_CHESTPLATE)
			.addRequirement(Skill.smithing, 29)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.IRON_NUGGET, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	/*Tier 4 smithing recipes*/
	
	public static final SQMCRecipe IRON_PICKAXE = new SQMCRecipe()
			.setOutput(CustomItem.IRON_PICKAXE)
			.addRequirement(Skill.smithing, 31)
			.setXP(Skill.smithing, 2.0)
			.addInput(CustomItem.IRON_INGOT, 3)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static final SQMCRecipe IRON_SWORD = new SQMCRecipe()
			.setOutput(CustomItem.IRON_SWORD)
			.addRequirement(Skill.smithing, 32)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.IRON_INGOT, 2)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe IRON_AXE = new SQMCRecipe()
			.setOutput(CustomItem.IRON_AXE)
			.addRequirement(Skill.smithing, 33)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.IRON_INGOT, 3)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe IRON_SHOVEL = new SQMCRecipe()
			.setOutput(CustomItem.IRON_SHOVEL)
			.addRequirement(Skill.smithing, 34)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.IRON_INGOT, 1)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe IRON_HOE = new SQMCRecipe()
			.setOutput(CustomItem.IRON_HOE)
			.addRequirement(Skill.smithing, 35)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.IRON_INGOT, 2)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe IRON_BOOTS = new SQMCRecipe()
			.setOutput(CustomItem.IRON_BOOTS)
			.addRequirement(Skill.smithing, 36)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.IRON_INGOT, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe IRON_HELMET = new SQMCRecipe()
			.setOutput(CustomItem.IRON_HELMET)
			.addRequirement(Skill.smithing, 37)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.IRON_INGOT, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe IRON_LEGGINGS = new SQMCRecipe()
			.setOutput(CustomItem.IRON_LEGGINGS)
			.addRequirement(Skill.smithing, 38)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.IRON_INGOT, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe IRON_CHESTPLATE = new SQMCRecipe()
			.setOutput(CustomItem.IRON_CHESTPLATE)
			.addRequirement(Skill.smithing, 39)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.IRON_INGOT, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	/*Tier 5 smithing recipes */
	
	public static final SQMCRecipe STEEL_PICKAXE = new SQMCRecipe()
			.setOutput(CustomItem.STEEL_PICKAXE)
			.addRequirement(Skill.smithing, 41)
			.setXP(Skill.smithing, 2.0)
			.addInput(CustomItem.STEEL_INGOT, 3)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static final SQMCRecipe STEEL_SWORD = new SQMCRecipe()
			.setOutput(CustomItem.STEEL_SWORD)
			.addRequirement(Skill.smithing, 42)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.STEEL_INGOT, 2)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe STEEL_AXE = new SQMCRecipe()
			.setOutput(CustomItem.STEEL_AXE)
			.addRequirement(Skill.smithing, 43)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.STEEL_INGOT, 3)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe STEEL_SHOVEL = new SQMCRecipe()
			.setOutput(CustomItem.STEEL_SHOVEL)
			.addRequirement(Skill.smithing, 44)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.STEEL_INGOT, 1)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe STEEL_HOE = new SQMCRecipe()
			.setOutput(CustomItem.STEEL_HOE)
			.addRequirement(Skill.smithing, 45)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.STEEL_INGOT, 2)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe STEEL_BOOTS = new SQMCRecipe()
			.setOutput(CustomItem.STEEL_BOOTS)
			.addRequirement(Skill.smithing, 46)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.STEEL_INGOT, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe STEEL_HELMET = new SQMCRecipe()
			.setOutput(CustomItem.STEEL_HELMET)
			.addRequirement(Skill.smithing, 47)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.STEEL_INGOT, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe STEEL_LEGGINGS = new SQMCRecipe()
			.setOutput(CustomItem.STEEL_LEGGINGS)
			.addRequirement(Skill.smithing, 48)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.STEEL_INGOT, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe STEEL_CHESTPLATE = new SQMCRecipe()
			.setOutput(CustomItem.STEEL_CHESTPLATE)
			.addRequirement(Skill.smithing, 49)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.STEEL_INGOT, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	/*Tier 6*/
	
	public static final SQMCRecipe DIAMOND_PICKAXE = new SQMCRecipe()
			.setOutput(CustomItem.DIAMOND_PICKAXE)
			.addRequirement(Skill.smithing, 51)
			.setXP(Skill.smithing, 2.0)
			.addInput(CustomItem.DIAMOND, 3)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static final SQMCRecipe DIAMOND_SWORD = new SQMCRecipe()
			.setOutput(CustomItem.DIAMOND_SWORD)
			.addRequirement(Skill.smithing, 52)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.DIAMOND, 2)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe DIAMOND_AXE = new SQMCRecipe()
			.setOutput(CustomItem.DIAMOND_AXE)
			.addRequirement(Skill.smithing, 53)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.DIAMOND, 3)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe DIAMOND_SHOVEL = new SQMCRecipe()
			.setOutput(CustomItem.DIAMOND_SHOVEL)
			.addRequirement(Skill.smithing, 54)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.DIAMOND, 1)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe DIAMOND_HOE = new SQMCRecipe()
			.setOutput(CustomItem.DIAMOND_HOE)
			.addRequirement(Skill.smithing, 55)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.DIAMOND, 2)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe DIAMOND_BOOTS = new SQMCRecipe()
			.setOutput(CustomItem.DIAMOND_BOOTS)
			.addRequirement(Skill.smithing, 56)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.DIAMOND, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe DIAMOND_HELMET = new SQMCRecipe()
			.setOutput(CustomItem.DIAMOND_HELMET)
			.addRequirement(Skill.smithing, 57)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.DIAMOND, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe DIAMOND_LEGGINGS = new SQMCRecipe()
			.setOutput(CustomItem.DIAMOND_LEGGINGS)
			.addRequirement(Skill.smithing, 58)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.DIAMOND, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe DIAMOND_CHESTPLATE = new SQMCRecipe()
			.setOutput(CustomItem.DIAMOND_CHESTPLATE)
			.addRequirement(Skill.smithing, 59)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.DIAMOND, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	/*Tier 7*/
	
	public static final SQMCRecipe IRIDESCENT_PICKAXE = new SQMCRecipe()
			.setOutput(CustomItem.IRIDESCENT_PICKAXE)
			.addRequirement(Skill.smithing, 61)
			.setXP(Skill.smithing, 2.0)
			.addInput(CustomItem.IRIDESCENT_GEM, 3)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static final SQMCRecipe IRIDESCENT_SWORD = new SQMCRecipe()
			.setOutput(CustomItem.IRIDESCENT_SWORD)
			.addRequirement(Skill.smithing, 62)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.IRIDESCENT_GEM, 2)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe IRIDESCENT_AXE = new SQMCRecipe()
			.setOutput(CustomItem.IRIDESCENT_AXE)
			.addRequirement(Skill.smithing, 63)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.IRIDESCENT_GEM, 3)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe IRIDESCENT_SHOVEL = new SQMCRecipe()
			.setOutput(CustomItem.IRIDESCENT_SHOVEL)
			.addRequirement(Skill.smithing, 64)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.IRIDESCENT_GEM, 1)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe IRIDESCENT_HOE = new SQMCRecipe()
			.setOutput(CustomItem.IRIDESCENT_HOE)
			.addRequirement(Skill.smithing, 65)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.IRIDESCENT_GEM, 2)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe IRIDESCENT_BOOTS = new SQMCRecipe()
			.setOutput(CustomItem.IRIDESCENT_BOOTS)
			.addRequirement(Skill.smithing, 66)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.IRIDESCENT_GEM, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe IRIDESCENT_HELMET = new SQMCRecipe()
			.setOutput(CustomItem.IRIDESCENT_HELMET)
			.addRequirement(Skill.smithing, 67)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.IRIDESCENT_GEM, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe IRIDESCENT_LEGGINGS = new SQMCRecipe()
			.setOutput(CustomItem.IRIDESCENT_LEGGINGS)
			.addRequirement(Skill.smithing, 68)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.IRIDESCENT_GEM, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe IRIDESCENT_CHESTPLATE = new SQMCRecipe()
			.setOutput(CustomItem.IRIDESCENT_CHESTPLATE)
			.addRequirement(Skill.smithing, 69)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.IRIDESCENT_GEM, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	/*Tier 8*/
	
	public static final SQMCRecipe RADIANT_PICKAXE = new SQMCRecipe()
			.setOutput(CustomItem.RADIANT_PICKAXE)
			.addRequirement(Skill.smithing, 71)
			.setXP(Skill.smithing, 2.0)
			.addInput(CustomItem.RADIANT_GEM, 3)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static final SQMCRecipe RADIANT_SWORD = new SQMCRecipe()
			.setOutput(CustomItem.RADIANT_SWORD)
			.addRequirement(Skill.smithing, 72)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.RADIANT_GEM, 2)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe RADIANT_AXE = new SQMCRecipe()
			.setOutput(CustomItem.RADIANT_AXE)
			.addRequirement(Skill.smithing, 73)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.RADIANT_GEM, 3)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe RADIANT_SHOVEL = new SQMCRecipe()
			.setOutput(CustomItem.RADIANT_SHOVEL)
			.addRequirement(Skill.smithing, 74)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.RADIANT_GEM, 1)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe RADIANT_HOE = new SQMCRecipe()
			.setOutput(CustomItem.RADIANT_HOE)
			.addRequirement(Skill.smithing, 75)
			.setXP(Skill.smithing, 4.0)
			.addInput(CustomItem.RADIANT_GEM, 2)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe RADIANT_BOOTS = new SQMCRecipe()
			.setOutput(CustomItem.RADIANT_BOOTS)
			.addRequirement(Skill.smithing, 76)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.RADIANT_GEM, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe RADIANT_HELMET = new SQMCRecipe()
			.setOutput(CustomItem.RADIANT_HELMET)
			.addRequirement(Skill.smithing, 77)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.RADIANT_GEM, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe RADIANT_LEGGINGS = new SQMCRecipe()
			.setOutput(CustomItem.RADIANT_LEGGINGS)
			.addRequirement(Skill.smithing, 78)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.RADIANT_GEM, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	public static SQMCRecipe RADIANT_CHESTPLATE = new SQMCRecipe()
			.setOutput(CustomItem.RADIANT_CHESTPLATE)
			.addRequirement(Skill.smithing, 79)
			.setXP(Skill.smithing, 16.0)
			.addInput(CustomItem.RADIANT_GEM, 4)
			.setRecipeType(RecipeType.SMITHING_ANVIL);
	
	/*Smelting recipes*/
	public static SQMCRecipe STONE = new SQMCRecipe()
			.setOutput(CustomItem.STONE)
			.addRequirement(Skill.smithing, 1)
			.setXP(Skill.smithing, 5.0)
			.addInput(CustomItem.COBBLESTONE)
			.addInput(CustomItem.COAL)
			.setRecipeType(RecipeType.SMITHING_FURNACE);
	
	public static SQMCRecipe IRON_NUGGET = new SQMCRecipe()
			.setOutput(CustomItem.IRON_NUGGET)
			.addRequirement(Skill.smithing, 15)
			.setXP(Skill.smithing, 5.0)
			.addInput(CustomItem.IRON_ORE, 9)
			.addInput(CustomItem.COAL, 5)
			.setRecipeType(RecipeType.SMITHING_FURNACE);
	
	public static SQMCRecipe IRON_INGOT = new SQMCRecipe()
			.setOutput(CustomItem.IRON_INGOT)
			.addRequirement(Skill.smithing, 30)
			.setXP(Skill.smithing, 5.0)
			.addInput(CustomItem.IRON_NUGGET, 9)
			.addInput(CustomItem.COAL, 5)
			.setRecipeType(RecipeType.SMITHING_FURNACE);
	
	public static SQMCRecipe STEEL_INGOT = new SQMCRecipe()
			.setOutput(CustomItem.STEEL_INGOT)
			.addRequirement(Skill.smithing, 40)
			.setXP(Skill.smithing, 5.0)
			.addInput(CustomItem.IRON_INGOT, 9)
			.addInput(CustomItem.COAL, 5)
			.addInput(CustomItem.CALCIUM_CARBONATE)
			.setRecipeType(RecipeType.SMITHING_FURNACE);
	
	/*Crafting table recipes*/
	public static SQMCRecipe OAK_PLANKS = new SQMCRecipe()
			.setOutput(CustomItem.OAK_PLANKS)
			.addInput(CustomItem.OAK_LOG)
			.setRecipeType(RecipeType.CRAFTING_TABLE);
	
	public static SQMCRecipe BIRCH_PLANKS = new SQMCRecipe()
			.setOutput(CustomItem.BIRCH_PLANKS)
			.addInput(CustomItem.BIRCH_LOG)
			.setRecipeType(RecipeType.CRAFTING_TABLE);
	
	public static SQMCRecipe SPRUCE_PLANKS = new SQMCRecipe()
			.setOutput(CustomItem.SPRUCE_PLANKS)
			.addInput(CustomItem.SPRUCE_LOG)
			.setRecipeType(RecipeType.CRAFTING_TABLE);
	
	public static SQMCRecipe JUNGLE_PLANKS = new SQMCRecipe()
			.setOutput(CustomItem.JUNGLE_PLANKS)
			.addInput(CustomItem.JUNGLE_LOG)
			.setRecipeType(RecipeType.CRAFTING_TABLE);
	
	public static SQMCRecipe ACACIA_PLANKS = new SQMCRecipe()
			.setOutput(CustomItem.ACACIA_PLANKS)
			.addInput(CustomItem.ACACIA_LOG)
			.setRecipeType(RecipeType.CRAFTING_TABLE);
	
	public static SQMCRecipe DARK_OAK_PLANKS = new SQMCRecipe()
			.setOutput(CustomItem.DARK_OAK_PLANKS)
			.addInput(CustomItem.DARK_OAK_LOG)
			.setRecipeType(RecipeType.CRAFTING_TABLE);
	
	
	/*Cooking recipes*/
	public static RecipeCookedSalmon recipeCookedSalmon = new RecipeCookedSalmon();
	public static RecipeCookedCod recipeCookedCod = new RecipeCookedCod();
	public static RecipeCookedTropicalFish recipeCookedTropicalFish = new RecipeCookedTropicalFish();
	public static RecipeCookedPufferfish recipeCookedPufferfish = new RecipeCookedPufferfish();
	public static RecipeCookedSquid recipeCookedSquid = new RecipeCookedSquid();
	
	/* Fields */
	private CustomItem output;
	private int amount = 1;
	private Map<Skill, Integer> requirements = new LinkedHashMap<Skill, Integer>();
	private Map<Skill, Double> xp = new LinkedHashMap<Skill, Double>();
	private Map<CustomItem, Integer> inputs = new LinkedHashMap<CustomItem, Integer>();
	private RecipeType recipeType;
	private boolean acceptsSubstitutes = false;

	/* Constructors */

	public SQMCRecipe() {
		recipes.add(this);
	}

	/* Setters and getters */
	
	public static ArrayList<SQMCRecipe> getRecipes() {
		return recipes;
	}
	
	private void setInputs(Map<CustomItem, Integer> inputs) {
		this.inputs = inputs;
	}
	
	public SQMCRecipe setOutput(CustomItem ci) {
		this.output = ci;
		return this;
	}
	
	public SQMCRecipe setOutput(CustomItem ci, int amount) {
		this.output = ci;
		this.amount = amount;
		return this;
	}

	public CustomItem getOutput() {
		return this.output;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public SQMCRecipe addRequirement(Skill s, int lvl) {
		requirements.put(s, lvl);
		return this;
	}

	public Map<Skill, Integer> getRequirements() {
		return this.requirements;
	}
	
	private void setRequirements(Map<Skill, Integer> requirements) {
		this.requirements = requirements;
		
	}

	public SQMCRecipe setXP(Skill s, Double amount) {
		xp.put(s, amount);
		return this;
	}
	
	private void setXP(Map<Skill, Double> xp) {
		this.xp = xp;
	}

	public Map<Skill, Double> getXP() {
		return xp;
	}
	
	public double getXP(Skill s) {
		if(getXP().containsKey(s)) return getXP().get(s);
		return 0;
	}

	public SQMCRecipe addInput(CustomItem ci, int amount) {
		inputs.put(ci, amount);
		return this;
	}
	
	public SQMCRecipe addInput(CustomItem ci) {
		inputs.put(ci, 1);
		return this;
	}

	public Map<CustomItem, Integer> getInputs() {
		return this.inputs;
	}

	public SQMCRecipe setRecipeType(RecipeType type) {
		this.recipeType = type;
		return this;
	}

	public RecipeType getRecipeType() {
		return this.recipeType;
	}

	public ItemStack getUIItemStack(Player player) {
		ItemStack output = new ItemStack(getOutput().getItemStack().getType());
		
		for(Enchantment e: getOutput().getEnchantments().keySet()) {
			output.addUnsafeEnchantment(e, getOutput().getEnchantments().get(e));
		}
		
		ItemMeta meta = output.getItemMeta();
		
		meta.setDisplayName(ChatColor.GOLD + getOutput().getName());
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		
		ArrayList<String> lore = new ArrayList<String>();

		if(getLevelRequirementLore(player).size() > 0) {
			lore.add(ChatColor.GRAY + "-------");

			lore.addAll(getLevelRequirementLore(player));
		}

		lore.add(ChatColor.GRAY + "-------");

		for (CustomItem ci : getInputs().keySet()) {

			if (hasRequiredInput(player, ci)) {
				lore.add(ChatColor.GREEN + "" + getInputs().get(ci) + " " + ci.getName() + " \u2713");
			} else {
				lore.add(ChatColor.RED + "" + getInputs().get(ci) + " " + ci.getName() + " \u2715");
			}

		}

		if(this.getXP().keySet().size() > 0) {
			
			lore.add(ChatColor.GRAY + "-------");

			for (Skill s : getXP().keySet()) {
				lore.add(ChatColor.DARK_PURPLE + "" + getXP(s) + " " + s.getProperName() + " XP");
			}
			
		}
		
		meta.setLore(lore);
		output.setItemMeta(meta);
		return output;
	}
	
	public ItemStack getSkillGuideItemStack(Player player) {
		ItemStack output = new ItemStack(getOutput().getItemStack());
		
		for(Enchantment e: getOutput().getEnchantments().keySet()) {
			output.addUnsafeEnchantment(e, getOutput().getEnchantments().get(e));
		}
		
		ItemMeta meta = output.getItemMeta();
		
		meta.setDisplayName(ChatColor.GOLD + getOutput().getName());
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
		
		ArrayList<String> lore = new ArrayList<String>();

		lore.add(ChatColor.GRAY + "-------");

		lore.addAll(getLevelRequirementLore(player));

		lore.add(ChatColor.GRAY + "-------");

		for (Skill s : getXP().keySet()) {
			lore.add(ChatColor.DARK_PURPLE + "" + getXP().get(s) + " " + s.getProperName() + " XP");
		}
		
		meta.setLore(lore);
		output.setItemMeta(meta);
		return output;
	}

	/* Craft a recipe when its recipe icon is clicked */
	
	public void onRecipeClick(InventoryClickEvent evt) {

			Player player = (Player) evt.getWhoClicked();
			ItemStack stack = evt.getCurrentItem();
			if(stack == null) return;
			if(stack.getType() == Material.AIR) return;
			if(stack.getItemMeta() == null) return;
			if(stack.getItemMeta().getDisplayName() == null) return;
			if(CustomItem.getCustomItem(stack) != null) return;
			
			for (SQMCRecipe r : SQMCRecipe.getRecipes()) {
				
				if (r.getUIItemStack(player).equals(stack)) {

					if(!canCraft(player, true)) {
						evt.setCancelled(true);
						player.closeInventory();
						return;
					} 

					DataManager.getPlayerData(player.getUniqueId()).getSessionData().setRecipe(this);
					player.sendMessage(ChatColor.GREEN + "Now crafting " + r.getOutput().getName() + ChatColor.GREEN + ".");
					player.closeInventory();
					
				}
			}

		

	}
	
	public boolean canCraft(Player player, boolean notify) {	
		
		if (!meetsRequirements(player)) {
			if(notify) sendInsufficientLevelNotice(player, "craft that");
			player.closeInventory();
			return false;
		}
		
		if (!hasRequiredItems(player)) {
			if(notify) player.sendMessage(ChatColor.RED + "You don't have enough materials to craft this item.");
			player.closeInventory();
			return false;
		}
		
		return true;
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onCustomRecipe(SQMCRecipeEvent evt) {
		if(!evt.isCancelled() && evt.getSQMCRecipe().canCraft(evt.getPlayer(), false)) {
			evt.getSQMCRecipe().craftItem(evt.getPlayer());
			for (Skill s : evt.getXpRewards().keySet()) {
				DataManager.getPlayerData(evt.getPlayer().getUniqueId()).awardXP(s, evt.getXpRewards().get(s));
			}
		} else {
			SessionData dat = DataManager.getPlayerData(evt.getPlayer().getUniqueId()).getSessionData();
			dat.setRecipe(null);
		}
		
	}
	
	
	/*Open crafting table UI*/
	@EventHandler(priority=EventPriority.LOW)
	public void onInteract(PlayerInteractEvent evt) {
		if(evt.getClickedBlock() == null) return;
		if(evt.isCancelled()) return;
		Material m = evt.getClickedBlock().getType();
		if(m.equals(Material.CRAFTING_TABLE) && evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			evt.getPlayer().closeInventory();
			UserInterface.craftingTable.open(evt.getPlayer());
			evt.setCancelled(true);
		}
		
	}
	
	//TODO: Optimize method
	public void craftItem(Player player) {
	
		for (CustomItem input : inputs.keySet()) {
	
			int amountToRemove = inputs.get(input);
			int removed = 0;
	
			while (amountToRemove > removed) {
				//Remove non-substitute
				
				for(ItemStack i: player.getInventory().getContents()) {
					CustomItem ci = CustomItem.getCustomItem(i);
					if(ci == null) continue;
					
					//Non-substitute
					if(ci.equals(input)) {
						i.setAmount(i.getAmount() - 1);
						removed++;
						break;
					}
					
					//Remove substitutes
					if(substitutes.containsKey(input) && acceptsSubstitutes()) {
						if(substitutes.get(input).contains(ci)) {
							i.setAmount(i.getAmount() - 1);
							removed++;
							break;
						}
					}
				}
				
			
			}
	
		}
	
		PlayerData dat = DataManager.getPlayerData(player.getUniqueId());
		dat.giveItem(getOutput().getItemStack(getAmount()));
		player.getWorld().playSound(player.getLocation(), getRecipeType().getSound(), getRecipeType().getVolume(), 0);
	
	}//END OF METHOD
	
	
	public boolean hasRequiredItems(Player player) {

		for (CustomItem input : inputs.keySet()) {

			if(!hasRequiredInput(player, input)) return false;

		}

		return true;
	}
	
	public boolean hasRequiredInput(Player player, CustomItem input) {
		
		int amountRequired = inputs.get(input);
		int totalAvailable = 0;
		
		//Add total without substitutes
		for(ItemStack i: player.getInventory().getContents()) {
			CustomItem ci = CustomItem.getCustomItem(i);
			if(ci == null) continue;
			if(ci.equals(input)) totalAvailable += i.getAmount();
		}
		
		//Add substitute total
		if(substitutes.keySet().contains(input) && acceptsSubstitutes()) {
			for(CustomItem substitute: substitutes.get(input)) {
				for(ItemStack i: player.getInventory().getContents()) {
					CustomItem examined = CustomItem.getCustomItem(i);
					if(examined == null) continue;
					if(examined.equals(substitute)) totalAvailable += i.getAmount();
				}
			}
		}
		
	
		if(amountRequired > totalAvailable) return false;
		
		return true;
		
	}

	public boolean acceptsSubstitutes() {
		return acceptsSubstitutes;
	}

	public SQMCRecipe setAcceptsSubstitutes(boolean acceptsSubstitutes) {
		this.acceptsSubstitutes = acceptsSubstitutes;
		return this;
	}

	@Override
	public int getLevelRequired(Skill s) {
		return requirements.get(s);
	}

	public static SQMCRecipe fromJSON(JSONObject obj) {
		
		/*Initialize variables*/
		int outputID = 1;
		int outputAmount = 1;
		Map<CustomItem, Integer> inputs = new LinkedHashMap<CustomItem, Integer>();
		Map<Skill, Integer> requirements = new LinkedHashMap<Skill, Integer>();
		Map<Skill, Double> xp = new LinkedHashMap<Skill, Double>();
		RecipeType type = RecipeType.SMITHING_ANVIL;
		boolean acceptsSubstitutes = false;
		
		/*Assign values*/
		if(obj.has("output-id")) outputID = obj.getInt("output-id");
		if(obj.has("output-amount")) outputAmount = obj.getInt("output-amount");
		if(obj.has("accepts-substitutes")) acceptsSubstitutes = obj.getBoolean("accepts-substitutes");
		
		if(obj.has("inputs")) {
			JSONArray jsonInputs = obj.getJSONArray("inputs");
			for(Object o: jsonInputs) {
				JSONObject jsonInput = (JSONObject) o;
				CustomItem input = CustomItem.STONE;
				int inputAmount = 1;
				if(jsonInput.has("item-id")) input = CustomItem.getCustomItem(jsonInput.getInt("item-id"));
				if(jsonInput.has("amount")) inputAmount = jsonInput.getInt("amount");
				inputs.put(input, inputAmount);
			}
		}
		
		if(obj.has("recipe-type")) {
			String typeString = obj.getString("recipe-type");
			for(RecipeType t: RecipeType.values()) {
				if(t.toString().equalsIgnoreCase(typeString)) {
					type = t;
				}
			}
		}
		
		if(obj.has("xp")) {
			JSONObject jsonXP = obj.getJSONObject("xp");
			for(Skill s: Skill.getSkills()) {
				if(jsonXP.has(s.getName().toLowerCase())) {
					xp.put(s, jsonXP.getDouble(s.getName().toLowerCase()));
				}
			}
		}
		
		if(obj.has("requirements")) {
			JSONObject jsonRequirements = obj.getJSONObject("requirements");
			for(Skill s: Skill.getSkills()) {
				if(jsonRequirements.has(s.getName().toLowerCase())) {
					requirements.put(s, jsonRequirements.getInt(s.getName().toLowerCase()));
				}
			}
		}
		
		
		/*Create and return objects*/
		SQMCRecipe recipe = new SQMCRecipe();
		recipe.setAcceptsSubstitutes(acceptsSubstitutes);
		recipe.setOutput(CustomItem.getCustomItem(outputID), outputAmount);
		recipe.setInputs(inputs);
		recipe.setRecipeType(type);
		recipe.setXP(xp);
		recipe.setRequirements(requirements);
		
		return null;
		
	}
	
	public static SQMCRecipe fromFile(String name) {
		
		InputStream stream = SQMCRecipe.class.getResourceAsStream("/recipe/" + name + ".json");
		Scanner sc = new Scanner(stream);
		String s = "";
		while(sc.hasNextLine()) {
			s += sc.nextLine();
		}
		sc.close();
		return fromJSON(new JSONObject(s));
		
	}
	
}
