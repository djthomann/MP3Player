package presentation.galleryview;

import java.util.ArrayList;

import business.MP3Player;
import data.Playlist;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.layout.Pane;
import presentation.components.ImageButton;
import presentation.components.PlayerButton;

public class GalleryViewController {
	
	private static MP3Player mp3Player = MP3Player.getInstance();
	
	private GalleryView rootView;
	private ArrayList<ImageButton<Playlist>> buttons;
	
	public GalleryViewController() {
		
		rootView = new GalleryView();
		
		buttons = rootView.buttons;
		
		init();
	}
	
	public void init() {
		
		for(ImageButton<Playlist> imageButton : buttons) {
			imageButton.addEventHandler(ActionEvent.ACTION, 
					event -> {
						if(mp3Player.playlistProperty().get().equals(imageButton.getObject())) {
							// aktueller Playlist ist Playlist von Button
							// --> play / pause
							mp3Player.play();
						} else {
							// aktueller Playlist ist nicht Playlist von Button
							// --> laden und dann play
							mp3Player.pause();
							mp3Player.playlistProperty().get().unshuffle();
							mp3Player.setPlaylist(imageButton.getObject());
							mp3Player.play();
						}
					});
			
		}	
		
		mp3Player.playingProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {
				for(ImageButton<Playlist> imageButton : buttons) {
					if(imageButton.getObject().equals(mp3Player.playlistProperty().get())) {
						if(newValue == true) {
							imageButton.setImage(PlayerButton.imageMap.get("pause_white"));
						} else {
							imageButton.setImage(PlayerButton.imageMap.get("play_white"));
						}
					}
				}
			}
			
		});
		
	}
	
	public Pane getRoot() {
		return rootView;
	}
	
}
