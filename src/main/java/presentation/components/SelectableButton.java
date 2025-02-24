package presentation.components;

import business.Util;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class SelectableButton extends PlayerButton {

	private static Util util = Util.getInstance();
	
	private static final Color SELECTED_COLOR = util.SELECTED_COLOR;
	private static int PLAYER_BUTTON_SIZE_STANDARD = util.PLAYER_BUTTON_SIZE_STANDARD;
	
	private boolean active = false;
	
	private Shape shape;
	
	public SelectableButton() {
		super();
		
		shape = new Circle(PLAYER_BUTTON_SIZE_STANDARD, SELECTED_COLOR);
		shape.setOpacity(0);
		
		stackPane.getChildren().add(1, shape);
	}
	
	public void toggle() {
		if(active) {
			active = !active;
			shape.setFill(IDLE_COLOR);
			shape.setOpacity(0);
		} else {
			active = !active;
			shape.setFill(SELECTED_COLOR);
			shape.setOpacity(1);
		}
	}
	
	public void setActive() {
		active = true;
		shape.setFill(SELECTED_COLOR);
		shape.setOpacity(1);
	}
	
	public void setInactive() {
		active = false;
		shape.setFill(IDLE_COLOR);
		shape.setOpacity(0);
	}
}
