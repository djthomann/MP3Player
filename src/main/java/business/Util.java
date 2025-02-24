package business;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import java.nio.file.Paths;

import javafx.scene.paint.Color;

public class Util {

	private static Util utilities = new Util();
	
	private final int SCREEN_WIDTH;
	private final int SCREEN_HEIGHT;
	
	public final int FRAME_WIDTH;
	public final int FRAME_HEIGHT;
	
	public final int PLAYER_BUTTON_SIZE_STANDARD;
	public final int PLAYER_BUTTON_SIZE_LARGE;
	
	public final int IMAGE_BUTTON_STANDARD_HEIGHT;
	public final int GALLERY_BUTTON_SIZE_STANDARD;
	
	public final int COVER_IMAGE_SIZE;
	
	public final int STANDARD_PADDING;
	public final int SMALL_PADDING;
	public final int LARGE_PADDING;
	
	public final Color SELECTED_COLOR = Color.LIGHTSEAGREEN;
	public final Color IDLE_COLOR = Color.WHITE;
	public final Color HOVER_COLOR = Color.AQUAMARINE;
	
	public static Util getInstance() {
		return utilities;
	}
	
	private Util() {		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		SCREEN_WIDTH = (int)d.width;
		SCREEN_HEIGHT = (int)d.height;
		FRAME_WIDTH = (int)(SCREEN_WIDTH * 0.6);
		FRAME_HEIGHT = (int)(SCREEN_HEIGHT * 0.8);
		PLAYER_BUTTON_SIZE_STANDARD = (int)(SCREEN_HEIGHT * 0.03);
		PLAYER_BUTTON_SIZE_LARGE = (int)(PLAYER_BUTTON_SIZE_STANDARD * 1.5);
		
		IMAGE_BUTTON_STANDARD_HEIGHT = (int)(SCREEN_HEIGHT * 0.1);
		GALLERY_BUTTON_SIZE_STANDARD = (int)(IMAGE_BUTTON_STANDARD_HEIGHT * 1.5);
		
		COVER_IMAGE_SIZE = (int)(FRAME_HEIGHT * 0.5);
		
		STANDARD_PADDING = 20;
		SMALL_PADDING = (int)(STANDARD_PADDING * 0.5);
		LARGE_PADDING = STANDARD_PADDING * 2;
	}

	 public static String getResourcePath(String resourcePath) {
        URL resourceUrl = Util.class.getClassLoader().getResource(resourcePath);
        
        if (resourceUrl == null) {
            System.err.println("Resource not found: " + resourcePath);
            return null;
        }

        try {
            // Konvertiere die URL in einen absoluten Dateipfad
            return Paths.get(resourceUrl.toURI()).toAbsolutePath().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	public static String getResourceUrl(String resourcePath) {
		URL resourceUrl = Util.class.getClassLoader().getResource(resourcePath);
		if (resourceUrl == null) {
			System.err.println("Resource not found: " + resourcePath);
			return null;
		}
		return resourceUrl.toExternalForm(); // Gibt die URL als String zurück
	}	
	
	public String formatSeconds(int seconds) {
		return String.format("%02d:%02d", seconds / 60, seconds % 60);
	}
	
	public String formatSeconds(long seconds) {
		return String.format("%02d:%02d", seconds / 60, seconds % 60);
	}
	
	public String formatMillis(int millis) {
		return formatSeconds(millis / 1000);
	}

	public String readFileNameFromPath(String path) {
		int lastSlashIndex = path.lastIndexOf('/');
		String fileNamePlusEnding = path.substring(lastSlashIndex + 1);
		String fileName = fileNamePlusEnding.split("\\.")[0];
		return fileName;
	}
	
}
