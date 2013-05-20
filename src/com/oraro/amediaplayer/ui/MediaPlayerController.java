package com.oraro.amediaplayer.ui;

/**
 * @author Andrei
 * 
 * Interface must be implemented by classes that want to control
 * the media player buttons: previous, next, pause, stop, play & seek
 */
public interface MediaPlayerController {

	/**
	 * Starts playing the current selected song
	 * @return the duration of the song in seconds
	 */
	long play();
	void pause();
	void stop();
	void next();
	void previous();
	void seek(int percentOfTheMedia);

}
