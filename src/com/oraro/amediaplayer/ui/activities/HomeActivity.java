package com.oraro.amediaplayer.ui.activities;

import java.util.List;

import android.os.Bundle;
import android.util.DisplayMetrics;

import com.oraro.amediaplayer.R;
import com.oraro.amediaplayer.dataaccess.AudioDataAccess;
import com.oraro.amediaplayer.entities.MediaItem;
import com.oraro.amediaplayer.player.AudioPlayer;
import com.oraro.amediaplayer.ui.list.BaseListActivity;
import com.oraro.amediaplayer.ui.list.ListAsyncTask;
import com.oraro.amediaplayer.ui.list.MediaRunnable;
import com.oraro.amediaplayer.ui.list.SelectableItem;
import com.oraro.amediaplayer.ui.video.VideoPlayer;

/**
 * First displayed screen. Entry point of the application. The screen will be
 * destroyed when exiting application and all other activities must be displayed
 * on top of this screen
 * 
 * @author alexandru.deaconu
 * @date May 2, 2013, 9:57:46 PM
 * @version
 */
public class HomeActivity extends BaseListActivity<SelectableItem> {

	@SuppressWarnings("unused")
	private static final String TAG = "HomeActivity";
	
	private VideoPlayer videoPlayer;
	
	private final MediaRunnable commonBehaviorRunnable = new MediaRunnable() {
		
		@Override
		public void execute(MediaItem mediaItem) {
			AudioPlayer.getInstance(HomeActivity.this).playSound(mediaItem.getUri());
			
		}
	};

	private ListAsyncTask<MediaItem> loader;
	
	/**
	 * Converts an array of bytes to its hexadecimal representation.
	 * @param b Input byte array
	 * @param addSpace if false, don't add spacing between characters 
	 * @return Byte array as a hexadecimal string.
	 */
	public static String byteArrayToHexString(byte[] b, boolean addSpace) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
			if(addSpace) {
				sb.append(" ");
			}
		}
		return sb.toString().toUpperCase();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AudioPlayer.getInstance(this);
		constructList();
		registerFilters();
		initFragments();
	}
	

	private void initFragments() {
		videoPlayer = new VideoPlayer();
		//COMMENTED DUE TO UNNEDED:
		/*getFragmentManager().beginTransaction().add(R.id.viewStub, videoPlayer)
				.commit();*/
	}


	private void constructList() {
		AudioDataAccess auda = new AudioDataAccess(this);
		final List<MediaItem> mediaList = auda.loadList();

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
//		int width = displaymetrics.widthPixels;
		
		loader = new ListAsyncTask<MediaItem>(this, getLeftListController(), mediaList, commonBehaviorRunnable);
		loader.setBitmapHeight(height/10);
		loader.setBitmapWidth(height/10);
		loader.execute();
	}

	
	@Override
	protected int getLayoutRes() {
		return R.layout.home_screen_layout;
	}

	public void refreshUI() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onBackPressed() {
		AudioPlayer.getInstance(this).stopAudio();
		loader.cancel(true);
		loader = null;
		finish();
	}
}
