package presentation.trackview;

import java.util.HashMap;
import business.MP3Player;
import business.Util;
import data.Track;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import presentation.components.ImageButton;

public class TrackView extends GridPane {

	private static Util util = Util.getInstance();
	
	protected Text index;
	protected Track track;
	protected Text songName;
	protected Text artistName;
	protected Text albumName;
	protected Text songDuration;
	protected boolean hasCurrentSong;
	
	private static ColumnConstraints col1;
	private static ColumnConstraints col2;
	private static ColumnConstraints col3;
	private static ColumnConstraints col4;
	private static ColumnConstraints col5;
	private static CornerRadii radii;
	private static RowConstraints rowConstraints = new RowConstraints(100);
	
	protected ImageButton<Track> button;
	protected static HashMap<String, Background> backgrounds = new HashMap<>();
	
	public TrackView() {
		
		this(0, null);
		
	}
	
	public TrackView(int index,  Track track) {
		
		this.getStylesheets().add(Util.getResourcePath("styles/text.css"));
		
		this.track = track;
		
		radii = new CornerRadii(20);
		
		backgrounds.put("normalBackground", new Background(new BackgroundFill(Color.TRANSPARENT, radii, Insets.EMPTY)));
		backgrounds.put("hoverBackground", new Background(new BackgroundFill(new Color(0.3, 0.3, 0.3, 1.0), radii, Insets.EMPTY)));
		backgrounds.put("normalPlayBackground", new Background(new BackgroundFill(Color.DARKCYAN.darker(), radii, Insets.EMPTY)));
		backgrounds.put("hoverPlayBackground", new Background(new BackgroundFill(Color.DARKCYAN, radii, Insets.EMPTY)));
		
		if(track == MP3Player.getInstance().trackProperty().get()) {
			this.setBackground(backgrounds.get("playBackground"));
		} else {
			this.setBackground(backgrounds.get("normalBackground"));
		}
		      
		col1 = new ColumnConstraints();
		col2 = new ColumnConstraints();
		col3 = new ColumnConstraints();
		col4 = new ColumnConstraints();
		col5 = new ColumnConstraints();
		
        col1.setPercentWidth(3);
        col2.setPercentWidth(20); 
        col3.setPercentWidth(32); 
        col4.setPercentWidth(30); 
        col5.setPercentWidth(15);
        
        col1.setHgrow(Priority.ALWAYS);
        col2.setHgrow(Priority.ALWAYS);
        col3.setHgrow(Priority.ALWAYS);
        col4.setHgrow(Priority.ALWAYS);
        col5.setHgrow(Priority.ALWAYS);
        
        this.getColumnConstraints().addAll(col1, col2, col3, col4, col5);
        
        rowConstraints.setVgrow(Priority.ALWAYS);
        this.getRowConstraints().add(rowConstraints);
		
		this.index = new Text(Integer.toString(index + 1));
		button = new ImageButton<>(track, index, track.getImage());
		songName = new Text(track.getTitle());
		albumName = new Text(track.getAlbum());
		artistName = new Text(track.getArtist());
		songDuration = new Text(util.formatSeconds(track.getLengthInSeconds()));

		setColumnIndex(this.index, 0);
		setColumnIndex(button, 1);
		setColumnIndex(songName, 2);
		setColumnIndex(artistName, 3);
		setColumnIndex(albumName, 3);
		setColumnIndex(songDuration, 4);
		
		GridPane.setHalignment(this.index, HPos.RIGHT);
		GridPane.setHalignment(button, HPos.CENTER);
		GridPane.setHalignment(songDuration, HPos.RIGHT);
		
		HBox.setHgrow(songName, Priority.ALWAYS);
		
		this.getChildren().addAll(	this.index,
									button,
									songName,
									artistName,
									songDuration);
		
		this.setPadding(new Insets(util.STANDARD_PADDING));
			
	}
	
	public Track getTrack() {
		return track;
	}
	
	public Text getIndex() {
		return index;
	}
	
	public boolean isHasCurrentSong() {
		return hasCurrentSong;
	}

	public void setHasCurrentSong(boolean hasCurrentSong) {
		this.hasCurrentSong = hasCurrentSong;
	}
	
	public static Background getBackground(String key) {
		return backgrounds.get(key);
	}
	
}
