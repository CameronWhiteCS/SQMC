package squeeze.theorem.skill.witchcraft;

public class SpellbookStandard extends Spellbook {

	public SpellbookStandard() {
		super("Standard Spellbook", "spellbook.default", 45);
		this.addSpell(Spell.uppkomstTeleport);
		this.addSpell(Spell.arcanopulse);
		this.addSpell(Spell.electricPotential);
		this.addSpell(Spell.none);
	}

}
