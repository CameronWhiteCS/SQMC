package squeeze.theorem.combat;

public interface CombatStats {

	public double getStrength(AttackStyle style);
	
	public double getDefense(AttackStyle style);
	
	public double getAccuracy(AttackStyle style);
	
	public double getHealth();
	
}
