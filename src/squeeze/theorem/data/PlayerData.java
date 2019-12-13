package squeeze.theorem.data;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;

import squeeze.theorem.audio.SQMCSong;
import squeeze.theorem.bank.BankAccount;
import squeeze.theorem.bank.BankDistrict;
import squeeze.theorem.combat.AttackStyle;
import squeeze.theorem.combat.CombatMode;
import squeeze.theorem.combat.CombatStats;
import squeeze.theorem.entity.EntityManager;
import squeeze.theorem.entity.SQMCEntity;
import squeeze.theorem.item.CombatItem;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.main.SQMC;
import squeeze.theorem.quest.Quest;
import squeeze.theorem.recipe.SQMCRecipe;
import squeeze.theorem.region.Region;
import squeeze.theorem.skill.SQMCEntityFire;
import squeeze.theorem.skill.Skill;
import squeeze.theorem.skill.witchcraft.Spellbook;

public class PlayerData implements CombatStats {

	/*Static fields*/
	private static NumberFormat formatter = new DecimalFormat("#0.0");
	
	/* Fields */
	private UUID id;
	private int balance = 25;
	private HashMap<Skill, Double> skills = new HashMap<Skill, Double>(){
		private static final long serialVersionUID = 7661283008007018818L;
		{
			for(Skill s: Skill.getSkills()) {
				put(s, 0.0);
			}
			put(Skill.hitpoints, 388.0);
	}};
	private List<String> flags = new ArrayList<String>();
	private SessionData sessionData = new SessionData(this);
	private Spellbook spellbook = Spellbook.standard;
	private CombatMode combatMode = CombatMode.AGGRESSIVE;
	private int mana = 0;
	private boolean hud = true;
	private SongPlayer songPlayer = null;
	private boolean music = true;
	private VanillaData vanillaData = new VanillaData();
	private Map<Integer, ItemStack> items = new ConcurrentHashMap<Integer, ItemStack>(); //Inventory items
	private HashMap<Skill, BossBar> xpBars;
	private HashMap<Skill, Long> xpBarTimers;
	private boolean showXPBars = true;
	private BankAccount bankAccount = new BankAccount(this);
	
	/* Constructors */
	public PlayerData(UUID id) {
		setUUID(id);
		this.xpBars = new HashMap<Skill, BossBar>();
		for(Skill s: Skill.getSkills()) {
			BossBar bar = Bukkit.createBossBar("", BarColor.BLUE, BarStyle.SEGMENTED_6);
			xpBars.put(s, bar);
		}
		
		this.xpBarTimers = new HashMap<Skill, Long>();
		for(Skill s: Skill.getSkills()) {
			xpBarTimers.put(s, System.currentTimeMillis());
		}
		

	}
	
	/* Setters and getters */
	
	public BankAccount getBankAccount() {
		return this.bankAccount;
	}
	
	public boolean showXPBars() {
		return this.showXPBars;
	}

	public void setShowXPBars(boolean showXPBars) {
		this.showXPBars = showXPBars;
	}
	
	public Map<Skill, BossBar> getXpBars() {
		return xpBars;
	}

	public Map<Skill, Long> getXpBarTimers() {
		return xpBarTimers;
	}
	
	private void setUUID(UUID id) {
		this.id = id;
	}
	
	public UUID getUUID() {
		return id;
	}
	
	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getBalance() {
		return balance;
	}
	
	public void addBalance(int amount) {
		setBalance(getBalance() + amount);
	}
	
	public void setXP(Skill s, double amount) {
		skills.put(s, amount);
	}

	public double getXP(Skill s) {
		return skills.get(s);
	}

	public Spellbook getSpellbook() {
		return spellbook;
	}

	public void setSpellbook(Spellbook spellbook) {
		this.spellbook = spellbook;
	}
	
	public void setCombatMode(CombatMode combatMode) {
		this.combatMode = combatMode;
	}
	
	public CombatMode getCombatMode() {
		return combatMode;
	}
	
	public void setMana(int mana) {
		this.mana = mana;
	}
	
	public int getMana() {
		return this.mana;
	}
	
	public void setHud(boolean hud) {
		this.hud = hud;
	}
	
	public boolean getHud() {
		return hud;
	}
	
	public SessionData getSessionData() {
		return sessionData;
	}
	
	public VanillaData getVanillaData() {
		return vanillaData;
	}

