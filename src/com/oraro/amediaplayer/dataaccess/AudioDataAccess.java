package com.oraro.amediaplayer.dataaccess;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.oraro.amediaplayer.entities.MediaItem;

/**
 * Class that provides access for audio files on the device
 * 
 * @author alexandru.deaconu
 * @date May 1, 2013, 1:58:04 PM
 * @version
 */
public class AudioDataAccess extends DataAccess<MediaItem> {

	private static final String[] PROJECTION = new String[] {
		MediaStore.Audio.Media._ID,
		MediaStore.Audio.Media.ARTIST,
		MediaStore.Audio.Media.BOOKMARK,
		MediaStore.Audio.Media.DURATION,
		MediaStore.Audio.Media.DISPLAY_NAME,
		MediaStore.Audio.Media.TITLE,
		MediaStore.Audio.Media.DATA,
		MediaStore.Audio.Media.ALBUM_ID
	};
	
	/**
	 * Used for querying audio files found either on the internal or external
	 * storage, depending on the uri sent as parameter
	 * 
	 * @param context
	 *            the context in which the was created.
	 * @param mediaUri
	 *            the uri form which to access data. It can be
	 *            <i>MediaStore.Audio.Media.EXTERNAL_CONTENT_URI</i> or
	 *            <i>MediaStore.Audio.Media.INTERNAL_CONTENT_URI</i>
	 */
	public AudioDataAccess(Context context, Uri mediaUri) {
		this(context, mediaUri, PROJECTION);
	}
	
	public AudioDataAccess(Context context) {
		this(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, PROJECTION);
	}
	
	protected AudioDataAccess(Context context, Uri uri, String[] projection) {
		super(context, uri, projection);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected MediaItem readValue(Cursor c) {
		return new MediaItem(
				c.getLong(c.getColumnIndex(MediaStore.Audio.Media._ID)),
				c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
				c.getInt(c.getColumnIndex(MediaStore.Audio.Media.BOOKMARK)),
				c.getInt(c.getColumnIndex(MediaStore.Audio.Media.DURATION)),
				c.getString(c.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)),
				c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE)),
				Uri.parse(c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA))),
				c.getInt(c.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
				);
	}

}
