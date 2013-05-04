package com.oraro.amediaplayer.dataaccess;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.oraro.amediaplayer.log.MPLog;
import com.oraro.amediaplayer.ui.list.Item;

/**
 * Abstract class that provides the methods for accessing the persisted data
 * (from content provides, media store, etc)
 * 
 * @author alexandru.deaconu
 * @date May 1, 2013, 1:37:01 PM
 * @version
 */
public abstract class DataAccess<T extends Item> {

	private static final String TAG = "DataAccess";
	
	protected Context mContext;
	protected String[] mProjection;
	protected Uri mUri;

	
	protected DataAccess(Context context, Uri uri, String[] projection) {
		this.mContext = context;
		this.mProjection = projection;
		this.mUri = uri;
	}
	
	
	/**
	 * Processes the cursor data and returns an item
	 * @param c the cursor which contains the table columns data
	 * @return an item
	 */
	protected abstract T readValue(Cursor c) throws ParseException;
	
	
	/**
	 * @return a list containing all items from the tables
	 */
	public List<T> loadList() {
		if(mContext == null) {
			return null;
		}
		
		List<T> itemList = new ArrayList<T>();
		ContentResolver contentResolver = mContext.getContentResolver();
		Cursor c = contentResolver.query(mUri, mProjection, null, null, null);
		
		if (c != null) {
			while (c.moveToNext()) {
				try {
					itemList.add(readValue(c));
				} catch (ParseException e) {
					MPLog.w(TAG, "Could not parse item", e);
				}
			}
			c.close();
		}
		
		return itemList;
	}
}