	public void setVanillaData(VanillaData vanillaData) {
		this.vanillaData = vanillaData;
	}
	
	public void setSongPlayer(SongPlayer player) {
		this.songPlayer = player;
	}
	
	public SongPlayer getSongPlayer() {
		return this.songPlayer;
	}
	
	public boolean getMusic() {
		return this.music;
	}
	
	public Map<Integer, ItemStack> getItems(){
		return this.items;
	}
	
	public void setItems(Map<Integer, ItemStack> items) {
		this.items = items;
	}
	
	public void setMusic(boolean music) {
		this.music = music;
		if(music == false && getSongPlayer() != null) {
			SongPlayer sp = getSongPlayer();
			setSongPlayer(null);
			sp.destroy();
		}
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(getUUID());
	}
	
	/*METHODS*/
	/*XP & Level Methods*/
	HashMap<Integer, Integer> xpMap = new HashMap<Integer, Integer>(){
		private static final long serialVersionUID = -612157451553598255L;
		{
			put(1, 0);
			put(2, 83);
			put(3, 174);
			put(4, 276);
			put(5, 388);
			put(6, 512);
			put(7, 650);
			put(8, 801);
			put(9, 969);
			put(10, 1154);
			put(11, 1358);
			put(12, 1584);
			put(13, 1833);	
			put(14, 2107);
			put(15, 2411);
			put(16, 2746);
			put(17, 3115);
			put(18,  3523);
			put(19, 3973);
			put(20, 4470);
			put(21, 5018);
			put(22, 5624);
			put(23, 6291);
			put(24, 7028);
			put(25, 7842);
			put(26, 8740);
			put(27, 9730);
			put(28, 10824);
			put(29, 12031);
			put(30, 13363);
			put(31, 14833);
			put(32, 16456);
			put(33, 18247);
			put(34, 20224);
			put(35, 22406);
			put(36, 24815);
			put(37, 27473);
			put(38, 30408);
			put(39, 33648);
			put(40, 37224);
			put(41, 41171);
			put(42, 45529);
			put(43, 50339);
			put(44, 55649);
			put(45, 61512);
			put(46, 67983);
			put(47, 75127);
			put(48, 83014);
			put(49, 91721);
			put(50, 101333);
			put(51, 111945);
			put(52, 123660);
			put(53, 136594);
			put(54, 150872);
			put(55, 166636);
			put(56, 184040);
			put(57, 203254);
			put(58, 224466);
			put(59, 247886);
			put(60, 273742);
			put(61, 302288);
			put(62, 333804);
			put(63, 368599);
			put(64, 407015);
			put(65, 449428);
			put(66, 496254);
			put(67, 547953);
			put(68, 605032);
			put(69, 668051);
			put(70, 737627);
			put(71, 814445);
			put(72, 899257);
			put(73, 992895);
			put(74, 1096278);
			put(75, 1210421);
			put(76, 1336443);
			put(77, 1475581);
			put(78, 1629200);
			put(79, 1798808);
			put(80, 1986068);
			put(81, 2192818);
			put(82, 2421087);
			put(83, 2673114);
			put(84, 2951373);
			put(85, 3258594);
			put(86, 3597792);
			put(87, 3972294);
			put(88, 4385776);
			put(89, 4842295);
			put(90, 5346332);
			put(91, 5902831);
			put(92, 6517253);
			put(93, 7195629);
			put(94, 7944614);
			put(95, 8771558);
			put(96, 9684577);
			put(97, 10692629);
			put(98, 11805606);
			put(99, 13034431);
			put(100, 13034432);
		}
	};
	
	public int getLevel(Skill s) {
		
		double xp = skills.get(s);
		int level = 1;
		
		for(int i = 1; i <= 99; i++) {
			if(xpMap.get(i) <= xp) level = i;
		}
	
		return level;
	
	}
	
	public int getTotalLevel() {
		int total = 0;
		for (Skill s : skills.keySet()) {
			total = total + getLevel(s);
		}
		return total;
	}
	
