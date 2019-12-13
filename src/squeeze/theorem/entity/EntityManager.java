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
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
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

import net.minecraft.server.v1_14_R1.EntityLiving;
import net.minecraft.server.v1_14_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_14_R1.PacketPlayOutSpawnEntityLiving;
import squeeze.theorem.combat.CombatStats;
import squeeze.theorem.data.DataManager;
import squeeze.theorem.data.PlayerData;
import squeeze.theorem.data.SessionData;
import squeeze.theorem.main.SQMC;
import squeeze.theorem.mechanics.Cooldown;
import squeeze.theorem.skill.SQMCEntityFire;
import squeeze.theorem.skill.Skill;

public class EntityManager implements Runnable, Listener {

	private static EntityManager instance = new EntityManager();
	
	//Lists for specific SQMCEntity types
	private List<SQMCEntity> entities = new ArrayList<SQMCEntity>();
	private List<Anchorable> anchorables = new ArrayList<Anchorable>();
	private List<Boundable> boundables = new ArrayList<Boundable>();
	public List<SQMCEntityFire> fires = new ArrayList<SQMCEntityFire>();
	
	//Maps for managing entities with specific properties
	private List<Location> queuedLocations = new ArrayList<Location>();
	private Map<Entity, SQMCEntity> entityMap = new HashMap<Entity, SQMCEntity>();
	private Map<Entity, Location> previousLocation = new HashMap<Entity, Location>();
	private Map<Entity, Location> currentLocation = new HashMap<Entity, Location>();
	private Map<Entity, Long> lastStruck = new ConcurrentHashMap<Entity, Long>(); //TODO: Make it where an entity targeting a player also puts them on this list
	private Map<Entity, Location> anchors = new HashMap<Entity, Location>();
	
	private EntityManager() {
		
	}
	
	/**
	 * Returns true if the entity corresponding to the provided location is alive
	 * and not queued to respawn, false if dead or unloaded.
	 * 
	 * @param loc Location corresponding to the entity in question
	 * @return
	 */
	private boolean isAlive(Location loc) {
		return anchors.containsValue(loc);	
	}
	
	private Location getAnchor(Entity e) {
		if(anchors.containsKey(e)) return anchors.get(e);
		return null;
	}
	
	public void registerEntity(SQMCEntity entity) {
		if(entity instanceof Anchorable) anchorables.add((Anchorable) entity);
		if(entity instanceof SQMCEntityFire) fires.add((SQMCEntityFire) entity);
		if(entity instanceof Boundable) boundables.add((Boundable) entity);
		entities.add(entity);
	}
	
	public List<SQMCEntity> getEntities(){
		return entities;
	}
	
	public List<Anchorable> getAnchorables() {
		return anchorables;
	}
	
	public List<SQMCEntityFire> getFires() {
		return fires;
	}
	
	public SQMCEntity getSQMCEntity(Entity entity) {
		for(Entity e: entityMap.keySet()) {
			if(e == entity) return entityMap.get(entity);
		}
		return null;
	}
	
	public LivingEntity spawn(SQMCEntity sqmcEntity, Location loc) {
		World world = loc.getWorld();
		LivingEntity entity = (LivingEntity) world.spawnEntity(loc, sqmcEntity.getEntityType());
		entity.setCustomName(sqmcEntity.getDisplayName());
		entity.setCustomNameVisible(true);
		entity.setAI(sqmcEntity.hasAI());
		entity.setGravity(sqmcEntity.hasGravity());
		entity.setInvulnerable(sqmcEntity.isInvulnerable());
		entity.setSilent(sqmcEntity.isSilent());
		entity.setRemoveWhenFarAway(false);
		
		if(sqmcEntity.getMainhand() != null) entity.getEquipment().setItemInMainHand(sqmcEntity.getMainhand().getItemStack());
		if(sqmcEntity.getOffhand() != null) entity.getEquipment().setItemInOffHand(sqmcEntity.getOffhand().getItemStack());
		if(sqmcEntity.getHelmet() != null) entity.getEquipment().setHelmet(sqmcEntity.getHelmet().getItemStack());
		if(sqmcEntity.getChestplate() != null) entity.getEquipment().setChestplate(sqmcEntity.getChestplate().getItemStack());
		if(sqmcEntity.getLeggings() != null) entity.getEquipment().setLeggings(sqmcEntity.getLeggings().getItemStack());
		if(sqmcEntity.getBoots() != null) entity.getEquipment().setBoots(sqmcEntity.getBoots().getItemStack());
		
		if(sqmcEntity.getProfession() != null && entity instanceof Villager) {
			Villager v = (Villager) entity;
			v.setProfession(sqmcEntity.getProfession());
		}
		
		if(sqmcEntity instanceof Boundable) {
			previousLocation.put(entity, entity.getLocation());
			currentLocation.put(entity, entity.getLocation());
		}
	
		if(sqmcEntity instanceof CombatStats) {
			CombatStats cs = (CombatStats) sqmcEntity;
			entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(cs.getHealth());
			entity.setHealth(cs.getHealth());
		}
		
		if (sqmcEntity instanceof Anchorable) {
			anchors.put(entity, loc);	
		}
		
		if(entity instanceof Ageable) {
			Ageable age = (Ageable) entity;
			if(sqmcEntity.isBaby()) age.setBaby();
			if(!sqmcEntity.isBaby()) age.setAdult();
		}
		
		queuedLocations.remove(loc);
		entityMap.put(entity, sqmcEntity);

		sqmcEntity.onSpawn(entity);
		return entity;

	}
	
