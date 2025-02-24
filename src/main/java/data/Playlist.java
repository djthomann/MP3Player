package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import business.MP3Player;
import business.Util;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;

public class Playlist {

	private static Util util = Util.getInstance();
	
	private ArrayList<Track> tracks;
	private ArrayList<Track> unshuffledTracks;
	private String title;
	
	@SuppressWarnings("unused")
	private String m3uFile;
	
	private Image cover;
	private static String STANDARD_PLAYLIST_COVER_PATH = "src/resources/images/default_playlist_icon.jpg";
	
	private SimpleBooleanProperty shuffled;
	
	public Playlist(String m3uFile) {

		System.out.println(m3uFile);

		this.m3uFile = m3uFile;
		
		title = util.readFileNameFromPath(m3uFile);
		
		tracks = new ArrayList<>();
		unshuffledTracks = new ArrayList<>();
		
		shuffled = new SimpleBooleanProperty(false);
		
		List<String> paths = readM3UFile(m3uFile);
		
		if(paths.get(0).startsWith("file")) {
			cover = new Image(paths.get(0));
			paths.remove(0);
		} else {
			cover = new Image("file:" + STANDARD_PLAYLIST_COVER_PATH);
		}
			
		
		for(String path : paths) {
			tracks.add(new Track(path));
		}
		
		for(String path : paths) {
			unshuffledTracks.add(new Track(path));
		}
		
	}
	
	private static List<String> readM3UFile(String filePath) {
		List<String> songPaths = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.startsWith("#")) {
					songPaths.add(line.trim());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return songPaths;
	}
	
	public void toggleShuffle() {
		if(shuffled.get()) {
			unshuffle();
		} else {
			shuffle();
		}
	}
	
	public void unshuffle() {
		tracks.clear();
		for(Track track : unshuffledTracks) {
			tracks.add(track);
		}
		shuffled.set(false);
	}
	
	public void shuffle() {
		Collections.shuffle(tracks);
		shuffled.set(true);
	}
	
	public int numberOfTracks() {
		return tracks.size();
	}
	
	public Track getTrack(int index) {
		return tracks.get(index);
	}
	
	public Iterator<Track> trackIterator() {
		return tracks.iterator();
	}

	public Image getCover() {
		return cover;
	}
	
	public String getTitle() {
		return title;
	}
	
	public SimpleBooleanProperty shuffledProperty() {
		return shuffled;
	}
	
}