	public void awardXP(Skill s, double amount) {
		
		//Variable creation
		int initialLevel = getLevel(s);
		double initialXP = getXP(s);
		
		setXP(s, getXP(s) + amount);
	
		double finalXP = getXP(s);
		int finalLevel = getLevel(s);
	
		int levelDifference = finalLevel - initialLevel;
		double xpDifference = finalXP - initialXP;
		
		//Notify level up
		if (levelDifference == 1) {
			getPlayer().sendMessage(ChatColor.GREEN + "Congraulations! You've just advanced a level in the " + s.getProperName()
					+ " skill! You are now level " + finalLevel + ".");
				
		}
		
		if (levelDifference > 1) {
			getPlayer().sendMessage(ChatColor.GREEN + "Congraulations! You've just advanced " + levelDifference + " levels in the "
					+ s.getProperName() + " skill! You are now level " + finalLevel + ".");
		}
	

		//Update progress bars
		if(showXPBars) {
		
			double denom = xpMap.get(finalLevel + 1) - xpMap.get(finalLevel);
			double num = (xpMap.get(finalLevel + 1) - getXP(s));
			double prog = 1 - (num / denom);
			
			xpBars.get(s).setProgress(prog);
			xpBars.get(s).addPlayer(getPlayer());
			xpBars.get(s).setTitle(String.format("%s+%s %s %s XP (%s%s)", ChatColor.RED, formatter.format(xpDifference), ChatColor.GREEN, s.getProperName(), formatter.format(prog * 100), "%"));
			xpBarTimers.put(s, System.currentTimeMillis());
			
		}
		
	}
	


	public void updateXPBars() {
		for(Skill s: Skill.getSkills()) {
			if(System.currentTimeMillis() - xpBarTimers.get(s) > 5000) {
				xpBars.get(s).removePlayer(getPlayer());
			}
		}
	}
	
	public void awardCombatXP(AttackStyle style, double damage) {
		
		Skill skill = null;
		
		if(style == AttackStyle.RANGED) skill = Skill.ranged;
		if(style == AttackStyle.MAGIC) skill = Skill.witchcraft;
		if(style == AttackStyle.MELEE) skill = Skill.strength;
		
		double k = 1;
		
		awardXP(Skill.hitpoints, damage * k / 4);
		
		if (getCombatMode() == CombatMode.AGGRESSIVE) {
			awardXP(skill, damage * k);
		} else if (getCombatMode() == CombatMode.DEFENSIVE) {
			awardXP(Skill.defense, damage * k);
			
		} else {
			awardXP(Skill.defense, damage * k / 2);
			awardXP(skill, damage * k / 2);
		}
	
	}
	

	//HUD and scoreboard
	public void initializeScoreboard() {
		
		try {
			getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			Scoreboard board = getPlayer().getScoreboard();
			Objective objective = board.registerNewObjective("display", "dummy", ChatColor.GREEN + "SQMC " + SQMC.VERSION);
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			Team mana = board.registerNewTeam("mana");
			mana.addEntry(ChatColor.GREEN + "Mana: " + ChatColor.RED);
			objective.getScore(ChatColor.GREEN + "Mana: " + ChatColor.RED).setScore(0);
			
			Team gold = board.registerNewTeam("gold");
			gold.addEntry(ChatColor.GREEN + "Gold: " + ChatColor.RED);
			objective.getScore(ChatColor.GREEN + "Gold: " + ChatColor.RED).setScore(0);
			
			Team time = board.registerNewTeam("time");
			time.addEntry(ChatColor.GREEN + "Time: " + ChatColor.RED);
			objective.getScore(ChatColor.GREEN + "Time: " + ChatColor.RED).setScore(0);
			
			Team location = board.registerNewTeam("location");
			location.addEntry(ChatColor.GREEN + "Loc: " + ChatColor.RED);
			objective.getScore(ChatColor.GREEN + "Loc: " + ChatColor.RED).setScore(0);
			
			getPlayer().setScoreboard(board);
		} catch(Exception exc) {
			Bukkit.getLogger().log(Level.SEVERE, String.format("[SQMC] Error initializing scoreboard for player %s (uuid='%s').", getPlayer().getName(), getPlayer().getUniqueId()));
			exc.printStackTrace();
		}


	}
	
