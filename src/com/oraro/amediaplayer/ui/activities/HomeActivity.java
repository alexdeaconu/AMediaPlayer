package com.oraro.amediaplayer.ui.activities;

import java.util.List;

import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.Bundle;

import com.oraro.amediaplayer.R;
import com.oraro.amediaplayer.dataaccess.AudioDataAccess;
import com.oraro.amediaplayer.entities.MediaItem;
import com.oraro.amediaplayer.log.MPLog;
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

	private static final String TAG = "HomeActivity";
	
	private VideoPlayer videoPlayer;

	private Visualizer vis;
	
	private final Visualizer.OnDataCaptureListener captureListener = new Visualizer.OnDataCaptureListener() {
		@Override
		public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes,
				int samplingRate) {
			MPLog.d(TAG, "Received bytes: " + byteArrayToHexString(bytes, true));
			System.out.println("onWaveFormDataCapture");
		}

		@Override
		public void onFftDataCapture(Visualizer visualizer, byte[] bytes,
				int samplingRate) {
			System.out.println("onFftDataCapture");
		}
	};

	
	private final MediaRunnable commonBehaviorRunnable = new MediaRunnable() {
		
		@Override
		public void execute(MediaItem mediaItem) {
			AudioPlayer.getInstance(HomeActivity.this).playSound(mediaItem.getUri());
			
			getFragmentManager().beginTransaction()
					.replace(R.id.viewStub, videoPlayer)
					.addToBackStack(null).commit();
			
			vis.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
			

		    vis.setDataCaptureListener(captureListener,
		            Visualizer.getMaxCaptureRate() / 2, true, false);

		    vis.setEnabled(true);
		    AudioPlayer.getInstance(HomeActivity.this).mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		    {
		        @Override
		        public void onCompletion(MediaPlayer mediaPlayer)
		        {
		            vis.setEnabled(false);
		        }
		    });
		}
	};
	
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
		vis = new Visualizer(AudioPlayer.getInstance(HomeActivity.this).mediaPlayer.getAudioSessionId());
		
		constructList();
		registerFilters();
		initFragments();
	}
	

	private void initFragments() {
		videoPlayer = new VideoPlayer();
		getFragmentManager().beginTransaction().add(R.id.viewStub, videoPlayer)
				.commit();
	}


	private void constructList() {
		AudioDataAccess auda = new AudioDataAccess(this);
		final List<MediaItem> mediaList = auda.loadList();

		ListAsyncTask<MediaItem> loader = new ListAsyncTask<MediaItem>(getLeftListController(), mediaList, commonBehaviorRunnable);
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
		finish();
	}
}
