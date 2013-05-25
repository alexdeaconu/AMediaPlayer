package com.oraro.amediaplayer.ui.list;

import java.io.FileDescriptor;
import java.util.List;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;

import com.oraro.amediaplayer.R;
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


	/**
	 * 
	 * @param listController
	 * @param itemList the media item containing track related info
	 * @param commonBehaviorRunnable
	 *            used for playing audio and UI screen specific operations like
	 *            detaching/attaching UI fragments on a layout
	 */
	public ListAsyncTask(Context context, ListViewController<SelectableItem> listController, List<T> itemList, MediaRunnable commonBehaviorRunnable) {
		this.listController = listController;
		this.itemList = itemList;
		this.commonBehaviourRunnable = commonBehaviorRunnable;
		this.context = context;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected Boolean doInBackground(Void... params) {
		if(itemList == null || listController == null) {
			cancel(true);
		}
		
		int i=0;
		
		for(T item :itemList) {
			publishProgress(item);
	
			try {
				Thread.sleep(i%2 ==0 ? 50: 100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			i++;
		}
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
	
	public Bitmap getAlbumart(int album_id) {
		Bitmap bm = null;
		try {
			final Uri sArtworkUri =  Uri
		            .parse("content://media/external/audio/albumart");

			Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
			MPLog.d(TAG, "The appended album uri is:"+uri);

			ParcelFileDescriptor pfd = context.getContentResolver()
					.openFileDescriptor(uri, "r");

			if (pfd != null) {
				FileDescriptor fd = pfd.getFileDescriptor();
				bm = BitmapFactory.decodeFileDescriptor(fd);
			} 
			
		} catch (Exception e) {
			MPLog.w(TAG, "The bitmap is null. Could not get album bitmap", e);
		}
		return bm;
	}

}