	public void updateScoreboard() {

		if(getPlayer().getScoreboard() == null) initializeScoreboard();
		if(getPlayer().getScoreboard().getObjective("display") == null) initializeScoreboard();
		
		Scoreboard board = getPlayer().getScoreboard();
		Objective objective = board.getObjective("display");
		
		if(getHud() && objective.getDisplaySlot() == null) {
			objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		}
		
		if(!getHud()) {
			objective.setDisplaySlot(null);
		}
		
		Team mana = board.getTeam("mana");
		mana.setSuffix(getMana() + "");
		
		Team gold = board.getTeam("gold");
		gold.setSuffix(getBalance() + "");
		
		long currentTime = getPlayer().getWorld().getTime() + 6000;
		if(currentTime >= 24000) currentTime = currentTime % 24000;
		long hours = currentTime / 1000;
		int minutes = (int) (((currentTime - (hours*1000.0))/1000.0)*60.0);
		
		String suffix = "";
		if(hours >= 12 && hours < 24) {
			suffix = "PM";
		} else {
			suffix = "AM";
		}
		if(hours > 12) hours = hours % 12;
		
		Team time = board.getTeam("time");
		time.setSuffix(hours + ":" + String.format("%02d", minutes) + " " + suffix);
		
		Team location = board.getTeam("location");
		int x = getPlayer().getLocation().getBlockX();
		int y = getPlayer().getLocation().getBlockY();
		int z = getPlayer().getLocation().getBlockZ();
		location.setSuffix(String.format("(%s, %s, %s)", x, y, z));

	}
	
	//Combat stats
	private ArrayList<CombatItem> getCombatItems() {
		Player player = getPlayer();
		ArrayList<CombatItem> combatItems = new ArrayList<CombatItem>();
	
		for (ItemStack s : player.getInventory().getArmorContents()) {
			CustomItem ci = CustomItem.getCustomItem(s);
			if (ci instanceof CombatItem == false)
				continue;
			if (ci != null) {
				combatItems.add((CombatItem) ci);
			}
		}
	
		CustomItem offhand = CustomItem.getCustomItem(player.getInventory().getItemInOffHand());
		if (offhand != null) {
			if (offhand instanceof CombatItem)
				combatItems.add((CombatItem) offhand);
		}
	
		CustomItem mainhand = CustomItem.getCustomItem(player.getInventory().getItemInMainHand());
		if (mainhand != null) {
			if (mainhand instanceof CombatItem)
				combatItems.add((CombatItem) mainhand);
		}
	
		return combatItems;
	}

	@Override
	public double getAccuracy(AttackStyle style) {
		double total = 3.0;
		for (CombatItem ci : getCombatItems()) {
			if(ci.meetsRequirements(getPlayer())) total += ci.getAccuracy(style);
		} 
		return total;
	}
	


	@Override
	public double getStrength(AttackStyle style) {
		
		double total = 1.0;
		for (CombatItem ci : getCombatItems()) {
			if(ci.meetsRequirements(getPlayer())) total += ci.getStrength(style);
			
		}
		
		if(style == AttackStyle.MELEE) total += (((double) getLevel(Skill.strength)) / 10);
		if(style == AttackStyle.MAGIC) total += (((double) getLevel(Skill.witchcraft)) / 10);
		if(style == AttackStyle.RANGED) total += (((double) getLevel(Skill.ranged)) / 10);
		
		
		return total;
	}

	@Override
	public double getDefense(AttackStyle style) {
		double total = 3.0;
		for (CombatItem ci : getCombatItems()) {
			if(ci.meetsRequirements(getPlayer())) total += ci.getDefense(style);
		}
		total += getLevel(Skill.defense);
		return total;
	}

	@Override
	public double getHealth() {
		return getPlayer().getHealth();
	}
	
	public double getMaxHealth() {
		return Math.ceil(getLevel(Skill.hitpoints) + getBonusHealth());
	}
	
	public double getBonusHealth() {
		double total = 0;
		for (CombatItem ci : getCombatItems()) {
			total += ci.getHealth();
		}
		return total;
	}
	
	public double getHPPerMinute() {

		double total = 0.0;

		total += Math.ceil(getLevel(Skill.hitpoints) * 0.05) + 0;

		double maxFire = 0.0;
		for (Entity e : getPlayer().getNearbyEntities(10, 10, 10)) {
			SQMCEntity cust = EntityManager.getInstance().getSQMCEntity(e);
			if (cust == null)
				continue;
			if (cust instanceof SQMCEntityFire == false)
				continue;
			SQMCEntityFire custFire = (SQMCEntityFire) cust;
			if (custFire.getHealthPerMinute() > maxFire)
				maxFire += custFire.getHealthPerMinute();
		}
		total += maxFire;

		return total;

	}
	
