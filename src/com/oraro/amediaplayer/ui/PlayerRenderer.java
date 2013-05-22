package com.oraro.amediaplayer.ui;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.media.audiofx.Visualizer;
import android.opengl.GLSurfaceView.Renderer;

/**
 * Extended Rendered interface. It is also a Visualizer.OnDataCaptureListener in
 * order to get fft data related events
 * 
 * @author alexandru.deaconu
 * @version
 * @created May 22, 2013 2:42:10 PM
 */
public abstract class PlayerRenderer implements Renderer, Visualizer.OnDataCaptureListener {

	protected Context mContext;
	protected int mDivisions;

	public PlayerRenderer(Context context, int divisions) {
		this.mContext = context;
		this.mDivisions = divisions;
	}
	
	@Override
	public void onFftDataCapture(Visualizer visualizer, byte[] fft,
			int samplingRate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform,
			int samplingRate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		
	}

}
