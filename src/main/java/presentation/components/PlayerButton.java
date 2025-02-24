package presentation.components;

import java.util.HashMap;

import business.Util;
import javafx.animation.FillTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class PlayerButton extends Button {
	
	protected static Util util = Util.getInstance();;
	
	private int size;
	
	protected static final Color IDLE_COLOR = util.IDLE_COLOR;
	protected static final Color HOVER_COLOR = util.HOVER_COLOR;
	
	public static HashMap<String, Image> imageMap = new HashMap<>();
	
	private FillTransition fillTransition;
	private Shape shape;
	
	protected StackPane stackPane;
	private Image backgroundImage;
	private ImageView backgroundImageView;
	
	public PlayerButton() {
		this(util.PLAYER_BUTTON_SIZE_STANDARD);
	}
	
	public PlayerButton(int size) {
        super();
        
        String cssPath = Util.getResourceUrl("styles/button.css");
		if (cssPath != null) {
			this.getStylesheets().add(cssPath);
		} else {
			System.err.println("Stylesheet nicht gefunden!");
		}
        
        this.size = size;

        stackPane = new StackPane();

        shape = new Circle(size, IDLE_COLOR);

        if(imageMap.size() == 0) {
        	putImages();
        }
		
        fillTransition = new FillTransition(Duration.seconds(0.4), shape);
        fillTransition.setFromValue(IDLE_COLOR);
        fillTransition.setToValue(HOVER_COLOR);

        this.setOnMouseEntered(event -> {
            fillTransition.setRate(1);
            fillTransition.play();
        });

        this.setOnMouseExited(event -> {
            fillTransition.setRate(-1);
            fillTransition.play();
        });
        
        backgroundImageView = new ImageView();
        backgroundImageView.setPreserveRatio(true);
        backgroundImageView.setFitWidth(size);
        backgroundImageView.setFitHeight(size);
        
    	stackPane.getChildren().addAll(shape, backgroundImageView);
    	this.setGraphic(stackPane);
        
        this.setShape(shape);
        this.setText("");
    }
	
	public void putImages() {
		imageMap.put("play", new Image("file:" + Util.getResourcePath("icons/play.png")));
		imageMap.put("play_white", new Image("file:" + Util.getResourcePath("icons/play_white.png")));
		imageMap.put("pause", new Image("file:" + Util.getResourcePath("icons/pause.png")));
		imageMap.put("pause_white", new Image("file:" + Util.getResourcePath("icons/pause_white.png")));
		imageMap.put("skip", new Image("file:" + Util.getResourcePath("icons/forward_skip.png")));
		imageMap.put("rewind", new Image("file:" + Util.getResourcePath("icons/backward_skip.png")));
		imageMap.put("minVolume", new Image("file:" + Util.getResourcePath("icons/volume_min.png")));
		imageMap.put("mediumVolume", new Image("file:" + Util.getResourcePath("icons/volume_medium.png")));
		imageMap.put("maxVolume", new Image("file:" + Util.getResourcePath("icons/volume_max.png")));
		imageMap.put("mute", new Image("file:" + Util.getResourcePath("icons/muted.png")));
		imageMap.put("repeatQueue", new Image("file:" + Util.getResourcePath("icons/repeat_queue.png")));
		imageMap.put("repeatSong", new Image("file:" + Util.getResourcePath("icons/repeat_song.png")));
		imageMap.put("shuffle", new Image("file:" + Util.getResourcePath("icons/shuffle.png")));
		imageMap.put("playlist", new Image("file:" + Util.getResourcePath("icons/playlist.png")));
		imageMap.put("gallery", new Image("file:" + Util.getResourcePath("icons/gallery.png")));
	}
	
	
	public void setImage(String path) {
		backgroundImage = new Image("file:src/icons/" + path);
        backgroundImageView.setImage(backgroundImage);
	}
	
	public void setImage(Image img) {
		backgroundImageView.setImage(img);
	}
	
	public Image getImage() {
		return backgroundImageView.getImage();
	}
	
	public int getSize() {
		return size;
	}
	
}
