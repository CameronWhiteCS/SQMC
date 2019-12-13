package squeeze.theorem.entity;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager.Profession;

import squeeze.theorem.entity.DialogueNode.DialogueType;
import squeeze.theorem.entity.uppkomst.Blacksmith;
import squeeze.theorem.entity.uppkomst.BotchedExperiment;
import squeeze.theorem.entity.uppkomst.BrightbriarMininingExecutive;
import squeeze.theorem.entity.uppkomst.CaptainFishy;
import squeeze.theorem.entity.uppkomst.ElevatorOperatorDescend;
import squeeze.theorem.entity.uppkomst.Florist;
import squeeze.theorem.entity.uppkomst.GrandMageTyrus;
import squeeze.theorem.entity.uppkomst.MagicStudent;
import squeeze.theorem.entity.uppkomst.MailCarrier;
import squeeze.theorem.entity.uppkomst.MelonFarmer;
import squeeze.theorem.entity.uppkomst.ProfessorFiddlesticks;
import squeeze.theorem.entity.uppkomst.ShadyMan;
import squeeze.theorem.entity.uppkomst.UnionBoss;
import squeeze.theorem.entity.uppkomst.UnionMiner;
import squeeze.theorem.entity.uppkomst.UppkomstPriestOfMoonti;
import squeeze.theorem.entity.volcanis.VolcanisPriestOfMoonti;
import squeeze.theorem.event.PlayerKillSQMCEntityEvent;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.skill.SQMCEntityFire;

public class SQMCEntity {
	
	/*These exist for the purpose of being accessible when creating dialogue nodes within sub-classes*/
	protected static DialogueType NPC = DialogueType.NPC;
	protected static DialogueType PLAYER = DialogueType.PLAYER;
	
	//Fires	
	public static SQMCEntityFire fireSpruce = new SQMCEntityFire("Spruce fire", CustomItem.SPRUCE_LOG, 1, 11.0, 30 * 20L, 1, 1);
	public static SQMCEntityFire fireBirch = new SQMCEntityFire("Birch fire", CustomItem.BIRCH_LOG, 15, 60.0, 60 * 20L, 2, 2);
	public static SQMCEntityFire fireOak = new SQMCEntityFire("Oak fire", CustomItem.OAK_LOG, 30, 90.0, 90 * 20L, 3, 3);
	public static SQMCEntityFire fireJungle = new SQMCEntityFire("Jungle fire", CustomItem.JUNGLE_LOG, 45, 135.0, 120 * 20L, 4, 4);
	public static SQMCEntityFire fireAcacia = new SQMCEntityFire("Acacia fire", CustomItem.ACACIA_LOG, 60, 202.5, 150 * 20L, 5, 5);
	public static SQMCEntityFire fireDarkOak = new SQMCEntityFire("Dark oak fire", CustomItem.DARK_OAK_LOG, 75, 303.75, 180 * 20L, 6, 6);
	
	//Uppkomst
	public static NPC brightbriarMiningExecutive = new BrightbriarMininingExecutive();
	public static NPC melonFarmer = new MelonFarmer();
	public static NPC captainFishy = new CaptainFishy();
	public static NPC shadyMan = new ShadyMan();
	public static NPC unionBoss = new UnionBoss();
	public static NPC unionMiner = new UnionMiner();
	public static NPC florist = new Florist();
	public static NPC mailCarrier = new MailCarrier();
	public static NPC professorFiddlesticks = new ProfessorFiddlesticks();
	public static NPC magicStudent = new MagicStudent();
	public static NPC BotchedExperiment = new BotchedExperiment();
	public static NPC uppkomstPriestOfMoonti = new UppkomstPriestOfMoonti();
	public static GrandMageTyrus grandMageTyrus = new GrandMageTyrus();
	public static Blacksmith blacksmith = new Blacksmith();
	public static GenericMob uppkomstCow = GenericMob.fromFileName("uppkomst-cow");
	public static ElevatorOperatorDescend elevatorOperatorDescend = new ElevatorOperatorDescend();
	public static GenericMob uppkomstBat = GenericMob.fromFileName("uppkomst-bat");
	public static GenericMob uppkomstCaveSpider = GenericMob.fromFileName("uppkomst-cavespider");
	public static GenericMob uppkomstFiend = GenericMob.fromFileName("uppkomst-fiend");
	public static GenericMob uppkomstUndeadMiner = GenericMob.fromFileName("uppkomst-undead-miner");
	
