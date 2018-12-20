package squeeze.theorem.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import squeeze.theorem.combat.AttackStyle;
import squeeze.theorem.combat.CombatMode;
import squeeze.theorem.combat.CombatStats;
import squeeze.theorem.entity.SQMCEntity;
import squeeze.theorem.item.CombatItem;
import squeeze.theorem.item.CustomItem;
import squeeze.theorem.main.SQMC;
import squeeze.theorem.quest.Quest;
import squeeze.theorem.skill.Skill;
import squeeze.theorem.skill.firemaking.SQMCEntityFire;
import squeeze.theorem.skill.witchcraft.Spellbook;

public class PlayerData implements CombatStats {

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
	private Map<Integer, ItemStack> items = new ConcurrentHashMap<Integer, ItemStack>();

	/* Constructors */
	public PlayerData(UUID id) {
		setUUID(id);
	}
	
	/* Setters and getters */
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
	
	/*Methods*/
	//Levels & XP
	public int getLevel(Skill s) {
		double xp = getXP(s);
		int output = 1;
	
		if (xp >= 0)
			output = 1;
		if (xp >= 83)
			output = 2;
		if (xp >= 174)
			output = 3;
		if (xp >= 276)
			output = 4;
		if (xp >= 388)
			output = 5;
		if (xp >= 512)
			output = 6;
		if (xp >= 650)
			output = 7;
		if (xp >= 801)
			output = 8;
		if (xp >= 969)
			output = 9;
		if (xp >= 1154)
			output = 10;
		if (xp >= 1358)
			output = 11;
		if (xp >= 1584)
			output = 12;
		if (xp >= 1833)
			output = 13;
		if (xp >= 2107)
			output = 14;
		if (xp >= 2411)
			output = 15;
		if (xp >= 2746)
			output = 16;
		if (xp >= 3115)
			output = 17;
		if (xp >= 3523)
			output = 18;
		if (xp >= 3973)
			output = 19;
		if (xp >= 4470)
			output = 20;
		if (xp >= 5018)
			output = 21;
		if (xp >= 5624)
			output = 22;
		if (xp >= 6291)
			output = 23;
		if (xp >= 7028)
			output = 24;
		if (xp >= 7842)
			output = 25;
		if (xp >= 8740)
			output = 26;
		if (xp >= 9730)
			output = 27;
		if (xp >= 10824)
			output = 28;
		if (xp >= 12031)
			output = 29;
		if (xp >= 13363)
			output = 30;
		if (xp >= 14833)
			output = 31;
		if (xp >= 16456)
			output = 32;
		if (xp >= 18247)
			output = 33;
		if (xp >= 20224)
			output = 34;
		if (xp >= 22406)
			output = 35;
		if (xp >= 24815)
			output = 36;
		if (xp >= 27473)
			output = 37;
		if (xp >= 30408)
			output = 38;
		if (xp >= 33648)
			output = 39;
		if (xp >= 37224)
			output = 40;
		if (xp >= 41171)
			output = 41;
		if (xp >= 45529)
			output = 42;
		if (xp >= 50339)
			output = 43;
		if (xp >= 55649)
			output = 44;
		if (xp >= 61512)
			output = 45;
		if (xp >= 67983)
			output = 46;
		if (xp >= 75127)
			output = 47;
		if (xp >= 83014)
			output = 48;
		if (xp >= 91721)
			output = 49;
		if (xp >= 101333)
			output = 50;
		if (xp >= 111945)
			output = 51;
		if (xp >= 123660)
			output = 52;
		if (xp >= 136594)
			output = 53;
		if (xp >= 150872)
			output = 54;
		if (xp >= 166636)
			output = 55;
		if (xp >= 184040)
			output = 56;
		if (xp >= 203254)
			output = 57;
		if (xp >= 224466)
			output = 58;
		if (xp >= 247886)
			output = 59;
		if (xp >= 273742)
			output = 60;
		if (xp >= 302288)
			output = 61;
		if (xp >= 333804)
			output = 62;
		if (xp >= 368599)
			output = 63;
		if (xp >= 407015)
			output = 64;
		if (xp >= 449428)
			output = 65;
		if (xp >= 496254)
			output = 66;
		if (xp >= 547953)
			output = 67;
		if (xp >= 605032)
			output = 68;
		if (xp >= 668051)
			output = 69;
		if (xp >= 737627)
			output = 70;
		if (xp >= 814445)
			output = 71;
		if (xp >= 899257)
			output = 72;
		if (xp >= 992895)
			output = 73;
		if (xp >= 1096278)
			output = 74;
		if (xp >= 1210421)
			output = 75;
		if (xp >= 1336443)
			output = 76;
		if (xp >= 1475581)
			output = 77;
		if (xp >= 1629200)
			output = 78;
		if (xp >= 1798808)
			output = 79;
		if (xp >= 1986068)
			output = 80;
		if (xp >= 2192818)
			output = 81;
		if (xp >= 2421087)
			output = 82;
		if (xp >= 2673114)
			output = 83;
		if (xp >= 2951373)
			output = 84;
		if (xp >= 3258594)
			output = 85;
		if (xp >= 3597792)
			output = 86;
		if (xp >= 3972294)
			output = 87;
		if (xp >= 4385776)
			output = 88;
		if (xp >= 4842295)
			output = 89;
		if (xp >= 5346332)
			output = 90;
		if (xp >= 5902831)
			output = 91;
		if (xp >= 6517253)
			output = 92;
		if (xp >= 7195629)
			output = 93;
		if (xp >= 7944614)
			output = 94;
		if (xp >= 8771558)
			output = 95;
		if (xp >= 9684577)
			output = 96;
		if (xp >= 10692629)
			output = 97;
		if (xp >= 11805606)
			output = 98;
		if (xp >= 13034431)
			output = 99;
	
		return output;
	
	}
	
