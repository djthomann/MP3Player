package presentation.coverview;

import business.MP3Player;
import data.Track;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class CoverViewController {

	CoverView rootView;
	
	MP3Player mp3Player;
	
	public CoverViewController() {
		rootView = new CoverView();
		
		mp3Player = MP3Player.getInstance();
		
		init();
	}
	
	public void init() {
		
		mp3Player.trackProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(ObservableValue<? extends Track> arg0, Track oldTrack, Track newTrack) {
				rootView.title.setText(newTrack.getTitle());
				rootView.artist.setText(newTrack.getArtist());
				rootView.imageView.setImage(newTrack.getImage());
			}
			
		});
		
	}
	
	public CoverView getRoot() {
		return rootView;
	}
	
}
