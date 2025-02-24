package presentation.progressview;

import business.MP3Player;
import business.Util;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ProgressView extends HBox {

	private static MP3Player mp3Player = MP3Player.getInstance();

	protected Text currentTime;
	protected Text songTime;
	
	public ProgressBar bar;	
	
	public ProgressView() {
		
		String cssPath = Util.getResourceUrl("styles/progressBar.css");
		if (cssPath != null) {
			this.getStylesheets().add(cssPath);
		} else {
			System.err.println("Stylesheet nicht gefunden!");
		}
		cssPath = Util.getResourceUrl("styles/text.css");
		if (cssPath != null) {
			this.getStylesheets().add(cssPath);
		} else {
			System.err.println("Stylesheet nicht gefunden!");
		}
		
		setAlignment(Pos.CENTER);
		
		currentTime = new Text("00:00");
		songTime = new Text(mp3Player.trackProperty().get().getFormattedTime());
		
		currentTime.setFill(Color.WHITE);
		songTime.setFill(Color.WHITE);
		
		bar = new ProgressBar();
		bar.setPrefSize(600, 30);
		bar.setProgress(0);
		
		this.getChildren().addAll(	currentTime,
									bar,
									songTime);
		

		this.setPadding(new Insets(20));
		this.setSpacing(20);
		
	}
	
}
