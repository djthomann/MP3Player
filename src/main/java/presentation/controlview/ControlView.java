package presentation.controlview;

import business.MP3Player;
import business.Util;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import presentation.components.PlayerButton;
import presentation.components.SelectableButton;
import presentation.progressview.ProgressView;
import presentation.progressview.ProgressViewController;

public class ControlView extends GridPane {
	
	private static Util util = Util.getInstance();
	private static MP3Player mp3Player = MP3Player.getInstance();
	
	private ProgressViewController progressViewController;
	public ProgressView progressView;
	
	public InterfaceControls interfaceControls;
	public SongControls songControls;
	public VolumeControls volumeControls;
	
	protected SelectableButton playlistButton;
	protected SelectableButton galleryButton;
	
	protected PlayerButton playButton;
	protected PlayerButton backwardButton;
	protected PlayerButton skipButton;
	
	protected SelectableButton repeatButton;
	protected SelectableButton shuffleButton;
	
	protected Slider volumeSlider;
	protected PlayerButton volumeButton;
	
	public ControlView() {
		
		ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20); 

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(80); 

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(20); 
		
        this.getColumnConstraints().addAll(col1, col2, col3);
        
        progressViewController = new ProgressViewController();
        progressView = progressViewController.getRoot();
        
        interfaceControls = new InterfaceControls();
		songControls = new SongControls();
		volumeControls = new VolumeControls();
		
		setColumnIndex(interfaceControls, 0);
		setColumnIndex(progressView, 1);
		setColumnIndex(volumeControls, 2);
		
		this.getChildren().addAll(
				progressView,
				interfaceControls,
				volumeControls);
		
		this.setPadding(new Insets(0, util.STANDARD_PADDING, util.STANDARD_PADDING, util.STANDARD_PADDING));
	}
	
	public class InterfaceControls extends HBox {
		
		public InterfaceControls() {
			
			playlistButton = new SelectableButton();
			galleryButton = new SelectableButton();
			
			playlistButton.setImage(PlayerButton.imageMap.get("playlist"));
			galleryButton.setImage(PlayerButton.imageMap.get("gallery"));
			
			this.getChildren().addAll(galleryButton, playlistButton);
		}
		
	}
	
	public class SongControls extends HBox {
		
		public SongControls() {
			
			HBox.setHgrow(this, Priority.NEVER);
			setAlignment(Pos.CENTER);
			playButton = new PlayerButton(util.PLAYER_BUTTON_SIZE_LARGE);
			skipButton = new PlayerButton();
			backwardButton = new PlayerButton();
			repeatButton = new SelectableButton();
			shuffleButton = new SelectableButton();
			
			playButton.setImage(PlayerButton.imageMap.get("play"));
			skipButton.setImage(PlayerButton.imageMap.get("skip"));
			backwardButton.setImage(PlayerButton.imageMap.get("rewind"));
			shuffleButton.setImage(PlayerButton.imageMap.get("shuffle"));
			repeatButton.setImage(PlayerButton.imageMap.get("repeatQueue"));
			
			this.getChildren().addAll(
					repeatButton,
					backwardButton,
					playButton,
					skipButton,
					shuffleButton);
		}
		
	}
	
	public class VolumeControls extends HBox  {
		
		public VolumeControls() {
			
			this.getStylesheets().add(Util.getResourcePath("styles/slider.css"));
			
			setAlignment(Pos.CENTER_RIGHT);
			
			volumeButton = new PlayerButton();	
			volumeButton.setImage(PlayerButton.imageMap.get("mediumVolume"));
			
			volumeSlider = new Slider(0, 1, 0.1);
			volumeSlider.setValue(mp3Player.INITIAL_VOLUME);
			
			this.getChildren().addAll(volumeButton, volumeSlider);
			
		}
		
	}
}
