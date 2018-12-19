package squeeze.theorem.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class CustomItemBook extends CustomItem {

	public static final String THE_COMMUNIST_MANIFESTO = "The history of all hitherto existing societ is the history of class struggles.\n" + 
			"Freeman and slave, patrician and plebeian, lord and serf, guild-maste and journeyman, in a word, oppressor and oppressed, stood in constant opposition to one another, carried on an uninterrupted, now hidden, now open fight, a fight that each time ended, either in a revolutionary reconstitution of society at large, or in the common ruin of the contending classes.\n" + 
			"In the earlier epochs of history, we find almost everywhere a complicated arrangement of society into various orders, a manifold gradation of social rank. In ancient Rome we have patricians, knights, plebeians, slaves; in the Middle Ages, feudal lords, vassals, guild-masters, journeymen, apprentices, serfs; in almost all of these classes, again, subordinate gradations.\n" + 
			"The modern bourgeois society that has sprouted from the ruins of feudal society has not done away with class antagonisms. It has but established new classes, new conditions of oppression, new forms of struggle in place of the old ones.\n" + 
			"Our epoch, the epoch of the bourgeoisie, possesses, however, this distinct feature: it has simplified class antagonisms. Society as a whole is more and more splitting up into two great hostile camps, into two great classes directly facing each other — Bourgeoisie and Proletariat.\n" + 
			"From the serfs of the Middle Ages sprang the chartered burghers of the earliest towns. From these burgesses the first elements of the bourgeoisie were developed.\n" + 
			"The discovery of America, the rounding of the Cape, opened up fresh ground for the rising bourgeoisie. The East-Indian and Chinese markets, the colonisation of America, trade with the colonies, the increase in the means of exchange and in commodities generally, gave to commerce, to navigation, to industry, an impulse never before known, and thereby, to the revolutionary element in the tottering feudal society, a rapid development.\n" + 
			"The feudal system of industry, in which industrial production was monopolised by closed guilds, now no longer sufficed for the growing wants of the new markets. The manufacturing system took its place. The guild-masters were pushed on one side by the manufacturing middle class; division of labour between the different corporate guilds vanished in the face of division of labour in each single workshop.\n" + 
			"Meantime the markets kept ever growing, the demand ever rising. Even manufacturer no longer sufficed. Thereupon, steam and machinery revolutionised industrial production. The place of manufacture was taken by the giant, Modern Industry; the place of the industrial middle class by industrial millionaires, the leaders of the whole industrial armies, the modern bourgeois.\n" + 
			"Modern industry has established the world market, for which the discovery of America paved the way. This market has given an immense development to commerce, to navigation, to communication by land. This development has, in its turn, reacted on the extension of industry; and in proportion as industry, commerce, navigation, railways extended, in the same proportion the bourgeoisie developed, increased its capital, and pushed into the background every class handed down from the Middle Ages.\n" + 
			"We see, therefore, how the modern bourgeoisie is itself the product of a long course of development, of a series of revolutions in the modes of production and of exchange.\n" + 
			"Each step in the development of the bourgeoisie was accompanied by a corresponding political advance of that class. An oppressed class under the sway of the feudal nobility, an armed and self-governing association in the medieval commune: here independent urban republic (as in Italy and Germany); there taxable “third estate” of the monarchy (as in France); afterwards, in the period of manufacturing proper, serving either the semi-feudal or the absolute monarchy as a counterpoise against the nobility, and, in fact, cornerstone of the great monarchies in general, the bourgeoisie has at last, since the establishment of Modern Industry and of the world market, conquered for itself, in the modern representative State, exclusive political sway. The executive of the modern state is but a committee for managing the common affairs of the whole bourgeoisie.\n" + 
			"The bourgeoisie, historically, has played a most revolutionary part.\n" + 
			"The bourgeoisie, wherever it has got the upper hand, has put an end to all feudal, patriarchal, idyllic relations. It has pitilessly torn asunder the motley feudal ties that bound man to his “natural superiors”, and has left remaining no other nexus between man and man than naked self-interest, than callous “cash payment”. It has drowned the most heavenly ecstasies of religious fervour, of chivalrous enthusiasm, of philistine sentimentalism, in the icy water of egotistical calculation. It has resolved personal worth into exchange value, and in place of the numberless indefeasible chartered freedoms, has set up that single, unconscionable freedom — Free Trade. In one word, for exploitation, veiled by religious and political illusions, it has substituted naked, shameless, direct, brutal exploitation.\n" + 
			"The bourgeoisie has stripped of its halo every occupation hitherto honoured and looked up to with reverent awe. It has converted the physician, the lawyer, the priest, the poet, the man of science, into its paid wage labourers.\n" + 
			"The bourgeoisie has torn away from the family its sentimental veil, and has reduced the family relation to a mere money relation.\n" + 
			"The bourgeoisie has disclosed how it came to pass that the brutal display of vigour in the Middle Ages, which reactionaries so much admire, found its fitting complement in the most slothful indolence. It has been the first to show what man’s activity can bring about. It has accomplished wonders far surpassing Egyptian pyramids, Roman aqueducts, and Gothic cathedrals; it has conducted expeditions that put in the shade all former Exoduses of nations and crusades.\n" + 
			"The bourgeoisie cannot exist without constantly revolutionising the instruments of production, and thereby the relations of production, and with them the whole relations of society. Conservation of the old modes of production in unaltered form, was, on the contrary, the first condition of existence for all earlier industrial classes. Constant revolutionising of production, uninterrupted disturbance of all social conditions, everlasting uncertainty and agitation distinguish the bourgeois epoch from all earlier ones. All fixed, fast-frozen relations, with their train of ancient and venerable prejudices and opinions, are swept away, all new-formed ones become antiquated before they can ossify. All that is solid melts into air, all that is holy is profaned, and man is at last compelled to face with sober senses his real conditions of life, and his relations with his kind.\n" + 
			"The need of a constantly expanding market for its products chases the bourgeoisie over the entire surface of the globe. It must nestle everywhere, settle everywhere, establish connexions everywhere.\n" + 
			"The bourgeoisie has through its exploitation of the world market given a cosmopolitan character to production and consumption in every country. To the great chagrin of Reactionists, it has drawn from under the feet of industry the national ground on which it stood. All old-established national industries have been destroyed or are daily being destroyed. They are dislodged by new industries, whose introduction becomes a life and death question for all civilised nations, by industries that no longer work up indigenous raw material, but raw material drawn from the remotest zones; industries whose products are consumed, not only at home, but in every quarter of the globe. In place of the old wants, satisfied by the production of the country, we find new wants, requiring for their satisfaction the products of distant lands and climes. In place of the old local and national seclusion and self-sufficiency, we have intercourse in every direction, universal inter-dependence of nations. And as in material, so also in intellectual production. The intellectual creations of individual nations become common property. National one-sidedness and narrow-mindedness become more and more impossible, and from the numerous national and local literatures, there arises a world literature.\n" + 
			"The bourgeoisie, by the rapid improvement of all instruments of production, by the immensely facilitated means of communication, draws all, even the most barbarian, nations into civilisation. The cheap prices of commodities are the heavy artillery with which it batters down all Chinese walls, with which it forces the barbarians’ intensely obstinate hatred of foreigners to capitulate. It compels all nations, on pain of extinction, to adopt the bourgeois mode of production; it compels them to introduce what it calls civilisation into their midst, i.e., to become bourgeois themselves. In one word, it creates a world after its own image.\n" + 
			"The bourgeoisie has subjected the country to the rule of the towns. It has created enormous cities, has greatly increased the urban population as compared with the rural, and has thus rescued a considerable part of the population from the idiocy of rural life. Just as it has made the country dependent on the towns, so it has made barbarian and semi-barbarian countries dependent on the civilised ones, nations of peasants on nations of bourgeois, the East on the West.\n" + 
			"The bourgeoisie keeps more and more doing away with the scattered state of the population, of the means of production, and of property. It has agglomerated population, centralised the means of production, and has concentrated property in a few hands. The necessary consequence of this was political centralisation. Independent, or but loosely connected provinces, with separate interests, laws, governments, and systems of taxation, became lumped together into one nation, with one government, one code of laws, one national class-interest, one frontier, and one customs-tariff.\n" + 
			"\n" + 
			"";
	
	private String text = "";
	private String author = "";
	
	public CustomItemBook(int ID, String name, Material material, String... lore) {
		super(ID, name, material, lore);
	}
	
	@Override
	public ItemStack getItemStack(int i) {
		ItemStack output = super.getItemStack(i);
		BookMeta bm = (BookMeta) output.getItemMeta();
		bm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		bm.setAuthor(getAuthor());
		
		String currentPage = "";
		
		for(char c: text.toCharArray()) {
			
			if(currentPage.length() >= 254) {
				bm.addPage(currentPage);
				currentPage = "";
			}
			
			currentPage += c;
			
		}
	
		

		output.setItemMeta(bm);
		return output;
	}

	public String getText() {
		return text;
	}

	public CustomItemBook setText(String text) {
		this.text = text;
		return this;
	}

	public String getAuthor() {
		return author;
	}

	public CustomItemBook setAuthor(String author) {
		this.author = author;
		return this;
	}

}
