package presentation.galleryview;

import java.util.ArrayList;
import java.util.Iterator;

import business.Util;
import data.Playlist;
import data.PlaylistManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import presentation.components.ImageButton;

public class GalleryView extends BorderPane {

	private static Util util = Util.getInstance();
	private static PlaylistManager playlistManager = PlaylistManager.getInstance();
	
	protected ArrayList<ImageButton<Playlist>> buttons;
	
	private GalleryScrollPane galleryScrollPane;
	private Background background;
	
	public GalleryView() {
		
		background = new Background(new BackgroundFill(new Color(0.2, 0.2, 0.2, 1.0), CornerRadii.EMPTY, Insets.EMPTY));
		this.setBackground(background);
		
		galleryScrollPane = new GalleryScrollPane();
		
		this.setCenter(galleryScrollPane);
		
	}
	
	class GalleryScrollPane extends ScrollPane {
		
		public FlowPane flowPane;
		
		public GalleryScrollPane() {
			
			flowPane = new FlowPane();
			flowPane.setBackground(background);
			this.setBackground(background);
			
			buttons = new ArrayList<>();
			int index = 0;
			for(Iterator<Playlist> iterator = playlistManager.playlistsIterator(); iterator.hasNext();) {
				Playlist playlist = iterator.next();
				buttons.add(new ImageButton<>(playlist, index, playlist.getCover(), util.GALLERY_BUTTON_SIZE_STANDARD));
				index++;
			}
			
			// Dummy-Buttons zum Testen
//			for(int i = 0; i < 30; i++) {
//				buttons.add(new ImageButton(DEFAULT_BUTTON_SIZE));
//			}
			
			flowPane.getChildren().addAll(buttons);
			
			flowPane.setAlignment(Pos.CENTER);
			flowPane.setPadding(new Insets(30));
			flowPane.setHgap(50);
			flowPane.setVgap(10);
			this.setContent(flowPane);
			
			this.setFitToWidth(true);
			this.setFitToHeight(true);
			
		}
		
	}
	
}
