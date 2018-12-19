package squeeze.theorem.recipe;

import org.bukkit.Sound;

public enum RecipeType {
	
	SMITHING_ANVIL(Sound.BLOCK_ANVIL_USE, 0.5f), SMITHING_FURNACE(Sound.BLOCK_FIRE_AMBIENT, 5f), COOKING_FIRE(Sound.BLOCK_FIRE_AMBIENT, 5f), CRAFTING_TABLE(Sound.BLOCK_ANVIL_USE, 0.5f);

	private Sound sound;
	private float volume;
	
	RecipeType(Sound sound, float volume){
		setSound(sound);
		setVolume(volume);
	}
	
	public Sound getSound() {
	return this.sound;
	}

	public void setSound(Sound sound) {
		this.sound = sound;
	}

	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}
}