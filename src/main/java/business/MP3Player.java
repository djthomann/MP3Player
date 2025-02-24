package business;
import data.Playlist;
import data.PlaylistManager;
import data.Track;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class MP3Player {
	
	private static PlaylistManager playlistManager = PlaylistManager.getInstance();
	
	private SimpleMinim minim;
	private SimpleAudioPlayer audioPlayer;
	
	public final float INITIAL_VOLUME = 0.3f;
	private final int SKIPBACK_THRESHOLD_SECONDS = 2;
	private final int NEXT_TRACK_THESHOLD_MILLI_SECONDS = 750;
	
	private int currentTrackIndex;

	private Thread audioThread;
	private Thread positionThread;
	
	private SimpleBooleanProperty playing;
	private SimpleBooleanProperty muted;
	private SimpleIntegerProperty currentMillis;
	private SimpleFloatProperty volume;
	private SimpleObjectProperty<Playmode> playmode;
	private SimpleObjectProperty<Track> currentTrack;
	private SimpleObjectProperty<Playlist> currentPlaylist;
	
	private static MP3Player mp3Player = new MP3Player();
	
	public static MP3Player getInstance() {
		return mp3Player;
	}
	
	private MP3Player() {
		
		minim = new SimpleMinim();
		
		playing = new SimpleBooleanProperty(false);
		muted = new SimpleBooleanProperty(false);
		currentMillis = new SimpleIntegerProperty();
		volume = new SimpleFloatProperty(INITIAL_VOLUME);
		playmode = new SimpleObjectProperty<>(Playmode.STANDARD);
		currentTrack = new SimpleObjectProperty<>();
		currentPlaylist = new SimpleObjectProperty<>(playlistManager.getPlaylist(0));
		
	}
	
	public void load(Track track) {
		pause();
		currentTrack.set(track);
		audioPlayer = minim.loadMP3File(track.getSoundFile());
		
		// Lautstärke-Einstellungen an neuen Audioplayer übertragen
		setVolume(volume.get());
		if(muted.get()) {
			mute();
		}
	}
	
	public void shufflePlaylist() {
		currentPlaylist.get().toggleShuffle();
		load(currentPlaylist.get().getTrack(0));
		currentTrackIndex = 0;
	}
	
	public void play(int index) {
		
		if(currentTrackIndex == index) {
			
			play();
			
		} else {
			pause();
			load(currentPlaylist.get().getTrack(index));
			currentTrackIndex = index;
			play();
		}
		
	}
	
	public void play() {
		if (!playing.get()) {
            playing.set(true);
            audioThread = new Thread(() -> {
            	audioPlayer.play();
            });
            audioThread.start();
            startPositionCheck();
        } else {
            pause();
        }
	}
	
	private void startPositionCheck() {
		positionThread = new Thread(() -> {
			while(positionMillis() < 500) {
				// Stellt sicher, das Position der Millisekunden nicht zu nah an +- 1000 Millis liegt. 
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					
				}
			}
			
			while(playing.get()) {
				try {
					Thread.sleep(1000);
					currentMillis.set(positionMillis());
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					break;
				}
				if(Math.abs((currentTrack.get().getLengthInMillis() - currentMillis.get())) <= NEXT_TRACK_THESHOLD_MILLI_SECONDS)  {
					nextSong();
				}
			}
		});
		positionThread.start();
	}
	
	public void pause() {
		if (playing.get()) {
            if (audioThread != null) {
                audioThread.interrupt();
            }
            if (positionThread != null) {
                positionThread.interrupt();
            }
            playing.set(false);
            audioPlayer.pause();
        }
	}
	
	
	public void rewind() {
		
		if(positionSeconds() > SKIPBACK_THRESHOLD_SECONDS) {	
			audioPlayer.rewind();
		} else {
			
			pause();
			
			if(playmode.get() == Playmode.STANDARD) {
				// Playlist einmal abspielen 
				
				if(currentTrackIndex == 0) {
					// Wenn erster Song, pausieren und auf Anfang setzen
					skipTo(0);
					return;
				}
				
			} else if(playmode.get() == Playmode.SONG_REPEAT) {
				// Song wiederholen --> Playlist wiederholen
				playmode.set(Playmode.PLAYLIST_REPEAT);
			}
			
			// Song normal abspielen
			if(currentTrackIndex == 0) {
				currentTrackIndex = currentPlaylist.get().numberOfTracks() - 1;
			} else {
				currentTrackIndex--;
			}
			
			load(currentPlaylist.get().getTrack(currentTrackIndex));
			play();
			
		}
	}
	
	public void skip() {
		
		pause();
		
		if(playmode.get() == Playmode.STANDARD) {
			// Playlist einmal abspielen
					
			if(currentTrackIndex == currentPlaylist.get().numberOfTracks() - 1) {
				// Wenn fertig, pausieren und ersten Song wieder laden. Aber nicht abspielen
				currentTrackIndex = 0;
				load(currentPlaylist.get().getTrack(currentTrackIndex));
				return;
			}
			
		} else if(playmode.get() == Playmode.SONG_REPEAT) {
			// Song wiederholen --> Playlist wiederholen
			playmode.set(Playmode.PLAYLIST_REPEAT);
		}
		
		// Song normal abspielen
		if(currentTrackIndex == currentPlaylist.get().numberOfTracks() - 1) {
			currentTrackIndex = 0;
		} else {
			currentTrackIndex++;
		}
		
		load(currentPlaylist.get().getTrack(currentTrackIndex));
		play();
	}
	
	public void nextSong() {
		if(playmode.get() != Playmode.SONG_REPEAT) {
			// wenn nicht loopend, dann nächster Song
			skip();
		} else {
			// loop den Song
			pause();
			load(currentPlaylist.get().getTrack(currentTrackIndex));
			play();
		}
	}
	
	public void setVolume(float volume) {
		if (volume >= 0.0f && volume <= 1.0f) {
	        
			this.volume.set(volume);
	        float dB = (float) (Math.log(volume) / Math.log(10.0) * 30.0);
	        
	        audioPlayer.setGain(dB);
	    }
	}
	
	public void mute() {
		muted.set(true);
		audioPlayer.mute();
	}
	
	public void unmute() {
		muted.set(false);
		audioPlayer.unmute();
		if(volume.get() == 0) {
			setVolume(INITIAL_VOLUME);
		}
	}
	
	public int length() {
		return audioPlayer.length();
	}
	
	public int positionMillis() {
		return audioPlayer.position();
	}
	
	public int positionSeconds() {
		return positionMillis() / 1000;
	}
	
	public int positionMinutes() {
		return positionSeconds() / 60000;
	}
	
	public float ratioFinished() {
		return (float)positionSeconds() / currentTrack.get().getLengthInSeconds();
	}
	
	public float percentFinished() {
		return ratioFinished() * 100;
	}
	
	public void skipTo(double percent) {
		
		Thread cueThread = new Thread(new Runnable() {
            @Override
            public void run() {
            	int millis = (int)(percent * (currentTrack.get().getLengthInSeconds() * 1000));
            	audioPlayer.cue(millis);
            	currentMillis.set(millis);
            }
        });
        cueThread.start();
	}
	
	public void setPlaylist(Playlist playlist) {
		currentPlaylist.set(playlist); 
		currentTrackIndex = 0;
		load(currentPlaylist.get().getTrack(currentTrackIndex));
	}
	
	// PROPERTY ZEUGS
	
	public final SimpleBooleanProperty playingProperty() {
		return playing;
	}
	
	public final SimpleBooleanProperty mutedProperty() {
		return muted;
	}
	
	public final SimpleIntegerProperty currentMilliSecondsProperty() {
		return currentMillis;
	}
	
	public final SimpleFloatProperty volumeProperty() {
		return volume;
	}
	
	public final SimpleObjectProperty<Playmode> repeatingProperty() {
		return playmode;
	}
	
	public void cycleRepeatingProperty() {
		switch(playmode.get()) {
		case STANDARD:
			playmode.set(Playmode.PLAYLIST_REPEAT);
			break;
		case PLAYLIST_REPEAT:
			playmode.set(Playmode.SONG_REPEAT);
			break;
		case SONG_REPEAT:
			playmode.set(Playmode.STANDARD);
			break;
		default:
			break;
		}
	}
	
	public final SimpleObjectProperty<Track> trackProperty() {
		return currentTrack;
	}
	
	public final SimpleObjectProperty<Playlist> playlistProperty() {
		return currentPlaylist;
	}

}
