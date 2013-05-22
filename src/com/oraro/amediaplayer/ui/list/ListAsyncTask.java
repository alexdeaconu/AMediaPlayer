package com.oraro.amediaplayer.ui.list;

import java.util.List;

import android.os.AsyncTask;

import com.oraro.amediaplayer.R;
import com.oraro.amediaplayer.entities.MediaItem;

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

	private ListViewController<SelectableItem> listController;
	private List<T> itemList;
	private MediaRunnable commonBehaviourRunnable;


	/**
	 * 
	 * @param listController
	 * @param itemList the media item containing track related info
	 * @param commonBehaviorRunnable
	 *            used for playing audio and UI screen specific operations like
	 *            detaching/attaching UI fragments on a layout
	 */
	public ListAsyncTask(ListViewController<SelectableItem> listController, List<T> itemList, MediaRunnable commonBehaviorRunnable) {
		this.listController = listController;
		this.itemList = itemList;
		this.commonBehaviourRunnable = commonBehaviorRunnable;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected Boolean doInBackground(Void... params) {
		if(itemList == null || listController == null) {
			cancel(true);
		}
		
		for(T item :itemList) {
			publishProgress(item);
		}
		return true;
	}


	@Override
	protected void onProgressUpdate(final T... values) {
		listController.add(new SelectableItem(-1, R.drawable.ic_launcher, values[0].getTitle(), R.drawable.ic_launcher, values[0].getArtist()) {
			
			@Override
			public void execute() {
				if(commonBehaviourRunnable != null) {
					commonBehaviourRunnable.execute(values[0]);
				}
			}
		});
	}

}
