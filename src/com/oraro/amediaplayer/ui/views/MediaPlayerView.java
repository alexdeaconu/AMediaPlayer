package com.oraro.amediaplayer.ui.views;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.oraro.amediaplayer.R;
import com.oraro.amediaplayer.ui.MediaPlayerController;

public class MediaPlayerView extends RelativeLayout implements OnSeekBarChangeListener, OnClickListener {	
	
	// constants
	private static final String DISPLAY_REMAINING_DURATION_00 = "00";
	private static final String DISPLAY_REMAINING_DURATION_COLON = ":";
	// object objects
	private MediaPlayerController mController;
	private Timer mRemainingDurationTimer;
	private RemainingDurationTask mRemainingDurationTask;
	// object views
	private SeekBar mSeekBarProgressTime;
	private TextView mTextViewRemainingTime;
	private ImageButton mImageButtonPrevious;
	private ImageButton mImageButtonPause;
	private ImageButton mImageButtonPlay;
	private ImageButton mImageButtonStop;
	private ImageButton mImageButtonNext;
	// object variables
	private long mSongDuration;
		
	
	//////////////////////////////////////////////////
	// OVERRIDEN METHODS
	//////////////////////////////////////////////////
	
	public MediaPlayerView(Context context) {
		super(context);
		init(context);
	}

	public MediaPlayerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		setupViews();
		setupViewsListeners();
	}	
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_button_previous:
			previous();
			break;
		case R.id.image_button_pause:
			pause();			
			break;
		case R.id.image_button_play:
			play();
			break;
		case R.id.image_button_stop:
			stop();
			break;
		case R.id.image_button_next:
			next();
			break;
		default:
			break;
		}
	}	

	//////////////////////////////////////////////////
	// GETTERS & SETTERS
	//////////////////////////////////////////////////
	
	public MediaPlayerController getController() {
		return mController;
	}

	public void setController(MediaPlayerController controller) {
		this.mController = controller;
	}

	//////////////////////////////////////////////////
	// PRIVATE METHODS
	//////////////////////////////////////////////////
	
	private void init(Context context) {
		inflate(context, R.layout.media_player_view, this);
	}
	
	/**
	 * Instantiates all the views from the layout & sets the
	 * visibility of pause & stop to hidden (until play is tapped)
	 */
	private void setupViews() {
		mSeekBarProgressTime = (SeekBar) findViewById(R.id.seekbar_progress_time);
		mTextViewRemainingTime = (TextView) findViewById(R.id.text_view_remaining_time);
		mImageButtonPrevious = (ImageButton) findViewById(R.id.image_button_previous);
		mImageButtonPause = (ImageButton) findViewById(R.id.image_button_pause);
		mImageButtonPause.setVisibility(View.GONE);
		mImageButtonPlay = (ImageButton) findViewById(R.id.image_button_play);
		mImageButtonStop = (ImageButton) findViewById(R.id.image_button_stop);
		mImageButtonStop.setVisibility(View.GONE);
		mImageButtonNext = (ImageButton) findViewById(R.id.image_button_next);
	}
	
	private void setupViewsListeners() {
		mSeekBarProgressTime.setOnSeekBarChangeListener(this);
		mImageButtonPrevious.setOnClickListener(this);
		mImageButtonPause.setOnClickListener(this);
		mImageButtonPlay.setOnClickListener(this);
		mImageButtonStop.setOnClickListener(this);
		mImageButtonNext.setOnClickListener(this);
	}	
	
	private void setIsPlayingVisibility(boolean isPlaying) {
		mImageButtonPause.setVisibility(isPlaying ? VISIBLE : GONE);
		mImageButtonStop.setVisibility(isPlaying ? VISIBLE : GONE);
		mImageButtonPlay.setVisibility(isPlaying ? GONE : VISIBLE);		
	}
	
	private void displayRemainingDuration(long duration) {
		long seconds = duration / 60;
		long minutes = duration % 60;
		long hours = duration / 3600;
		StringBuilder remainingDuration = new StringBuilder();
		if (hours > 0) remainingDuration.append(hours).append(DISPLAY_REMAINING_DURATION_COLON);
		if (minutes > 0) {
			remainingDuration.append(minutes);
		} else {
			remainingDuration.append(DISPLAY_REMAINING_DURATION_00);
		}
		remainingDuration.append(DISPLAY_REMAINING_DURATION_COLON);
		if (seconds > 0) {
			remainingDuration.append(seconds);
		} else {
			remainingDuration.append(DISPLAY_REMAINING_DURATION_00);
		}
		mTextViewRemainingTime.setText(remainingDuration);
		mSeekBarProgressTime.setProgress((int) (duration * 100 / mSongDuration));
	}
	
	private void cancelRemainingDurationTimer() {
		mRemainingDurationTask.cancel();
		mRemainingDurationTimer.cancel();
	}
	
	//////////////////////////////////////////////////
	// OBJECT METHODS
	//////////////////////////////////////////////////
	
	public void previous() {
		cancelRemainingDurationTimer();
		mController.previous();
	}
	
	public void pause() {
		cancelRemainingDurationTimer();
		mController.pause();
		setIsPlayingVisibility(false);
	}

	public void play() {
		mSongDuration = mController.play();
		mRemainingDurationTask = new RemainingDurationTask(mSongDuration);
		mRemainingDurationTimer = new Timer();
		mRemainingDurationTimer.schedule(mRemainingDurationTask, 0, 1000);
		setIsPlayingVisibility(true);
	}

	public void stop() {
		cancelRemainingDurationTimer();
		mController.stop();
		setIsPlayingVisibility(false);
	}

	public void next() {
		mController.next();
		cancelRemainingDurationTimer();
	}	
	
	//////////////////////////////////////////////////
	// INNER CLASSES
	//////////////////////////////////////////////////
		
	private class RemainingDurationTask extends TimerTask {
		
		private long countdownDuration;
		
		public RemainingDurationTask(long duration) {
			countdownDuration = duration;
		}

		@Override
		public void run() {
			displayRemainingDuration(countdownDuration);
		}		
	}
}