	// Remove anchored entities on chunk load
	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent evt) {

		Chunk chunk = evt.getChunk();
		for (Entity e : chunk.getEntities()) {

			SQMCEntity ce = getSQMCEntity(e);
			if (ce == null) continue;
			queuedLocations.remove(e);
			entityMap.remove(e);
			previousLocation.remove(e);
			currentLocation.remove(e);
			lastStruck.remove(e);
			anchors.remove(e);
			e.remove();
			
		}

	}
	
	/*EVENTS*/
	@EventHandler
	public void onDrop(PlayerDropItemEvent evt) {
		Player player = evt.getPlayer();
		DataManager dataManager = DataManager.getInstance();
		SessionData sdat = dataManager.getPlayerData(player.getUniqueId()).getSessionData();
		sdat.endConversation();
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent evt) {
		eyeTracking(evt);
		endConversations(evt);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEntityEvent evt) {

		if (evt.isCancelled()) return;
		if (evt.getHand().equals(EquipmentSlot.OFF_HAND)) {
			evt.setCancelled(true);
			return;
		}

		
		SQMCEntity cust = getSQMCEntity(evt.getRightClicked());
		if (cust == null) return;
		
		Entity entity = evt.getRightClicked();
		Player player = evt.getPlayer();
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
		
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
		DataManager dataManager = DataManager.getInstance();
		PlayerData dat = dataManager.getPlayerData(player.getUniqueId());
		SessionData sessionData = dat.getSessionData();
		if (sessionData.getNPC() == null || sessionData.getDialogueNode() == null)
			return;

		if (evt.getTo().distance(sessionData.getNPC().getLocation()) > 7) {

			sessionData.endConversation();

		}
	}
	
	
	/* Death of custom entities */	
	@EventHandler
	public void onDeath(EntityDeathEvent evt) {
		respawn(evt);
	}
	
	@EventHandler
	public void onTarget(EntityTargetEvent evt) {
		SQMCEntity ce = getSQMCEntity(evt.getEntity());
		if(ce == null) return;
		if(ce.isPassive()) evt.setCancelled(true);
	}
	
	public void respawn(EntityDeathEvent evt) {
		
		Entity entity = evt.getEntity();
		SQMCEntity ce = getSQMCEntity(entity);
		if(ce == null) return;
		
		Location anchor = getAnchor(entity);
		if(anchor == null) return;
		if(ce instanceof Respawnable == false) return;
		Respawnable respawnable = (Respawnable) ce;
		queuedLocations.add(anchor);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(SQMC.getPlugin(SQMC.class), new Runnable() {
			public void run() {
				if(!isAlive(anchor) && anchor.getChunk().isLoaded() && respawnable.getLocations().contains(anchor)) {
					spawn(ce, anchor);
				}
				
			}
		}, respawnable.getRespawnDelay());	
		
		entityMap.remove(evt.getEntity());
		anchors.remove(evt.getEntity());
		
	}
	
	private void condtionallyVisible() {
		
		
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
	
	private void eyeTracking(PlayerMoveEvent evt) {
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
	
	private void boundEntities() {
		
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
				
				Location anchor = getAnchor(e);
				if(anchor == null) continue;
				
				double x = e.getLocation().getX();
				double y = e.getLocation().getY();
				double z = e.getLocation().getZ();
				
				Vector v = new Vector(x, y, z).subtract(new Vector(anchor.getX(), anchor.getY(), anchor.getZ()));
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
	
	@EventHandler
	public void onPickup(EntityPickupItemEvent evt) {
		if(evt.getEntity() instanceof Player == false) return;
		Player player = (Player) evt.getEntity();
		DataManager dataManager = DataManager.getInstance();
		SessionData sdat = dataManager.getPlayerData(player.getUniqueId()).getSessionData();
		sdat.endConversation();
	}
	
	// Remove old entities & spawn anchored entities on chunk load
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent evt) {
		
		for(Entity e: evt.getChunk().getEntities()) {
			if(e instanceof LivingEntity && e instanceof Player == false) e.remove();
		}
		
		for (Anchorable anch : getAnchorables()) {
			SQMCEntity ce = (SQMCEntity) anch;
			Chunk chunk = evt.getChunk();
			List<Location> locs = anch.getLocationsWithin(chunk);
			if (locs.isEmpty())
				continue;

			for (Location loc : locs) {
				if(!isAlive(loc) && !queuedLocations.contains(loc)) {
					spawn(ce, loc);
					queuedLocations.remove(loc);
				}
				
			}

		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onStrike(EntityDamageByEntityEvent evt) {
		
		if(evt.isCancelled()) return;
		
		if(evt.getDamager() instanceof Player == false || evt.getEntity() instanceof Player) return;
		
		lastStruck.put(evt.getEntity(), System.currentTimeMillis());
		
	}
	
	/*RUNNABLE*/
	@Override
	public void run() {
		boundEntities();
		condtionallyVisible();
	}
	
	public static EntityManager getInstance() {
		return instance;
	}
	

}
