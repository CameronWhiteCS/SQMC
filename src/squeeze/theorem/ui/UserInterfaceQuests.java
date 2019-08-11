package squeeze.theorem.ui;

import squeeze.theorem.quest.Quest;

public class UserInterfaceQuests extends ChestInterface {

	public UserInterfaceQuests(String title) {
		super(title);
		for(Quest q: Quest.getQuests()) {
			this.addComponent(q.getUIComponent());
		}
	}

}
