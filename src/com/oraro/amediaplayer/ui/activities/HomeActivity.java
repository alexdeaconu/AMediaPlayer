package com.oraro.amediaplayer.ui.activities;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.oraro.amediaplayer.R;
import com.oraro.amediaplayer.dataaccess.AudioDataAccess;
import com.oraro.amediaplayer.entities.MediaItem;
import com.oraro.amediaplayer.ui.list.BaseListActivity;
import com.oraro.amediaplayer.ui.list.SelectableItem;

/**
 * First displayed screen. Entry point of the application
 * 
 * @author alexandru.deaconu
 * @date May 2, 2013, 9:57:46 PM
 * @version
 */
public class HomeActivity extends BaseListActivity<SelectableItem> {

	private static final String TAG = "HomeActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		constructList();
		registerFilters();
	}
	

	private void constructList() {
		List<SelectableItem> itemList = new ArrayList<SelectableItem>();

		
		
		//-----TODO this is only for testing -delete afterwards!!!!
		AudioDataAccess auda = new AudioDataAccess(this);
		List<MediaItem> mediaList = auda.loadList();
		int noOfItems = mediaList.size();
		if (mediaList.size() > 10) {
			noOfItems = 10;
		} 
		for(int  i=0 ; i< noOfItems; i++) {
			itemList.add( new SelectableItem(i, R.drawable.ic_launcher, mediaList.get(i).getTitle(), R.drawable.ic_launcher) {
				
				public void execute() {
					//TODO execute action->play song, maybe pause song...
				}
			});
		}
		setLeftList(itemList);
		//-----------------
		
		
		
	}

	@Override
	protected int getLayoutRes() {
		return super.getLayoutRes();
	}

	public void refreshUI() {
		// TODO Auto-generated method stub
		
	}
	
}
