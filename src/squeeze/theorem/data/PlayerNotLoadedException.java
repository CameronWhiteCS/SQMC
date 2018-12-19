package squeeze.theorem.data;

import org.bukkit.entity.Player;

public class PlayerNotLoadedException extends RuntimeException {

	/*Static fields*/
	private static final long serialVersionUID = -1197162673061069774L;
	
	/*Fields*/
	private Player player;
	
	public PlayerNotLoadedException(Player player) {
		setPlayer(player);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