	public void damage(double damage) {
		if(getHealth() < damage) {
			getPlayer().setHealth(0);
		} else {
			getPlayer().damage(damage);
		}
	}
	
	public int getMaxMana() {
		return getLevel(Skill.witchcraft) * 5 + 5;
	}
	
	public int getManaPerSecond() {
		return (int) Math.floor(getLevel(Skill.witchcraft) / 20) + 1;
	}

	//Quest system
	public List<String> getFlags() {
	
		return this.flags;
	
	}

	public void addFlag(String flag) {
		if(flags.contains(flag)) return;
		flags.add(flag);
		for(Quest q: Quest.getQuests()) {
			if(q.getStartFlag().equalsIgnoreCase(flag)) sendTitle(ChatColor.GOLD + "Quest Start", ChatColor.DARK_PURPLE + q.getTitle());
			if(q.getEndFlag().equalsIgnoreCase(flag)) sendTitle(ChatColor.GOLD + "Quest Complete", ChatColor.DARK_PURPLE + q.getTitle());
		}
	}
	
	public void addFlags(String...flags) {
		for(String f: flags) {
			addFlag(f);
		}
	}
	
	public void removeFlag(String flag) {
		if(!this.flags.contains(flag)) flags.remove(flag);
	}
	
	public boolean hasFlags(String...flags) {
		ArrayList<String> list = new ArrayList<String>();
		for(String s: flags) {
			list.add(s);
		}
		
		return this.flags.containsAll(list);
	} 
	
	public void setFlags(List<String> flags) {
		this.flags = flags;
	}
	
	public void sendTitle(String title, String subtitle) {
		getPlayer().sendTitle(title, subtitle, 0, 100, 0);
	}
	
	//Item methods
	public void giveItem(ItemStack stack) {
		if(getPlayer().getInventory().firstEmpty() == -1) {
			getPlayer().getWorld().dropItem(getPlayer().getLocation(), stack);
		} else {
			getPlayer().getInventory().addItem(stack);
		}
	}
	
	public void giveItems(List<ItemStack> items) {
		for(ItemStack i: items) {
			giveItem(i);
		}
	}
	
	public void giveItem(CustomItem ci, int amount) {
		giveItem(ci.getItemStack(amount));
	}
	
	public void giveItem(CustomItem ci) {
		giveItem(ci.getItemStack());
	}
	
	public void removeItem(CustomItem customItem, int amount) {
		
		Inventory inv = getPlayer().getInventory();
		for(ItemStack stack: inv.getContents()) {
			if(amount == 0) return;
			CustomItem ci = CustomItem.getCustomItem(stack);
			if(ci != customItem) continue;
			int count = CustomItem.getCount(stack);
			
			if(count > amount) {
				CustomItem.setCount(stack, count - amount);
				return;
			}
			
			if(count <= amount) {
				CustomItem.setCount(stack, 0);
				amount -= count;
			}
		}
		
		
	}
	
	public boolean hasItem(CustomItem customItem, int amount) {
		
		int total = 0;
		
		Inventory inv = getPlayer().getInventory();
		for(ItemStack stack: inv.getContents()) {
			CustomItem ci = CustomItem.getCustomItem(stack);
			if(ci == null) continue;
			total += CustomItem.getCount(stack);
		}
		
		return (total >= amount);
		
	}

	//Songs
	public Song getCurrentSong() {
		if(getSongPlayer() == null) return null;
		return getSongPlayer().getSong();
	}
	
	public void playSong(Song song){
		
		if(!music) return;
		if(getCurrentSong() == song) return;
		songPlayer = new RadioSongPlayer(song);
		songPlayer.addPlayer(getPlayer());
		songPlayer.setAutoDestroy(true);
		songPlayer.setLoop(true);
		songPlayer.setPlaying(true);
		
	}
	
	public void playSong(SQMCSong song) {
		if(!music) return;
		if(getCurrentSong() == song.getSong()) return;
		playSong(song.getSong());
		getPlayer().sendTitle("", ChatColor.GOLD + song.getName(), 0, 100, 0);
	}

	
	public BankDistrict getBankDistrict() {
		for(Region r: Region.getRegions()) {
			if(r.contains(getPlayer().getLocation())) return r.getBankDistrict();
		}
		return BankDistrict.UPPKOMST;
	}
	
}
