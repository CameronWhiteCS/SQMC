package squeeze.theorem.skill.witchcraft;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import squeeze.theorem.ui.ChestInterface;
import squeeze.theorem.ui.UIComponent;

public class Spellbook extends ChestInterface {

	/*Static fields*/
	private static ArrayList<Spellbook> spellbooks = new ArrayList<Spellbook>();
	public static Spellbook standard = new SpellbookStandard();
	
	
	/*Fields*/
	private String name;
	private int size;
	private List<UIComponent> spells = new ArrayList<UIComponent>();
	private String identifier;
	
	/*Constructors*/
	public Spellbook(String name, String identifier, int size) {
		super(name);
		setName(name);
		setSize(size);
		setIdentifier(identifier);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Spellbook addSpell(Spell spell) {
		this.spells.add(spell);
		return this;
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public static ArrayList<Spellbook> getSpellbooks() {
		return spellbooks;
	}

	@Override
	public void open(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Inventory getInventory(Player player) {
		Inventory inv = Bukkit.createInventory(null, getSize(), ChatColor.DARK_PURPLE + getTitle(player));
		for(UIComponent ui: spells) {
			inv.addItem(ui.getItemStack(player));
		}
		return inv;
	}

	@Override
	public List<UIComponent> getComponents() {
		return this.spells;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public static Spellbook fromString(String string) {

		for(Spellbook sp: spellbooks) {
			if(sp.getIdentifier().equalsIgnoreCase(string)) return sp;
		}
		
		return null;
	}

}
