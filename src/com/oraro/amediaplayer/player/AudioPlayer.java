package com.oraro.amediaplayer.player;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;

import com.oraro.amediaplayer.log.MPLog;

/**
 * Class that handles the audio resources
 *
 */
public class AudioPlayer {
	
	private static final String TAG = "AudioPlayer";
	private static AudioPlayer instance;
	private static AudioPlayer notificationInstance;
	
	public MediaPlayer mediaPlayer;
	private LinkedList<Uri> playlist;
	private Context mContext;
	
	
	private AudioPlayer(final Context context, int streamType){
		this.mContext = context;
		
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(streamType);
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			
			public void onCompletion(MediaPlayer mp) {
				
				if(playlist != null && !playlist.isEmpty()) {
					playlist.removeFirst();
					
					if(!playlist.isEmpty()) {
						
						playSound(playlist.getFirst());
					}
				} 
				
			}
		});
	}
	
	/**
	 * Returns an com.tunstall.mymedic.util.AudioPlayer instance
	 */
	public static synchronized AudioPlayer getInstance(Context context){
		if (instance == null){
			instance = new AudioPlayer(context, AudioManager.STREAM_MUSIC);
		}
		return instance;
	}
	
	public static synchronized AudioPlayer getNotificationInstance(Context context){
		if (notificationInstance == null){
			notificationInstance = new AudioPlayer(context, AudioManager.STREAM_NOTIFICATION);
		}
		return notificationInstance;
	}
	
	
	/**
	 * Releases resources associated with this AudioPlayer object. It is
	 * considered good practice to call this method when you're done using the
	 * AudioPlayer
	 */
	public void release(){
		if (instance.mediaPlayer != null){
			instance.mediaPlayer.release();
		}
		instance = null;
	}
	
	public synchronized void playSound(Uri soundUri){
		stopPlayAudioWithoutSpeakerOff();
		
		if (soundUri == null)
			return ;
		
		try {
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setDataSource(mContext, soundUri);
			MPLog.d(TAG, "Sound uri: "+ soundUri);
		} catch (IllegalArgumentException e) {
			MPLog.e(TAG, ".playSound # Illegal ex : " +e.getMessage());
		} catch (IllegalStateException e) {
			MPLog.e(TAG, ".playSound # "+e.getMessage());
		} catch (IOException e) {
			MPLog.e(TAG, ".playSound # "+e.getMessage());
		}

		try {
			mediaPlayer.prepare();
		} catch (IllegalStateException e) {
			MPLog.e(TAG, ".playSound # "+e.getMessage());
		} catch (IOException e) {
			MPLog.e(TAG, ".playSound # "+e.getMessage());
		}

		mediaPlayer.start();
		
	}
	

//	public synchronized void playSoundFromAssets(Context context, String fileName){
//
//		stopPlayAudioWithoutSpeakerOff();
//		
//		try {
//			AssetFileDescriptor descriptor = context.getAssets().openFd( fileName );
//			mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
//			mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
//			descriptor.close();
//		} catch (IllegalArgumentException e) {
//			MPLog.e(TAG, ".playSound # Illegal ex : " +e.getMessage());
//		} catch (IllegalStateException e) {
//			MPLog.e(TAG, ".playSound # "+e.getMessage());
//		} catch (IOException e) {
//			MPLog.e(TAG, ".playSound # "+e.getMessage());
//		}
//
//		try {
//			mediaPlayer.prepare();
//		} catch (IllegalStateException e) {
//			MPLog.e(TAG, ".playSound # "+e.getMessage());
//		} catch (IOException e) {
//			MPLog.e(TAG, ".playSound # "+e.getMessage());
//		}
//
//		mediaPlayer.start();
//	}

	/**
	 * Stops only one audio file
	 */
	public synchronized void stopPlayAudio() {

		stopPlayAudioWithoutSpeakerOff();
		
	}
	
	
	/**
	 * Stops the audio stream, without turning off the speaker
	 */
	private synchronized void stopPlayAudioWithoutSpeakerOff() {
		
		if (mediaPlayer.isPlaying()){
			
			mediaPlayer.stop();
			
		}
		
		mediaPlayer.reset();
	}
	
	
	/**
	 * Use this method in order to stop a playlist of audio files
	 */
	public synchronized void stopPlaylist() {

		stopPlayAudio();
		
		playlist = null;
	}
	

	
	/**
	 * Reads aloud a list of audio files
	 * 
	 * @param context
	 * @param audioFiles a list of media files names that should be played
	 */
	public void playList(List<Uri> audioFiles) {
		if(audioFiles ==null || audioFiles.isEmpty()) {
			return;
		}
		
		LinkedList<Uri> list = new LinkedList<Uri>();
		
		for(Uri s : audioFiles) {
			
			list.add(s);
		}
		
		this.playlist = list;
		playSound(playlist.getFirst());
		
	}
	
}
