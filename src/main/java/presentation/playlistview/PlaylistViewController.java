package presentation.playlistview;

import java.util.Iterator;

import business.MP3Player;
import data.Playlist;
import data.PlaylistManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class PlaylistViewController {

	private static MP3Player mp3Player = MP3Player.getInstance();
	private static PlaylistManager playlistManager = PlaylistManager.getInstance();

	private PlaylistView rootView;
	
	public PlaylistViewController() {
		
		rootView = new PlaylistView();
		
		init();
	}
	
	public void init() {
		
		mp3Player.playlistProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(ObservableValue<? extends Playlist> arg0, Playlist oldValue, Playlist newValue) {
				rootView.playlistName.setText(newValue.getTitle());
				rootView.loadCurrentPlaylist();
			}
			
		});
		
		for(Iterator<Playlist> iterator = playlistManager.playlistsIterator(); iterator.hasNext(); ) {
			Playlist playlist = iterator.next();
			playlist.shuffledProperty().addListener(new ChangeListener<>() {

				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
					rootView.loadCurrentPlaylist();
				}
				
			});
		}
		
	}
	
	public PlaylistView getRoot() {
		return rootView;
	}
	
}
