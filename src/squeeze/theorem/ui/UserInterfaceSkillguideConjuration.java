package squeeze.theorem.ui;

import squeeze.theorem.entity.EntityManager;
import squeeze.theorem.skill.conjuration.Familiar;

public class UserInterfaceSkillguideConjuration extends ChestInterface {

	public UserInterfaceSkillguideConjuration(String title) {
		super(title);
		for(Familiar familiar: EntityManager.getInstance().getFamiliars()) {
			this.addComponent(familiar);
		}
	}

}
