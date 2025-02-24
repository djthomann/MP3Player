package presentation.trackview;

import business.MP3Player;
import data.Track;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import presentation.components.ImageButton;
import presentation.components.PlayerButton;

public class TrackViewController {

	private static MP3Player mp3Player = MP3Player.getInstance();
	
	private TrackView rootView;
	private ImageButton<Track> button;
	
	
	public TrackViewController(int index, Track track) {
		
		rootView = new TrackView(index, track);
		button = rootView.button;
		
		init();
	}
	
	public void init() {
		button.addEventHandler(ActionEvent.ACTION, 
				event -> {
					mp3Player.play(button.getIndex());
				});
		
		rootView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) { 
                	mp3Player.play(button.getIndex());
                }
            }
        });
		
		rootView.setOnMouseEntered(event -> {
			if(rootView.hasCurrentSong) {
				rootView.setBackground(TrackView.backgrounds.get("hoverPlayBackground"));
			} else {				
				rootView.setBackground(TrackView.backgrounds.get("hoverBackground"));
			}
		});
		
		rootView.setOnMouseExited(event -> {
			if(rootView.hasCurrentSong) {
				rootView.setBackground(TrackView.backgrounds.get("normalPlayBackground"));
			} else {				
				rootView.setBackground(TrackView.backgrounds.get("normalBackground"));
			}
		});
		
		mp3Player.trackProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(ObservableValue<? extends Track> arg0, Track oldValue, Track newValue) {
				if(newValue.equals(rootView.getTrack())) {
					rootView.hasCurrentSong = true;
					rootView.setBackground(TrackView.backgrounds.get("normalPlayBackground"));
				} else {
					rootView.hasCurrentSong = false;
					rootView.setBackground(TrackView.backgrounds.get("normalBackground"));
				}
			}
			
		});
		
		mp3Player.playingProperty().addListener(
				
				new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> bool, Boolean oldValue, Boolean newValue) {
					if(mp3Player.trackProperty().get().equals(rootView.getTrack())) {
						if(newValue == true) {
							button.setImage(PlayerButton.imageMap.get("pause_white"));
						} else {
							button.setImage(PlayerButton.imageMap.get("play_white"));
						}
					}
				}
			
		});
	}
	
	public TrackView getRoot() {
		return rootView;
	}
	
}
