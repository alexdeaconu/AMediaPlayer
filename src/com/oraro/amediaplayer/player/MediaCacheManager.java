package com.oraro.amediaplayer.player;

import java.io.IOException;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.oraro.amediaplayer.entities.MediaItem;


/**
 * Class that contains a list of media items to be preloaded in order to provide
 * a better user experience.
 * 
 * @author alexandru.deaconu
 * @date May 4, 2013, 3:49:42 PM
 * @version
 */
public class MediaCacheManager {

	/**
	 * Preloads the media file
	 * @param item contains the uri of the media file
	 */
	public MediaPlayer preload(Context context, MediaItem item) {
		MediaPlayer mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		
		try {
			mediaPlayer.setDataSource(context, item.getUri());
			mediaPlayer.prepareAsync();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mediaPlayer;
	}
}
