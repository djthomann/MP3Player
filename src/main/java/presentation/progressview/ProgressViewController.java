package presentation.progressview;

import business.MP3Player;
import business.Util;
import data.Track;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

public class ProgressViewController {

	private static Util util = Util.getInstance();
	private static MP3Player mp3Player = MP3Player.getInstance();
	
	private ProgressView rootView;
	
	private Text songTime;
	private Text currentTime;
	private ProgressBar progressBar;
	
	public final double PROGRESS_BAR_OFFSET = 0.01;
	
	public ProgressViewController() {
		
		rootView = new ProgressView();
		
		progressBar = rootView.bar;
		songTime = rootView.songTime;
		currentTime = rootView.currentTime;
		
		init();
	}
	
	public void init() {
		
		mp3Player.trackProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(ObservableValue<? extends Track> arg0, Track oldTrack, Track newTrack) {
				progressBar.setProgress(0);
				songTime.setText(newTrack.getFormattedTime());
				currentTime.setText("00:00");
			}
			
		});
		
		mp3Player.currentMilliSecondsProperty().addListener(new ChangeListener<>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
				
				progressBar.setProgress(mp3Player.ratioFinished() + PROGRESS_BAR_OFFSET);
				currentTime.setText(util.formatMillis(newValue.intValue()));
				
			}			
			
		});
		
		progressBar.setOnMouseClicked(event -> {
			
            double mouseX = event.getX();
            double progressBarWidth = progressBar.getWidth();
            double progress = mouseX / progressBarWidth;
            mp3Player.skipTo(progress);
            progressBar.setProgress(progress + PROGRESS_BAR_OFFSET);

        });
	}
	
	public ProgressView getRoot() {
		return rootView;
	}
	
}
