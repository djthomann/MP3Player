package data;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import business.Util;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class Track {

	private long length; 
	private String title;
	private String artist;
	private String album;
	private Image artwork;

	private String soundFile;

	public Track(String soundFile) {
		
		this.soundFile = Util.getResourcePath(soundFile);
		// System.out.println(soundFile);
		
		Mp3File mp3file = null;
		try {
			mp3file = new Mp3File(this.soundFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		length = mp3file.getLengthInSeconds();
		
//		System.out.println("Length of this mp3 is: " + mp3file.getLengthInSeconds() + " seconds");
//		System.out.println("Bitrate: " + mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)"));
//		System.out.println("Sample rate: " + mp3file.getSampleRate() + " Hz");
//		System.out.println("Has ID3v1 tag?: " + (mp3file.hasId3v1Tag() ? "YES" : "NO"));
//		System.out.println("Has ID3v2 tag?: " + (mp3file.hasId3v2Tag() ? "YES" : "NO"));
//		System.out.println("Has custom tag?: " + (mp3file.hasCustomTag() ? "YES" : "NO"));
		
		if (mp3file.hasId3v1Tag()) {
			ID3v1 id3v1Tag = mp3file.getId3v1Tag();
		    artist = id3v1Tag.getArtist();
		    title = id3v1Tag.getTitle();
		    album = id3v1Tag.getAlbum();
//		  System.out.println(title + artist + album);
//		  System.out.println("Year: " + id3v1Tag.getYear());
//		  System.out.println("Genre: " + id3v1Tag.getGenre() + " (" + id3v1Tag.getGenreDescription() + ")");
//		  System.out.println("Comment: " + id3v1Tag.getComment());
		} 
		
		if(mp3file.hasId3v2Tag()) {
		    ID3v2 id3v2Tag = mp3file.getId3v2Tag();
		    artist = id3v2Tag.getArtist();
		    title = id3v2Tag.getTitle();
		    album = id3v2Tag.getAlbum();
		    byte[] imageData = id3v2Tag.getAlbumImage();
		    if (imageData != null) {
		        try {
					BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageData));
					Image image = SwingFXUtils.toFXImage(img, null);
					artwork = image;
					
				} catch (IOException e) {
					e.printStackTrace();
				}
		     }
		}
	}
	
	public String getFormattedTime() {
		String formattedTime = String.format("%02d:%02d", length / 60, length % 60);
		return formattedTime;
	}

	public long getLengthInSeconds() {
		return length;
	}
	
	public long getLengthInMillis() {
		return getLengthInSeconds() * 1000;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getSoundFile() {
		return soundFile;
	}

	public void setSoundFile(String soundFile) {
		this.soundFile = soundFile;
	}
	
	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}
	
	public Image getImage() {
		return artwork;
	}
	
	public void setImage(Image image) {
		artwork = image;
	}
	
}
