package presentation.playlistview;

import java.util.ArrayList;
import java.util.Iterator;

import business.MP3Player;
import business.Util;
import data.Playlist;
import data.Track;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import presentation.trackview.TrackView;
import presentation.trackview.TrackViewController;

public class PlaylistView extends BorderPane {

	private static MP3Player mp3Player = MP3Player.getInstance();
	private static Util util = Util.getInstance();
	
	protected Playlist currentPlaylist;
	protected Text playlistName;
	protected SonglistView songlistView;
	protected ArrayList<TrackViewController> trackViewController = new ArrayList<>();
	
	protected VBox songlist;
	private Background background;
	
	public PlaylistView() {
		
		background = new Background(new BackgroundFill(new Color(0.2, 0.2, 0.2, 1.0), CornerRadii.EMPTY, Insets.EMPTY));
		this.setBackground(background);
		
		currentPlaylist = mp3Player.playlistProperty().get();
		playlistName = new Text(currentPlaylist.getTitle());
		playlistName.setStyle("-fx-font: 36 helvetica; -fx-font-weight: bold; -fx-fill: lightgray");
		playlistName.setTextAlignment(TextAlignment.LEFT);
		setMargin(playlistName, new Insets(util.STANDARD_PADDING, 0, 0, util.LARGE_PADDING));
		
		songlistView = new SonglistView();
		
		this.setTop(playlistName);
		this.setCenter(songlistView);
		
	}
	
	public class SonglistView extends ScrollPane {		
		
		public SonglistView() {
			
			this.getStylesheets().add(Util.getResourcePath("styles/scroll.css"));
	
			songlist = new VBox();
			songlist.setPadding(new Insets(util.STANDARD_PADDING));
			this.setContent(songlist);
			this.setFitToWidth(true);
			this.setFitToHeight(true);
			
			background = new Background(new BackgroundFill(new Color(0.2, 0.2, 0.2, 1.0), CornerRadii.EMPTY, Insets.EMPTY));
			songlist.setBackground(background);
			
			loadCurrentPlaylist();
			
		}
		
	}
	
	public void loadCurrentPlaylist() {
		currentPlaylist = mp3Player.playlistProperty().get();
		
		trackViewController.clear();
		songlist.getChildren().clear();
		
		int i = 0;
		for(Iterator<Track> iterator = currentPlaylist.trackIterator(); iterator.hasNext();) {
			Track track = iterator.next();
			trackViewController.add(new TrackViewController(i++, track));
		}
		
		for(TrackViewController controller : trackViewController) {
			if(controller.getRoot().getTrack().equals(mp3Player.trackProperty().get())) {
				controller.getRoot().setHasCurrentSong(true);
				controller.getRoot().setBackground(TrackView.getBackground("normalPlayBackground"));
			}
			songlist.getChildren().add(controller.getRoot());
		}
	}
}
