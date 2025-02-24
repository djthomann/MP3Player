package presentation.coverview;

import business.MP3Player;
import business.Util;
import data.Track;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CoverView extends BorderPane {

	private static Util util = Util.getInstance();
	private static MP3Player mp3Player = MP3Player.getInstance();
	
	protected Image image;
	protected ImageView imageView;
	protected Track track;
	protected ArtistInfo info;
	protected Text artist;
	protected Text title;
	
	public CoverView() {
		
		track = mp3Player.trackProperty().get();
		
		imageView = new ImageView(track.getImage());
		imageView.setFitHeight(util.COVER_IMAGE_SIZE);
		imageView.setFitWidth(util.COVER_IMAGE_SIZE);		
		
		info = new ArtistInfo();
		
		this.setCenter(imageView);
		this.setBottom(info);
	}	
	
	public class ArtistInfo extends VBox {
		
		public ArtistInfo() {
			
			this.getStylesheets().add(Util.getResourcePath("styles/text.css"));
			
			title = new Text(track.getTitle());
			artist = new Text(track.getArtist());
			title.getStyleClass().add("headline");
			
			this.setPadding(new Insets(10));
			this.setSpacing(15);
			setAlignment(Pos.CENTER);
			
			this.getChildren().addAll(title, artist);
		}
		
	}
	
}
