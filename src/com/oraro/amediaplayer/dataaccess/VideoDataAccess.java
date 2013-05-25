package com.oraro.amediaplayer.dataaccess;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.oraro.amediaplayer.entities.MediaItem;

/**
 *  Class that provides access for video files on the device
 * 
 * @author alexandru.deaconu
 * @date May 1, 2013, 2:18:08 PM
 * @version
 */
public class VideoDataAccess extends DataAccess<MediaItem> {

	private static final String[] PROJECTION = new String[] {
		MediaStore.Video.Media._ID,
		MediaStore.Video.Media.ARTIST,
		MediaStore.Video.Media.BOOKMARK,
		MediaStore.Video.Media.DURATION,
		MediaStore.Video.Media.DISPLAY_NAME,
		MediaStore.Video.Media.TITLE,
		MediaStore.Video.Media.DATA
	};
	
	/**
	 * Used for querying video files found either on the internal or external
	 * storage, depending on the uri sent as parameter
	 * 
	 * @param context
	 *            the context in which the was created.
	 * @param mediaUri
	 *            the uri form which to access data. It can be
	 *            <i>MediaStore.Video.Media.EXTERNAL_CONTENT_URI</i> or
	 *            <i>MediaStore.Video.Media.INTERNAL_CONTENT_URI</i>
	 */
	public VideoDataAccess(Context context, Uri mediaUri) {
		this(context, mediaUri, PROJECTION);
	}
	
	public VideoDataAccess(Context context) {
		super(context, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, PROJECTION);
	}
	
	protected VideoDataAccess(Context context, Uri uri, String[] projection) {
		super(context, uri, projection);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected MediaItem readValue(Cursor c) {
		return new MediaItem(
				c.getLong(c.getColumnIndex(MediaStore.Video.Media._ID)), 
				c.getString(c.getColumnIndex(MediaStore.Video.Media.ARTIST)),
				c.getInt(c.getColumnIndex(MediaStore.Video.Media.BOOKMARK)),
				c.getInt(c.getColumnIndex(MediaStore.Video.Media.DURATION)),
				c.getString(c.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME)),
				c.getString(c.getColumnIndex(MediaStore.Video.Media.TITLE)),
				Uri.parse(c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA))),
				-1
				);
	}

}
