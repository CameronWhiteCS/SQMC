package squeeze.theorem.combat;

public enum CombatMode {
	
	AGGRESSIVE, DEFENSIVE, SPLIT;
	public static CombatMode fromString(String string) {
		for(CombatMode cm: values()) {
			if(cm.toString().equalsIgnoreCase(string)) return cm;
		}
		return null;
	}
	
}
