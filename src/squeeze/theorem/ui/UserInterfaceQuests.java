package squeeze.theorem.ui;

import squeeze.theorem.quest.Quest;

public class UserInterfaceQuests extends MultiPageInterface {

	public UserInterfaceQuests(String title, int size) {
		super(title, size);
		for(Quest q: Quest.getQuests()) {
			this.addComponent(q.getUIComponent());
		}
	}

}
