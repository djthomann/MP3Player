package presentation.controlpanelview;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import presentation.controlview.ControlView;
import presentation.controlview.ControlViewController;
import presentation.controlview.ControlView.SongControls;
import presentation.playerview.PlayerView;

public class ControlPanelView extends VBox {
	
	private ControlViewController controlViewController;
	public ControlView controlView;
	public SongControls songControls;
	
	private Background background;
	
	public ControlPanelView(PlayerView playerView) {
		
		background = new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));
		this.setBackground(background);
		
		controlViewController = new ControlViewController(playerView);
		controlView = controlViewController.getRoot();
		songControls = controlView.songControls;
		
		this.getChildren().addAll(songControls, controlView);
		
		this.setPadding(new Insets(20, 0, 0, 0));
		
	}
	
}
