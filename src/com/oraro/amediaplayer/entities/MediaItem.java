package com.oraro.amediaplayer.entities;

import android.net.Uri;

import com.oraro.amediaplayer.log.MPLog;
import com.oraro.amediaplayer.ui.list.Item;

/**
 * Class that contains data related to a media file 
 * 
 * @author alexandru.deaconu
 * @date May 1, 2013, 2:17:14 PM
 * @version
 */
public class MediaItem extends Item {

	private String artist;
	private int bookmark;
	private int duration;
	private String displayName;
	private String title;
	private Uri uri;
	private int albumId;

	/**
	 * @param id The unique ID for a row. 
	 * @param artist The artist who created the video file, if any 
	 * @param bookmark The bookmark for the video. Time in ms. Represents the location in the video that the video should start playing at the next time it is opened. If the value is null or out of the range 0..DURATION-1 then the video should start playing from the beginning. 
	 * @param duration The duration of the video file, in ms 
	 * @param displayName The display name of the file 
	 * @param title The title of the content 
	 * @param uri the uri which identifies the media e.g. content://media/external/audio/media/999
	 * @param albumId 
	 */
	public MediaItem(long id, String artist, int bookmark, int duration,
			String displayName, String title, Uri uri, int albumId) {
		super(id);
		this.artist = artist;
		this.bookmark = bookmark;
		this.duration = duration;
		this.displayName = displayName;
		this.title = title;
		this.uri = uri;
		this.albumId = albumId;
		MPLog.d("", "media uri: "+uri);
	}

	public String getArtist() {
		return artist;
	}

	public int getBookmark() {
		return bookmark;
	}

	public int getDuration() {
		return duration;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getTitle() {
		return title;
	}

	public Uri getUri() {
		return uri;
	}
}
