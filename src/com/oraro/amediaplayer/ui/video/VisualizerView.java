package com.oraro.amediaplayer.ui.video;

import com.oraro.amediaplayer.log.MPLog;
import com.oraro.amediaplayer.ui.activities.HomeActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.util.AttributeSet;
import android.view.View;

/**
 * A class that draws visualizations of data received from a
 * {@link Visualizer.OnDataCaptureListener#onWaveFormDataCapture } and
 * {@link Visualizer.OnDataCaptureListener#onFftDataCapture }
 */
public class VisualizerView extends View {
	@SuppressWarnings("unused")
	private static final String TAG = "VisualizerView";
	
	private byte[] mFFTBytes;
	private Rect mRect = new Rect();
	private Visualizer mVisualizer;
	
	private Paint mFlashPaint = new Paint();
	private Paint mFadePaint = new Paint();

	private com.oraro.amediaplayer.ui.video.BarRenderer barGraphRendererBottom;
	
	public VisualizerView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs);
		init();
	}
	
	public VisualizerView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}
	
	public VisualizerView(Context context)
	{
		this(context, null, 0);
	}
	
	private void init() {
		mFFTBytes = null;
		
		mFlashPaint.setColor(Color.argb(122, 255, 255, 255));
		mFadePaint.setColor(Color.argb(238, 255, 255, 255)); // Adjust alpha to change how quickly the image fades
		mFadePaint.setXfermode(new PorterDuffXfermode(Mode.MULTIPLY));
		
		Paint paint = new Paint();
		paint.setStrokeWidth(50f);
		paint.setAntiAlias(true);
		paint.setColor(Color.argb(200, 56, 138, 252));
		barGraphRendererBottom = new BarRenderer(16, paint, false);
	}
	
	/**
	 * Links the visualizer to a player
	 * @param player - MediaPlayer instance to link to
	 */
	public void link(MediaPlayer player)
	{
		if(player == null)
		{
			throw new NullPointerException("Cannot link to null MediaPlayer");
		}
		
		// Create the Visualizer object and attach it to our media player.
		mVisualizer = new Visualizer(player.getAudioSessionId());
		mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
		
		// Pass through Visualizer data to VisualizerView
		Visualizer.OnDataCaptureListener captureListener = new Visualizer.OnDataCaptureListener()
		{
			@Override
			public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes,
					int samplingRate)
			{
			}
			
			@Override
			public void onFftDataCapture(Visualizer visualizer, byte[] bytes,
					int samplingRate)
			{
				MPLog.d(TAG, "the fft bytes are: "+HomeActivity.byteArrayToHexString(bytes, true));
				updateVisualizerFFT(bytes);
			}
		};
		
		mVisualizer.setDataCaptureListener(captureListener,
				Visualizer.getMaxCaptureRate() / 2, true, true);
		
		// Enabled Visualizer and disable when we're done with the stream
		player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mVisualizer.setEnabled(true);
				
			}
		});
		player.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		{
			@Override
			public void onCompletion(MediaPlayer mediaPlayer)
			{
				mVisualizer.setEnabled(false);
			}
		});
	}
	
	
	/**
	 * Call to release the resources used by VisualizerView. Like with the
	 * MediaPlayer it is good practice to call this method
	 */
	public void release()
	{
		mVisualizer.release();
	}
	
	
	/**
	 * Pass FFT data to the visualizer. Typically this will be obtained from the
	 * Android Visualizer.OnDataCaptureListener call back. See
	 * {@link Visualizer.OnDataCaptureListener#onFftDataCapture }
	 * @param bytes
	 */
	public void updateVisualizerFFT(byte[] bytes) {
		mFFTBytes = bytes;
		invalidate();
	}
	
	boolean mFlash = false;
	
	/**
	 * Call this to make the visualizer flash. Useful for flashing at the start
	 * of a song/loop etc...
	 */
	public void flash() {
		mFlash = true;
		invalidate();
	}
	
	Bitmap mCanvasBitmap;
	Canvas mCanvas;

	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		// Create canvas once we're ready to draw
		mRect.set(0, 0, getWidth(), getHeight());
		
		if(mCanvasBitmap == null)
		{
			mCanvasBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Config.ARGB_8888);
		}
		if(mCanvas == null)
		{
			mCanvas = new Canvas(mCanvasBitmap);
		}
		
		
		if (mFFTBytes != null) {
			// Render all FFT renderers
			barGraphRendererBottom.onRender(mCanvas, mFFTBytes, mRect);
		}
		
		// Fade out old contents
		mCanvas.drawPaint(mFadePaint);
		
		if(mFlash)
		{
			mFlash = false;
			mCanvas.drawPaint(mFlashPaint);
		}
		
		canvas.drawBitmap(mCanvasBitmap, new Matrix(), null);
	}
}
