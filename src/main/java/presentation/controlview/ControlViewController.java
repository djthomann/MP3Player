package presentation.controlview;

import java.util.Iterator;

import business.MP3Player;
import business.Playmode;
import data.Playlist;
import data.PlaylistManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import presentation.components.PlayerButton;
import presentation.components.SelectableButton;
import presentation.playerview.PlayerView;

public class ControlViewController {

	private static MP3Player mp3Player = MP3Player.getInstance();
	
	private ControlView rootView;
	private PlayerView playerView;
	
	private PlayerButton playButton;
	private Button skipButton;
	private Button rewindButton;
	
	private SelectableButton repeatButton;
	private SelectableButton shuffleButton;
	
	private SelectableButton playlistButton;
	private SelectableButton galleryButton;
	
	private Slider volumeSlider;
	private PlayerButton volumeButton;
	
	public ControlViewController(PlayerView playerView) {
		
		rootView = new ControlView();
		this.playerView = playerView;
		
		playButton = rootView.playButton;
		skipButton = rootView.skipButton;
		rewindButton = rootView.backwardButton;
		
		repeatButton = rootView.repeatButton;
		shuffleButton = rootView.shuffleButton;
		
		playlistButton = rootView.playlistButton;
		galleryButton = rootView.galleryButton;
		
		volumeSlider = rootView.volumeSlider;
		volumeButton = rootView.volumeButton;
		
		init();
	}
	
	public void init() {
		
		playButton.addEventHandler(ActionEvent.ACTION, 
				event ->  {
					mp3Player.play();
				});
		skipButton.addEventHandler(ActionEvent.ACTION, 
				event -> {
					mp3Player.skip();
				});
		rewindButton.addEventHandler(ActionEvent.ACTION, 
				event -> {
					mp3Player.rewind();
				});
		
		shuffleButton.addEventHandler(ActionEvent.ACTION,
				event -> {
					mp3Player.shufflePlaylist();
					shuffleButton.toggle();
				});
		
		repeatButton.addEventHandler(ActionEvent.ACTION,
				event -> {
					mp3Player.cycleRepeatingProperty();
				});
		
		playlistButton.addEventHandler(ActionEvent.ACTION, 
				event -> {
					if(playerView.toggleCenter(playerView.playlistView)) {
						playlistButton.setActive();
						galleryButton.setInactive();
					} else {
						playlistButton.setInactive();
					}
				});
		galleryButton.addEventHandler(ActionEvent.ACTION, 
				event -> {
					if(playerView.toggleCenter(playerView.galleryView)) {
						galleryButton.setActive();
						playlistButton.setInactive();
					} else {
						galleryButton.setInactive();
					}
				});
		
		volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			float floatValue = newValue.floatValue();

			mp3Player.setVolume(floatValue);
			
			if(floatValue == 0) {
				mp3Player.mute();
			} else {
				mp3Player.unmute();
			}
			
			String gradient = String.format("-fx-background-color: linear-gradient(to right, aquamarine %.0f%%, transparent 0%%);", newValue.floatValue() * 100);
			volumeSlider.lookup(".track").setStyle(gradient);
        });
		
		volumeButton.addEventHandler(ActionEvent.ACTION, 
				event -> {
					if(mp3Player.mutedProperty().get()) {
						mp3Player.unmute();
					} else {
						mp3Player.mute();
					}
		});
		
		mp3Player.playingProperty().addListener(
				
				new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> bool, Boolean oldValue, Boolean newValue) {
					if(newValue == true) {
						playButton.setImage(PlayerButton.imageMap.get("pause"));
					} else {
						playButton.setImage(PlayerButton.imageMap.get("play"));
					}
				}
			
		});
		
		mp3Player.mutedProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {
				if(newValue == true) {
					volumeButton.setImage(PlayerButton.imageMap.get("mute"));
				} else {
					
					float floatValue = mp3Player.volumeProperty().get();
					
					if(floatValue > 0.66) {
						volumeButton.setImage(PlayerButton.imageMap.get("maxVolume"));
					} else if(floatValue <= 0.66 && floatValue >= 0.33) {
						volumeButton.setImage(PlayerButton.imageMap.get("mediumVolume"));
					} else if(floatValue < 0.33 && floatValue > 0){
						volumeButton.setImage(PlayerButton.imageMap.get("minVolume"));
					} else if(floatValue == 0){
						volumeButton.setImage(PlayerButton.imageMap.get("mute"));
					}
					
				}
			}
		});
		
		mp3Player.repeatingProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(ObservableValue<? extends Playmode> arg0, Playmode oldMode, Playmode newMode) {
				
				switch(newMode) {
				case STANDARD:
					repeatButton.setInactive();
					repeatButton.setImage(PlayerButton.imageMap.get("repeatQueue"));
					break;
				case PLAYLIST_REPEAT:
					repeatButton.setActive();
					repeatButton.setImage(PlayerButton.imageMap.get("repeatQueue"));
					break;
				case SONG_REPEAT:
					repeatButton.setActive();
					repeatButton.setImage(PlayerButton.imageMap.get("repeatSong"));
					break;
				default:
					break;
				}
			}
		});
		
		mp3Player.volumeProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
				
				float floatValue = newValue.floatValue();
				
				if(floatValue > 0.66) {
					volumeButton.setImage(PlayerButton.imageMap.get("maxVolume"));
				} else if(floatValue <= 0.66 && floatValue >= 0.33) {
					volumeButton.setImage(PlayerButton.imageMap.get("mediumVolume"));
				} else if(floatValue < 0.33 && floatValue > 0){
					volumeButton.setImage(PlayerButton.imageMap.get("minVolume"));
				} else if(floatValue == 0){
					volumeButton.setImage(PlayerButton.imageMap.get("mute"));
				}
				
				volumeSlider.adjustValue(floatValue);
			}
			
		});
		
		mp3Player.playlistProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(ObservableValue<? extends Playlist> arg0, Playlist arg1, Playlist arg2) {
				shuffleButton.setInactive();
			}
			
		});
		
	}
	
	public ControlView getRoot() {
		return rootView;
	}
	
}
