package presentation.components;

import business.Util;
import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ImageButton<T> extends Button {

	private static Util util = Util.getInstance();
	
	private T myObject;
	private int index;
	private Image image;
	private int size;
	
	static final int STANDARD_HEIGHT = 100;
	static final String DEFAULT_ICON_PATH = Util.getResourcePath("images/default_playlist_icon.jpg");
	
	private ImageView imageView;
	private ImageView iconView;
	private StackPane stackPane;
	
	public ImageButton() {
		this(null, 0, "file:" + DEFAULT_ICON_PATH, util.IMAGE_BUTTON_STANDARD_HEIGHT);
	}
	
	public ImageButton(T object, int size) {
		this(object, 0, "file:src/resources/images/default_playlist_icon.jpg", size);
	}
	
	public ImageButton(T object, int index, String path) {
		this(object, index, new Image(path), util.IMAGE_BUTTON_STANDARD_HEIGHT);
	}
	
	public ImageButton(T object, int index, Image image) {
		this(object, index, image, util.IMAGE_BUTTON_STANDARD_HEIGHT);
	}
	
	public ImageButton(T object, int index, String path, int size) {
		this(object, index, new Image(path), size);
	}
	
	public ImageButton(T object, int index, Image image, int size) {
		
		String cssPath = Util.getResourceUrl("styles/button.css");
		if (cssPath != null) {
			this.getStylesheets().add(cssPath);
		} else {
			System.err.println("Stylesheet nicht gefunden!");
		}

		myObject = object;
		
		this.index = index;
		this.image = image;
		this.size = size;
		
		imageView  = new ImageView(image);
		imageView.setPreserveRatio(true);
        imageView.setFitHeight(size);
        
        Rectangle clip = new Rectangle(size, size);
        clip.setArcWidth(20); 
        clip.setArcHeight(20);
        
        iconView = new ImageView(PlayerButton.imageMap.get("play_white"));
        iconView.setOpacity(0.0);
        
        stackPane = new StackPane();
        
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.2), imageView);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.2);
        
        FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(0.2), iconView);
        fadeTransition2.setFromValue(0.0);
        fadeTransition2.setToValue(1.0);
        
        
        this.setOnMouseEntered(event -> {
        	fadeTransition.setRate(1);
        	fadeTransition.play();
        	fadeTransition2.setRate(1);
        	fadeTransition2.play();
        });

        this.setOnMouseExited(event -> {
        	fadeTransition.setRate(-1);
        	fadeTransition.play();
        	fadeTransition2.setRate(-1);
        	fadeTransition2.play();
        });
		
        stackPane.getChildren().addAll(imageView, iconView);
        
        stackPane.setClip(clip);
        
		this.setGraphic(stackPane);
	}
	
	public void setImage(Image img) {
		iconView.setImage(img);
	}
	
	public T getObject() {
		return myObject;
	}
	
	public int getIndex() {
		return index;
	}
	
	public Image getImage() {
		return image;
	}
	
	public int getSize() {
		return size;
	}
	
}
