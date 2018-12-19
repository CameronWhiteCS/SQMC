
package squeeze.theorem.combat;

public enum AttackStyle {
	
	MELEE("Melee"), RANGED("Ranged"), MAGIC("Magic");
	
	private String properName;
	
	AttackStyle(String properName) {
		this.properName = properName;
	}
	
	public String getProperName() {
		return this.properName;
	}
	
}