	//Volcanis
	public static NPC volcanisPriestOfMoonti = new VolcanisPriestOfMoonti();
	
	/*FIELDS*/
	private String prefix;
	private String name;
	private String suffix;
	private EntityType entityType;
	private boolean AI = true;
	private boolean invulnerable = false;
	private boolean gravity = true;
	private boolean silent = false;
	private boolean baby = false;
	private Profession profession;
	private boolean passive = false;
	private CustomItem mainhand, offhand, helmet, chestplate, leggings, boots;
	
	
	/* Constructors */
	public SQMCEntity(String name, EntityType entityType) {
		setName(name);
		setEntityType(entityType);
		EntityManager.getInstance().registerEntity(this);
	}

	
	/* Setters and getters */
	
	public void addSpawnPoint(Location loc)	{
		
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	public String getDisplayName() {
		String output = "";
		if(getPrefix() != null) output += (ChatColor.RED + getPrefix() + " ");
		if(getName() != null) output += (ChatColor.GREEN + getName());
		if(getSuffix() != null) output += (" " + ChatColor.AQUA + getSuffix());
		return output;
	}
	
	public boolean isBaby() {
		return baby;
	}

	public void setBaby(boolean baby) {
		this.baby = baby;
	}
	
	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	public EntityType getEntityType() {
		return this.entityType;
	}

	public boolean hasAI() {
		return AI;
	}

	public void setAI(boolean aI) {
		AI = aI;
	}

	public boolean isInvulnerable() {
		return invulnerable;
	}

	public void setInvulnerable(boolean invulnerable) {
		this.invulnerable = invulnerable;
	}
	
	public boolean hasGravity() {
		return gravity;
	}

	public void setGravity(boolean gravity) {
		this.gravity = gravity;
	}

	public boolean isSilent() {
		return silent;
	}

	public void setSilent(boolean silent) {
		this.silent = silent;
	}
	
	public Profession getProfession() {
		return profession;
	}

	public void setProfession(Profession profession) {
		this.profession = profession;
	}
	
	public void setPassive(boolean passive) {
		this.passive = passive;
	}
	
	public boolean isPassive() {
		return this.passive;
	}
	
	public CustomItem getOffhand() {
		return offhand;
	}

	public void setOffhand(CustomItem offhand) {
		this.offhand = offhand;
	}

	public CustomItem getHelmet() {
		return helmet;
	}

	public void setHelmet(CustomItem helmet) {
		this.helmet = helmet;
	}

	public CustomItem getChestplate() {
		return chestplate;
	}

	public void setChestplate(CustomItem chestplate) {
		this.chestplate = chestplate;
	}

	public CustomItem getLeggings() {
		return leggings;
	}

	public void setLeggings(CustomItem leggings) {
		this.leggings = leggings;
	}

	public CustomItem getBoots() {
		return boots;
	}

	public void setBoots(CustomItem boots) {
		this.boots = boots;
	}	
	
	//Intended to be overwritten
	protected void onDeath(PlayerKillSQMCEntityEvent evt) {
		
	}	

	public CustomItem getMainhand() {
		return mainhand;
	}

	public void setMainhand(CustomItem mainhand) {
		this.mainhand = mainhand;
	}

	//Intended to be overwritten
	public void onSpawn(LivingEntity entity) {

		
	}
	
}
