package squeeze.theorem.quest;

import org.bukkit.Material;

public class QuestEmbargo extends Quest{

	public QuestEmbargo() {
		
		setTitle("Embargo");
		setStartFlag("quest.embargo.start");
		setEndFlag("quest.embargo.end");
		setMaterial(Material.IRON_INGOT);
		setDescription("Foreign trade policy sends Brightbriar's", "business into shambles");
		setStartPoint("Brightbriar Mining Executive");
		setReward("100 gold, 250 mining xp");
		
		addQuestLogEntry("quest.embargo.start", "I talked to §9Brightbriar§r, the mining executive. He hired me for the day, and wants me to fetch him some mail from the §aMail Carrier§r from the Uppkomst docks.");
		addQuestLogEntry("quest.embargo.receive_embargo_letter", "I picked up the §6Brightbriar letter§r from the §aMail Carrier§r. I should deliver it to Brightbriar.");
		addQuestLogEntry("quest.embargo.deliver_embargo_letter", "I devliered a letter to Brightbriar, and he wants to take a moment to read it before sending me off to do anything else.");
		addQuestLogEntry("quest.embargo.receive_layoff_notice", "Turns out the letter I delivered to Brightbriar was an extraordinarily bad piece of news. Foreign tariffs on Uppkomst steel mean a majority of his men have to be let go, and I have to tell them using this §6Layoff notice§r he gave me.");
		addQuestLogEntry("quest.embargo.deliver_layoff_notice", "I delivered the §6Layoff notice§r to the §aUnion Miners§r, and they didn't take it well. They started rambling about someone named \"Melvin,\" but I didn't catch most of what they were saying. I should go back to Brightbriar.");
		addQuestLogEntry("quest.embargo.end", "After I informed his former employees that their days as miners were over, Brightbriar thanked me for doing his dirty work for him, and paid me 100 gold. I gained 250 mining experience. §5Quest complete!");
	}

}
