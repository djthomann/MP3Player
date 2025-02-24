

import business.MP3Player;
import business.Util;
import data.PlaylistManager;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import presentation.playerview.PlayerView;

public class PlayerApp extends Application {
	
	private static PlaylistManager playlistManager = PlaylistManager.getInstance();
	private static MP3Player mp3Player = MP3Player.getInstance();
	private static Util util = Util.getInstance();

	public final String APPLICATION_NAME = "MP3-Player von David Thomann";
	public final String APP_ICON_PATH = "src/resources/icons/app_icon_aquamarine.png";
	
	private Parent parent;
	private Stage primaryStage;
	private Scene primaryScene;
	
	private Image icon;
	
	public static void main(String[] args) {
		
		mp3Player.setPlaylist(playlistManager.getPlaylist(0));
		
		launch(args);
		
		System.exit(0); // Beendet alles, wenn Fenster geschlossen wird
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.primaryStage = primaryStage;
		this.primaryStage.setMinWidth(util.FRAME_WIDTH);
		this.primaryStage.setMinHeight(util.FRAME_HEIGHT);
		
		PlayerView playerView = new PlayerView();
		parent = playerView;
		primaryScene = new Scene(parent, util.FRAME_WIDTH, util.FRAME_HEIGHT);
		icon = new Image("file:" + APP_ICON_PATH);
		
		this.primaryStage.getIcons().add(icon);
		this.primaryStage.setTitle(APPLICATION_NAME);
		this.primaryStage.setScene(primaryScene);
		this.primaryStage.show(); 
	}
	
	public Parent getParent() {
		return parent;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public Scene getPrimaryScene() {
		return primaryScene;
	}

	public Image getIcon() {
		return icon;
	}

}
