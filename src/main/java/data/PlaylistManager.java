package data;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import business.Util;

public class PlaylistManager {

	private ArrayList<Playlist> playlists;
	
	private static PlaylistManager playlistManager = new PlaylistManager();
	
	public static PlaylistManager getInstance() {
		return playlistManager;
	}
	
	private PlaylistManager() {
		playlists = new ArrayList<>();
	
		String path = Util.getResourcePath("playlists/test.m3u");
		if (path != null) {
			loadPlaylist(path);
			System.out.println("Playlist geladen: " + path);
		} else {
			System.err.println("Playlist nicht gefunden!");
		}
	}
	
	
	public void loadPlaylist(String m3uFile) {
		playlists.add(new Playlist(m3uFile));
	}
	
	public Playlist getPlaylist(int index) {
		return playlists.get(index);
	}
	
	public Iterator<Playlist> playlistsIterator() {
		return playlists.iterator();
	}
	
}
