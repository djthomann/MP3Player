package presentation.playerview;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import presentation.controlpanelview.ControlPanelView;
import presentation.coverview.CoverView;
import presentation.coverview.CoverViewController;
import presentation.galleryview.GalleryViewController;
import presentation.playlistview.PlaylistView;
import presentation.playlistview.PlaylistViewController;

public class PlayerView extends BorderPane {

	public ControlPanelView controlPanelView;
	public CoverViewController coverViewController;
	public CoverView coverView;
	public PlaylistViewController playlistViewController;
	public PlaylistView playlistView;
	public GalleryViewController galleryViewController;
	public Pane galleryView;
	
	public PlayerView() {
		
		controlPanelView = new ControlPanelView(this);
		coverViewController = new CoverViewController();
		coverView = coverViewController.getRoot();
		playlistViewController = new PlaylistViewController();
		playlistView = playlistViewController.getRoot();
		galleryViewController = new GalleryViewController();
		galleryView = galleryViewController.getRoot();
		
		this.setBackground(new Background(new BackgroundFill(Color.DARKSLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		this.setBottom(controlPanelView);
		this.setCenter(coverView);
	}
	
	public boolean toggleCenter(Pane view) {
		if(this.getCenter() == view) {
			this.setCenter(coverView);
			return false;
		} else {
			this.setCenter(view);
			return true;
		}
	}
	
}