	public int getTotalLevel() {
		int total = 0;
		for (Skill s : skills.keySet()) {
			total = total + getLevel(s);
		}
		return total;
	}
	
	public void awardXP(Skill s, double amount) {
		
		int initial = getLevel(s);
	
		setXP(s, getXP(s) + amount);
	
		int fnl = getLevel(s);
	
		int difference = fnl - initial;
	
		if (difference == 1) {
			getPlayer().sendMessage(ChatColor.GREEN + "Congraulations! You've just advanced a level in the " + s.getProperName()
					+ " skill! You are now level " + fnl + ".");
		}
	
		if (difference > 1) {
			getPlayer().sendMessage(ChatColor.GREEN + "Congraulations! You've just advanced " + difference + " levels in the "
					+ s.getProperName() + " skill! You are now level " + fnl + ".");
		}
		
	}

	public void awardCombatXP(AttackStyle style, double damage) {
		
		Skill skill = null;
		
		if(style == AttackStyle.RANGED) skill = Skill.ranged;
		if(style == AttackStyle.MAGIC) skill = Skill.witchcraft;
		if(style == AttackStyle.MELEE) skill = Skill.strength;
		
		awardXP(Skill.hitpoints, damage * 1.5);
		
		if (getCombatMode() == CombatMode.AGGRESSIVE) {
			awardXP(skill, damage * 3);
		} else if (getCombatMode() == CombatMode.DEFENSIVE) {
			awardXP(Skill.defense, damage * 3);
			
		} else {
			awardXP(Skill.defense, damage * 1.5);
			awardXP(skill, damage * 1.5);
		}
	
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(getUUID());
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

			getPlayer().setScoreboard(board);
		} catch(Exception exc) {
			System.out.println("Error initializing player scoreboard");
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
		total += 0.1 * getLevel(Skill.defense);
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
			SQMCEntity cust = SQMCEntity.getSQMCEntity(e);
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
	
	public void giveItems(ItemStack[] items) {
		for(ItemStack i: items) {
			giveItem(i);
		}
	}
	
	public void giveItems(List<ItemStack> items) {
		for(ItemStack i: items) {
			giveItem(i);
		}
	}
	
	public void giveItems(CustomItem[] items) {
		for(CustomItem i: items) {
			giveItem(i);
		}
	}
	
	public void giveItem(CustomItem ci, int amount) {
		giveItem(ci.getItemStack(amount));
	}
	
	public void giveItem(CustomItem ci) {
		giveItem(ci.getItemStack());
	}
	
	public void removeItem(CustomItem ci, int amount) {
		
		int total = 0;
		Inventory inv = getPlayer().getInventory();
		for(ItemStack stack: inv.getContents()) {
			if(CustomItem.getCustomItem(stack) == ci) {
				while(stack.getAmount() > 0 && total < amount) {
					stack.setAmount(stack.getAmount() - 1);
					total += 1;
				}
			}
			
		}
		
		
	}
	
	public boolean hasItem(CustomItem ci) {
		return getPlayer().getInventory().containsAtLeast(ci.getItemStack(), 1);
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
	
}
