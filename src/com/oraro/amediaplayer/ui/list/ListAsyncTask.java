package com.oraro.amediaplayer.ui.list;

import java.io.FileDescriptor;
import java.util.List;
import java.util.Map.Entry;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;

import com.oraro.amediaplayer.R;
import com.oraro.amediaplayer.cache.AbstractCache;
import com.oraro.amediaplayer.cache.CacheFactory;
import com.oraro.amediaplayer.cache.CacheFactory.CacheTypes;
import com.oraro.amediaplayer.entities.MediaItem;
import com.oraro.amediaplayer.log.MPLog;

/**
 * Class that asynchronously populates a list with selectable items created on
 * data retrieved from the Media items. The media items contain track related
 * information, like artist name, track name or cover
 * 
 * @author alexandru.deaconu
 * @date May 21, 2013, 7:39:00 AM
 * @version
 */
public class ListAsyncTask<T extends MediaItem> extends AsyncTask<Void, T, Boolean> {

	private static final String TAG = "ListAsyncTask";
	
	private ListViewController<SelectableItem> listController;
	private List<T> itemList;
	private MediaRunnable commonBehaviourRunnable;
	private Context context;

	private AbstractCache<Integer, Bitmap> bitmapCache;

	private long sleepTime = 75;

	private int bitmapHeight = 150;

	private int bitmapWidth = 150;

	private static final Uri ARTWORK_URI =  Uri.parse("content://media/external/audio/albumart");

	/**
	 * 
	 * @param listController
	 * @param itemList the media item containing track related info
	 * @param commonBehaviorRunnable
	 *            used for playing audio and UI screen specific operations like
	 *            detaching/attaching UI fragments on a layout
	 */
	@SuppressWarnings("unchecked")
	public ListAsyncTask(Context context, ListViewController<SelectableItem> listController, List<T> itemList, MediaRunnable commonBehaviorRunnable) {
		this.listController = listController;
		this.itemList = itemList;
		this.commonBehaviourRunnable = commonBehaviorRunnable;
		this.context = context;
		this.bitmapCache = CacheFactory.getCache(CacheTypes.BITMAP);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected Boolean doInBackground(Void... params) {
		if(itemList == null || listController == null) {
			cancel(true);
			return false;
		}
		
		for(T item :itemList) {
			if(isCancelled()) {
				break;
			}
			
			publishProgress(item);
	
			try {
				if(sleepTime != 0) {
					Thread.sleep(sleepTime);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				cancel(true);
			}
		}
		
		itemList = null;
		return true;
	}


	@Override
	protected void onProgressUpdate(final T... values) {
		listController.add(new SelectableItem(-1, getAlbumart(values[0].getAlbumId()), values[0].getTitle(), R.drawable.ic_launcher, values[0].getArtist()) {
			
			@Override
			public void execute() {
				if(commonBehaviourRunnable != null) {
					commonBehaviourRunnable.execute(values[0]);
				}
			}
		});
	}
	
	public Bitmap getAlbumart(final int album_id) {
		if(this.bitmapCache.get(album_id) != null) {
			this.sleepTime = 50;
			return (Bitmap) this.bitmapCache.get(album_id); 
		} else {
			sleepTime = 75;
			Bitmap bm = null;
			try {
				Uri uri = ContentUris.withAppendedId(ARTWORK_URI, album_id);
				
				ParcelFileDescriptor pfd = context.getContentResolver()
						.openFileDescriptor(uri, "r");
				
				if (pfd != null) {
					FileDescriptor fd = pfd.getFileDescriptor();
					bm = BitmapFactory.decodeFileDescriptor(fd);
					final Bitmap bm1 = Bitmap.createScaledBitmap(bm,bitmapWidth, bitmapHeight, true);
					bm = bm1;
					
					MPLog.d(TAG, "Loading into memory");
					bitmapCache.put(new Entry<Integer, Bitmap>() {
						
						@Override
						public Integer getKey() {
							return album_id;
						}
						
						@Override
						public Bitmap getValue() {
							return bm1;
						}
						
						@Override
						public Bitmap setValue(Bitmap object) {
							return object;
						}
					});
				} 
				
			} catch (Exception e) {
				MPLog.w(TAG, "The bitmap is null. Could not get album bitmap", e);
			}
			return bm;
		}
		
	}


	public void setBitmapHeight(int height) {
		this.bitmapHeight = height;
	}


	public void setBitmapWidth(int width) {
		this.bitmapWidth = width;
	}

}
