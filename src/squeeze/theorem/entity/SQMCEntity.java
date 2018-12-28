package squeeze.theorem.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_13_R2.EntityLiving;
import net.minecraft.server.v1_13_R2.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_13_R2.PacketPlayOutSpawnEntityLiving;
import squeeze.theorem.combat.CombatStats;
import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.data.SessionData;
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
import squeeze.theorem.main.SQMC;
import squeeze.theorem.mechanics.Cooldown;
import squeeze.theorem.skill.SQMCEntityFire;
import squeeze.theorem.skill.Skill;

public class SQMCEntity implements Listener, Runnable {
	
	/*STATIC FIELDS*/
	private static List<SQMCEntity> entities = new ArrayList<SQMCEntity>();
	private static List<Anchorable> anchorables = new ArrayList<Anchorable>();
	private static List<Location> queuedLocations = new ArrayList<Location>();
	private static Map<Entity, SQMCEntity> entityMap = new HashMap<Entity, SQMCEntity>();
	private static Map<Entity, Location> spawnpoints = new HashMap<Entity, Location>();
	private static Map<Entity, Location> previousLocation = new HashMap<Entity, Location>();
	private static Map<Entity, Location> currentLocation = new HashMap<Entity, Location>();
	private static Map<Entity, Long> lastStruck = new ConcurrentHashMap<Entity, Long>();
	protected static DialogueType NPC = DialogueType.NPC;
	protected static DialogueType PLAYER = DialogueType.PLAYER;
	public static List<SQMCEntityFire> fires = new ArrayList<SQMCEntityFire>();
	
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
		if(this instanceof Anchorable) anchorables.add((Anchorable) this);
		entities.add(this);
	}

	/* Setters and getters */
	public static List<SQMCEntity> getEntities(){
		return entities;
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
	
	public static List<SQMCEntityFire> getFires() {
		return fires;
	}

	public static SQMCEntity getSQMCEntity(Entity entity) {

		for(Entity e: entityMap.keySet()) {
			if(e == entity) return entityMap.get(entity);
		}

		return null;

	}
	
	public static List<Anchorable> getAnchorableEntities() {
		return anchorables;
	}

	/* Methods */
	public LivingEntity spawn(Location loc) {
		World world = loc.getWorld();
		LivingEntity entity = (LivingEntity) world.spawnEntity(loc, getEntityType());
		entity.setCustomName(getDisplayName());
		entity.setCustomNameVisible(true);
		entity.setAI(hasAI());
		entity.setGravity(hasGravity());
		entity.setInvulnerable(isInvulnerable());
		entity.setSilent(isSilent());
		entity.setRemoveWhenFarAway(false);
		
		if(getMainhand() != null) entity.getEquipment().setItemInMainHand(getMainhand().getItemStack());
		if(getOffhand() != null) entity.getEquipment().setItemInOffHand(getOffhand().getItemStack());
		if(getHelmet() != null) entity.getEquipment().setHelmet(getHelmet().getItemStack());
		if(getChestplate() != null) entity.getEquipment().setChestplate(getChestplate().getItemStack());
		if(getLeggings() != null) entity.getEquipment().setLeggings(getLeggings().getItemStack());
		if(getBoots() != null) entity.getEquipment().setBoots(getBoots().getItemStack());
		
		if(getProfession() != null && entity instanceof Villager) {
			Villager v = (Villager) entity;
			v.setProfession(getProfession());
		}
		
		if(this instanceof Boundable) {
			previousLocation.put(entity, entity.getLocation());
			currentLocation.put(entity, entity.getLocation());
		}
		
		if(entity instanceof Ageable) {
			Ageable age = (Ageable) entity;
			if(baby) age.setBaby();
			if(!baby) age.setAdult();
		}
		
		if(this instanceof CombatStats) {
			CombatStats cs = (CombatStats) this;
			entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(cs.getHealth());
			entity.setHealth(cs.getHealth());
		}
		
		queuedLocations.remove(loc);
		entityMap.put(entity, this);
		
		// Anchorable mechanics
		if (this instanceof Anchorable) spawnpoints.put(entity, loc);

	
		return entity;

	}

	// Remove old entities & spawn anchored entities on chunk load
	@EventHandler
	public static void onChunkLoad(ChunkLoadEvent evt) {
		
		for(Entity e: evt.getChunk().getEntities()) {
			if(e instanceof LivingEntity && e instanceof Player == false) e.remove();
		}
		
		for (Anchorable anch : getAnchorableEntities()) {
			SQMCEntity ce = (SQMCEntity) anch;
			Chunk chunk = evt.getChunk();
			List<Location> locs = anch.getLocationsWithin(chunk);
			if (locs.isEmpty())
				continue;

			for (Location loc : locs) {
				if(!alreadySpawned(loc) && !queuedLocations.contains(loc)) {
					ce.spawn(loc);
					queuedLocations.remove(loc);
				}
				
			}

		}
	}
	
	private static boolean alreadySpawned(Location loc) {
		
		return spawnpoints.containsValue(loc);
		
	}

	public static Location getLocation(Entity e) {
		
		if(spawnpoints.containsKey(e)) return spawnpoints.get(e);
		
		return null;
		
	}

	// Remove anchored entities on chunk load
	@EventHandler
	public static void onChunkUnload(ChunkUnloadEvent evt) {

		Chunk chunk = evt.getChunk();
		for (Entity e : chunk.getEntities()) {

			SQMCEntity ce = SQMCEntity.getSQMCEntity(e);
			if (ce == null) continue;
			entityMap.remove(e);
			spawnpoints.remove(e);
			e.remove();
			
		}

	}	
	
	/*EVENTS*/
	@EventHandler
	public void onDrop(PlayerDropItemEvent evt) {
		Player player = evt.getPlayer();
		SessionData sdat = DataManager.getPlayerData(player.getUniqueId()).getSessionData();
		sdat.endConversation();
	}
	
	@EventHandler
	public void onPickup(EntityPickupItemEvent evt) {
		if(evt.getEntity() instanceof Player == false) return;
		Player player = (Player) evt.getEntity();
		SessionData sdat = DataManager.getPlayerData(player.getUniqueId()).getSessionData();
		sdat.endConversation();
	}
	
	//Intended to be overwritten
	protected void onDeath(PlayerKillSQMCEntityEvent evt) {
		
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent evt) {
		eyeTracking(evt);
		endConversations(evt);
	}
	
	private static void eyeTracking(PlayerMoveEvent evt) {
		if(evt.isCancelled()) return;
		Location loc = evt.getTo();
		Player player = evt.getPlayer();
		World world = player.getWorld();

		for (Entity e : world.getEntities()) {

			SQMCEntity customEntity = getSQMCEntity(e);
			if (customEntity == null)
				continue;
			if (customEntity instanceof EyeTracking == false)
				continue;
			EyeTracking eyeTracking = (EyeTracking) customEntity;

			if (e.getLocation().distance(loc) > eyeTracking.getTrackingDistance())
				continue;

			Vector v = loc.toVector().subtract(e.getLocation().toVector());
			Location loc2 = e.getLocation();
			loc2.setDirection(v);
			e.teleport(loc2);

		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEntityEvent evt) {

		if (evt.isCancelled()) return;
		if (evt.getHand().equals(EquipmentSlot.OFF_HAND)) {
			evt.setCancelled(true);
			return;
		}

		
		SQMCEntity cust = SQMCEntity.getSQMCEntity(evt.getRightClicked());
		if (cust == null) return;
		
		Entity entity = evt.getRightClicked();
		Player player = evt.getPlayer();
		PlayerData dat = DataManager.getPlayerData(player.getUniqueId());
		
		//Cancel all interactions if entity is invisible
		if(cust instanceof ConditionallyVisible) {
			ConditionallyVisible cv = (ConditionallyVisible) cust;
			if(!cv.isVisible(evt.getPlayer())) {
				evt.setCancelled(true);
				return;
			}
		}
		
		//Interactable
		interact: {
			if(cust instanceof Interactable == false) break interact;
			if(player.isSneaking() == true && cust instanceof Pickpocketable == true) break interact;
				Interactable dialogue = (Interactable) cust;
	
				dat.getSessionData().setNPC(entity);
	
				dat.getSessionData().setDialogueNode(dialogue.getDialogueNode(evt.getPlayer()));
			
		}
		
		//Pickpocketable
		thiev: {
			if(cust instanceof Pickpocketable == false) break thiev;
			if(Cooldown.pickpocketing.isOnCooldown(player.getUniqueId())) break thiev;
			if(player.isSneaking() == false) break thiev;
			
			Pickpocketable pickpocketable = (Pickpocketable) cust;
			
			if(!pickpocketable.meetsRequirements(player)) {
				pickpocketable.sendInsufficientLevelNotice(player, "pickpocket this NPC");
				break thiev;
			}
					
			if(pickpocketable.didSucceed(player)) {
				dat.giveItems(pickpocketable.getLoot(player));
				player.playSound(player.getLocation(), Sound.BLOCK_WOOL_PLACE, 1F, 0F);
				dat.awardXP(Skill.larceny, pickpocketable.getXP());
			} else {
				dat.damage(pickpocketable.getDamageOnFail(player));
				player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT, 1F, 0F);
				player.sendMessage(ChatColor.RED + cust.getName() + " yells angirly, saying '" + pickpocketable.getFailMessage() + "'");
			}
					
			Cooldown.pickpocketing.addPlayer(player.getUniqueId(), pickpocketable.getCooldown(player));
		}

		evt.setCancelled(true);

	}
	
	private static void endConversations(PlayerMoveEvent evt) {
		if(evt.isCancelled()) return;
		Player player = evt.getPlayer();
		PlayerData dat = DataManager.getPlayerData(player.getUniqueId());
		SessionData sessionData = dat.getSessionData();
		if (sessionData.getNPC() == null || sessionData.getDialogueNode() == null)
			return;

		if (evt.getTo().distance(sessionData.getNPC().getLocation()) > 7) {

			sessionData.endConversation();

		}
	}
	
	/*RUNNABLE*/
	public void run() {
		boundEntities();
		condtionallyVisible();
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onStrike(EntityDamageByEntityEvent evt) {
		
		if(evt.getDamager() instanceof Player == false || evt.getEntity() instanceof Player) return;
		
		if(evt.isCancelled()) return;
		
		lastStruck.put(evt.getEntity(), System.currentTimeMillis());
		
	}
	
	
	
	private static void boundEntities() {
		
		for(Entity e: lastStruck.keySet()) {
			long diff = System.currentTimeMillis() - lastStruck.get(e);
			if(diff > 60000) lastStruck.remove(e);
		}
		
		for(World w: Bukkit.getWorlds()){
			for(Entity e: w.getEntities()) {
						
				SQMCEntity ce = getSQMCEntity(e);
				if(ce == null) continue;	
				
				if(ce instanceof Boundable == false) continue;
				Boundable boundable = (Boundable) ce;
					
				if(e instanceof Mob == false) return;
				Mob m = (Mob) e;
				
				if(lastStruck.containsKey(e) || m.getTarget() instanceof Player) continue;
				
				Location origin = getLocation(e);
				if(origin == null) continue;
				
				double x = e.getLocation().getX();
				double y = e.getLocation().getY();
				double z = e.getLocation().getZ();
				
				Vector v = new Vector(x, y, z).subtract(new Vector(origin.getX(), origin.getY(), origin.getZ()));
				if(v.length() > boundable.getWanderRadius()) {
					
					e.teleport(previousLocation.get(e));
					currentLocation.put(e, e.getLocation());
					
				} else {
					
					previousLocation.put(e, currentLocation.get(e));
					currentLocation.put(e, e.getLocation());
					
				}
			}
		}
	}
	
	private static void condtionallyVisible() {
		
		
		for(Player player: Bukkit.getOnlinePlayers()) {
			World world = player.getWorld();
			for(Entity e: world.getEntities()) {
				if(e.getLocation().distance(player.getLocation()) > 64) continue;
				if(e instanceof LivingEntity == false) return;
				SQMCEntity ce = getSQMCEntity(e);
				if(ce == null) continue;
				if(ce instanceof ConditionallyVisible == false) continue;
				ConditionallyVisible cv = (ConditionallyVisible) ce;
				
				if(cv.isVisible(player) == false) {
					PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] {e.getEntityId()});
					((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
					cv.getViewers().remove(player);
				} else {
					
		
					if(cv.getViewers().contains(player)) continue;
					PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving((EntityLiving)((CraftEntity) e).getHandle());
					((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
					cv.getViewers().add(player);
					
				}
				
				
			}
		}
		
	}
	
	/* Death of custom entities */	
	@EventHandler
	public void onDeath(EntityDeathEvent evt) {
		respawn(evt);
	}
	
	public static void respawn(EntityDeathEvent evt) {
		
		Entity entity = evt.getEntity();
		SQMCEntity ce = getSQMCEntity(entity);
		if(ce == null) return;
		
		Location loc = getLocation(entity);
		if(loc == null) return;
		if(ce instanceof Respawnable == false) return;
		Respawnable respawnable = (Respawnable) ce;
		if(ce instanceof Anchorable == false) return;
		Anchorable anchorable = (Anchorable) ce;
		queuedLocations.add(loc);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(SQMC.getPlugin(SQMC.class), new Runnable() {
			public void run() {
				if(!alreadySpawned(loc) && loc.getChunk().isLoaded() && anchorable.getLocations().contains(loc)) {
					ce.spawn(loc);
				}
				
			}
		}, respawnable.getRespawnDelay());	
		
		entityMap.remove(evt.getEntity());
		spawnpoints.remove(evt.getEntity());
		
	}
	
	@EventHandler
	public void onTarget(EntityTargetEvent evt) {
		SQMCEntity ce = SQMCEntity.getSQMCEntity(evt.getEntity());
		if(ce == null) return;
		if(ce.isPassive()) evt.setCancelled(true);
	}

	public CustomItem getMainhand() {
		return mainhand;
	}

	public void setMainhand(CustomItem mainhand) {
		this.mainhand = mainhand;
	}
	
}
