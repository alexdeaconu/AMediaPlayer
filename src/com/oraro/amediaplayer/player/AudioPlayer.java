//package com.oraro.amediaplayer.player;
//
//import java.io.IOException;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Queue;
//
//import android.content.Context;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.media.MediaPlayer.OnCompletionListener;
//import android.media.MediaPlayer.OnErrorListener;
//import android.media.MediaPlayer.OnPreparedListener;
//import android.net.Uri;
//import android.os.Handler;
//
//import com.oraro.amediaplayer.entities.MediaItem;
//import com.oraro.amediaplayer.log.MPLog;
//
///**
// * Class that handles the audio resources
// *
// */
//public class AudioPlayer {
//	
//	private static final String TAG = "AudioPlayer";
//	private static final int PREPARE_TIMEOUT = 2000;
//	private static final int VOLUME_FADE_INTERVAL = 100; // milliseconds
//	private static final int MIN_VOLUME_LEVEL = 1; // volume level
//	
//	private static AudioPlayer instance;
//	private static AudioPlayer notificationInstance;
//	
//	private MediaPlayer mediaPlayer;
//	private Queue<MediaItem> playlist = new LinkedList<MediaItem>();
//	private Context mContext;
//	
//	private Handler mHandler = new Handler();
//	
//	private Runnable playRunnable;
//	private int streamType;
//	private AudioManager audioManager;
//	private MediaItem currentSound;
//	
//
//	/**
//	 * Instantiate a new player for a stream type.
//	 * @param context The context on which the player is build.
//	 * @param streamType The type of the stream used by the player.
//	 */
//	private AudioPlayer(final Context context, int streamType){
//		this.mContext = context;
//		this.streamType = streamType;
//		this.audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE); 
//		
//		initializePlayer(this.streamType);
//	}
//	
//	
//	/**
//	 * Initializes a new media player for a specific stream type.
//	 * @param streamType The type used by media player.
//	 */
//	private void initializePlayer(int streamType){
//		
//		if (mediaPlayer != null){
//			mediaPlayer.setOnCompletionListener(null);
//			mediaPlayer.release();
//		}
//		
//		mediaPlayer = new MediaPlayer();
//		mediaPlayer.setAudioStreamType(streamType);
//		
//		// when the sound is complete, the speaker can be disabled and next sound in queue should be played.
//		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
//			
//			public void onCompletion(MediaPlayer mp) {
//				MPLog.d(TAG, "Sound done");
//				if (currentSound != null){
//					if (currentSound.isDisableSpeaker()) {
//						LinuxManager.disableSound();
//					}
//					currentSound.dispatchComplete();
//					currentSound = null;					
//				}
//				startSound();
//			}
//		});
//		
//		// when the async preparation of sound file is done, the skip callback must be removed.
//		mediaPlayer.setOnPreparedListener(new OnPreparedListener(){
//
//			public void onPrepared(MediaPlayer mp) {
//				MPLog.d(TAG, "Sound ready");
//				mHandler.removeCallbacks(skipSound);
//				mp.start();
//			}
//		});
//		
//		mediaPlayer.setOnErrorListener(new OnErrorListener(){
//
//			public boolean onError(MediaPlayer mp, int what, int extra) {
//				if (currentSound != null){
//					if (currentSound.isDisableSpeaker()) {
//						LinuxManager.disableSound();
//					}
//					currentSound.dispatchComplete();
//					currentSound = null;					
//				}
//				startSound();
////				cancelAllSounds(false);
//				return false;
//			}
//			
//		});
//		
//	}
//	
//	/**
//	 * Cancels all pending sounds. If a sound has the flag set as canBeInteruptted, it will be removed from the sound queue. 
//	 * @param forceCancel If set to true, even sounds which cannot be interrupted will be interrupted.
//	 */
//	public synchronized void cancelAllSounds(boolean forceCancel){		
//		disposeHandler();
//		
//		if (this.currentSound != null){
//			if (this.currentSound.isCanBeInterrupted() || forceCancel){
//				this.currentSound = null;
//				if (mediaPlayer != null){
//					mediaPlayer.reset();
//				}
//			}
//		}
//		
//		this.playlist.clear();
//		LinuxManager.disableSound();		
//	}
//	
//	/**
//	 * Cancels all pending sound using a fadeout function which is decreasing the volume level before stopping the sound.
//	 * @param forceCancel If set to true, it will cancel all sounds even they have the flag cannot be interrupted
//	 * @param fadeOutTime The number of milliseconds for decreasing the volume to lowest level. 
//	 */
//	public synchronized void cancelAllSounds(final boolean forceCancel, int fadeOutTime){
//		
//		if (this.currentSound == null || (this.currentSound != null && this.fadeoutThread != null && this.fadeoutThread.isAlive())){
//			return;
//		}
//		
//		if (currentSound != null && currentSound.getUri() != null  && (currentSound.isCanBeInterrupted() || forceCancel)){
//		
//			this.fadeoutThread = new FadeoutThread(VOLUME_FADE_INTERVAL, audioManager.getStreamVolume(AudioManager.STREAM_MUSIC), MIN_VOLUME_LEVEL) {
//				
//				@Override
//				public void onTransitionCompleted() {
//					
//					audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, this.getInitialVolume(), AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
//				}
//				
//				@Override
//				public void applyFade(int currentVolume) {
//					audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
//				}
//			};
//			
//			this.fadeoutThread.start();	
//			try {
//				this.fadeoutThread.join();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			cancelAllSounds(forceCancel);
//		}
//	}
//	
//	/**
//	 * Returns an com.tunstall.mymedic.util.AudioPlayer instance
//	 */
//	public static synchronized AudioPlayer getInstance(Context context){
//		if (instance == null || (instance.mediaPlayer != null && instance.mediaPlayer.getAudioSessionId() == 0)){
//			instance = new AudioPlayer(context, AudioManager.STREAM_MUSIC);
//			
//		}
//		return instance;
//	}
//	
//	/**
//	 * Returns an com.tunstall.mymedic.util.AudioPlayer instance
//	 */
//	public static synchronized AudioPlayer getMotificationInstance(Context context){
//		if (notificationInstance == null || (notificationInstance.mediaPlayer != null && notificationInstance.mediaPlayer.getAudioSessionId() == 0)){
//			notificationInstance = new AudioPlayer(context, AudioManager.STREAM_NOTIFICATION);
//			
//		}
//		return notificationInstance;
//	}
//	
//	/**
//	 * Releases resources associated with this AudioPlayer object. It is
//	 * considered good practice to call this method when you're done using the
//	 * AudioPlayer
//	 */
//	public void release(){
//		if (mediaPlayer != null){
//			mediaPlayer.release();
//		}
//		instance = null;
//	}
//	
//	/**
//	 * Enqueues an audio file to be played after preparation.
//	 * @param soundUri The Uri of the sound resource.
//	 * @return True if the the media player enqueued the audio; false - if the preparation failed.
//	 */
//	private synchronized boolean startSound(Uri soundUri){
//		
//		MPLog.d(TAG, "Playing sound "+soundUri);
//		if (soundUri == null || isMute())
//			return false;
//		
//		try {
//			mediaPlayer.reset();
//			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//			mediaPlayer.setDataSource(mContext, soundUri);
//		} catch (IllegalArgumentException e) {
//			MPLog.e(TAG, ".playSound # Illegal ex : " +e.getMessage());
//		} catch (IllegalStateException e) {
//			MPLog.e(TAG, ".playSound # "+e.getMessage());
//		} catch (IOException e) {
//			MPLog.e(TAG, ".playSound # "+e.getMessage());
//		}
//
//		try {
//			mediaPlayer.prepareAsync();
//			mHandler.postDelayed(skipSound, PREPARE_TIMEOUT);
//			return true;
//		} catch (IllegalStateException e) {
//			MPLog.e(TAG, ".playSound # "+e.getMessage());
//		} 
//		return false;
//	}
//	
//	/**
//	 * Adds a sound item to the audio player's queue. When the sound playback is complete, the complete listener will be executed.
//	 * @param sound The sound object to be played.
//	 */
//	public void playSound(MediaItem sound) {
//		if (sound != null){
//			MPLog.d(TAG, "Play sound:" + sound);
//			this.addListRes(sound, false);
//		}
//	}
//	
//	/**
//	 * Adds a sound item to the audio player's queue. When the sound playback is complete, the complete listener will be executed.
//	 * If cancelPending is true, already enqueued sounds will be canceled (if canBeCanceled = true).
//	 * @param sound The sound object to be played.
//	 * @param cancelPending True for canceling already enqueued sounds.
//	 */
//	public void playSound(MediaItem sound, boolean cancelPending) {
//		MPLog.d(TAG, "Play sound with cancel:" + sound);
//		if (sound != null){
//			this.addListRes(sound, cancelPending);
//		}
//	}
//	
//	/**
//	 * Adds a sound object to internal queue.
//	 * @param item The sound object to be added.
//	 * @param clearPending If true, pending sounds that are allowed to be canceled (canBeCanceled=true) will be canceled; if false, sound will only be added to the and of the queue.
//	 */
//	private synchronized void addListRes(MediaItem item, boolean clearPending) {
//		try{
//			if (clearPending){
//				for (Iterator<MediaItem> iterator = this.playlist.iterator(); iterator
//						.hasNext();) {
//					MediaItem sound = iterator.next();
//					
//					if (sound.isCanBeInterrupted()){
//						iterator.remove();						
//					}
//				}
//			}
//			this.playlist.add(item);
//			startSound();
//		}catch (IllegalStateException e) {
//			MPLog.w(TAG, "media player not prepared: "+e, e );
//		}
//	}
//
//	/**
//	 * Process current (first) sound item or notify all listener for completion if no sound resource is available.
//	 */
//	private void startSound() {
//		MPLog.d(TAG, "Starting sound");
//		if(!mediaPlayer.isPlaying() && this.currentSound == null) {
//			this.currentSound = this.playlist.poll();
//			if (this.currentSound != null){
//				if (!startSound(currentSound.getUri())){
//					MediaItem oldSound = this.currentSound;
//					this.currentSound = null;
//					oldSound.dispatchComplete();
//				}
//			}
//		}
//	}
//	
//	/**
//	 * Plays an audio resource(file).
//	 * @param resourceName the name of the resource
//	 * @param the delay before playing the sound
//	 */
//	public void playSound(final MediaItem sound, int delay ) {
//		disposeHandler();
//		
//		if(sound == null ) {
//			return;
//		}
//		
//		playRunnable = new Runnable() {
//			
//			public void run() {
//				playSound(sound, true);
//			}
//		};
//		mHandler.postDelayed(playRunnable, delay);
//	}
//	
//	/**
//	 * Remove all callbacks for delayed playback and skip sound.
//	 */
//	private void disposeHandler() {
//		
//		if(playRunnable != null) {
//			mHandler.removeCallbacks(playRunnable);
//		}
//		mHandler.removeCallbacks(skipSound);
//	}
//
//	
//	/**
//	 * Enqueues a list of sounds.
//	 * 
//	 * @param audioFiles a list of media files names that should be played.
//	 */
//	public void playList(List<MediaItem> audioFiles) {		
//		for(MediaItem sound: audioFiles){
//			playSound(sound);
//		}
//	}
//	
//	/**
//	 * Returns the state of the speaker.
//	 * @return true if device volume is mute and no sound can be heard.
//	 */
//	private boolean isMute() {
//		if (prefsManager.getIntSetting(
//				SharedPreferencesManager.APP_STATUS_PREFS,
//				SharedPreferencesManager.KEY_AUDIO_LEVEL) <= 1) {
//			
//			return true;
//		}
//		
//		return false;
//	}
//	
//}
