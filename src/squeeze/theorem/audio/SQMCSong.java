package squeeze.theorem.audio;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;

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
	public SQMCSong(Song song, String name) {
		this.setSong(song);
		this.setName(name);
		songs.add(this);
	}

	/*Setters and getter*/
	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/*Methods*/
	public static Song parse(String s) {
		InputStream stream = SQMCSong.class.getResourceAsStream("/songs/" + s + ".nbs");
		Song song = NBSDecoder.parse(stream);
		return song;
	}
	
	public static SQMCSong getSong(String name) {
		for(SQMCSong song: songs) {
			if(song.getName().equalsIgnoreCase(name)) return song;
		}
		return null;
	}
	
}
