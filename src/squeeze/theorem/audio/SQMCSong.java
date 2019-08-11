package squeeze.theorem.audio;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;

import squeeze.theorem.main.SQMC;

/**
 * SQMCSong serves as a wrapper class for com.xxmicloxx.NoteBlockAPI.model.Song
 * 
 * @author SqueezeTheorem
 *
 */
public class SQMCSong {

	/*Static fields*/
	private static List<SQMCSong> songs = new ArrayList<SQMCSong>();
	public static SQMCSong amazingGrace = new SQMCSong(parse("amazing_grace"), "Amazing grace");
	public static SQMCSong wisdom = new SQMCSong(parse("wisdom"), "Wisdom");
	public static SQMCSong illFlyAway = new SQMCSong(parse("ill_fly_away"), "I'll Fly Away");
	public static SQMCSong battleHymnOfTheReublic = new SQMCSong(parse("battle_hymn_of_the_republic"), "Battle Hymn of the Republic");
	public static SQMCSong beethoven = new SQMCSong(parse("beethoven"), "Beethoven's 5th");
	public static SQMCSong aviciiLevels = new SQMCSong(parse("avicii_levels"), "Avicii - Levels");
	public static SQMCSong yankeeDoodle = new SQMCSong(parse("yankee_doodle"), "Yankee Doodle Dandy");
	public static SQMCSong aviciiTheDays = new SQMCSong(parse("avicii_the_days"), "Avicii - The Days");
	public static SQMCSong underwater = new SQMCSong(parse("underwater"), "Super Mario Bros - Underwater");
	
	/*Fields*/
	private Song song;
	private String name;
	
	/*Constructors*/
	/**
	 * Creates an instance of SQMCSong using an existing instance of com.xxmicloxx.NoteBlockAPI.model.Song 
	 * @param song Instance of com.xxmicloxx.NoteBlockAPI.model.Song to be used
	 * @param name Name to be displayed on the player's screen in-game when this song starts playing
	 */
	public SQMCSong(Song song, String name) {
		this.setSong(song);
		this.setName(name);
		songs.add(this);
	}

	/*Setters and getter*/
	/**
	 * 
	 * @return An instance of com.xxmicloxx.NoteBlockAPI.model.Song
	 */
	public Song getSong() {
		return song;
	}

	/**
	 * 
	 * @param song An instance of com.xxmicloxx.NoteBlockAPI.model.Song
	 */
	public void setSong(Song song) {
		this.song = song;
	}

	/**
	 * 
	 * @return The display name of this Song
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name The display name of this song
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/*Methods*/
	private static Song parse(String s) {
		
		File f = new File(SQMC.getPlugin(SQMC.class).getDataFolder() + "/res/songs/" + s + ".nbs");
		Song song = NBSDecoder.parse(f);
		return song;
	}
	
	/**
	 * 
	 * @param name The display name of song you're trying to get an instance of
	 * @return The first instance of SQMCSong with a display name of the provided input name (case insensitive). Can return null.
	 */
	public static SQMCSong getSong(String name) {
		for(SQMCSong song: songs) {
			if(song.getName().equalsIgnoreCase(name)) return song;
		}
		return null;
	}
	
}
