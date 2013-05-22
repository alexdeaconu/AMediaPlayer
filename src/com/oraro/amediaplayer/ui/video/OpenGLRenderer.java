package com.oraro.amediaplayer.ui.video;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.media.audiofx.Visualizer;
import android.opengl.GLU;

import com.oraro.amediaplayer.player.AudioPlayer;
import com.oraro.amediaplayer.ui.PlayerRenderer;

/**
 * @author alexandru.deaconu
 * @date May 19, 2013, 4:29:27 PM
 * @version
 */
public class OpenGLRenderer extends PlayerRenderer {

    private Cube mCube = new Cube();
    private float mCubeRotation = (float) -0.5;
	private Visualizer visual;
	private Context mContext;

    public OpenGLRenderer(Context context, int divisions) {
    	super(context, divisions);
    	this.mContext = context;
    	
    	// Create the Visualizer object and attach it to our media player.  // player.getAudioSessionId()
    	visual = new Visualizer(AudioPlayer.getInstance(mContext).getSessionId());
    	visual.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
    }
    
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); 
            
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);

        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                  GL10.GL_NICEST);
            
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);        
        gl.glLoadIdentity();
        
        gl.glTranslatef(0.5f, 0.0f, -10.0f);

        gl.glRotatef(mCubeRotation, 1.0f, 1.0f, 1.0f);
            
        mCube.draw(gl);
           
        gl.glLoadIdentity();
        
        mCubeRotation -=0.5;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);
        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

	@Override
	public void onFftDataCapture(Visualizer visualizer, byte[] fft,
			int samplingRate) {

//		for (int i = 0; i < fft.length / mDivisions ; i++) {
//		      mFFTPoints[i * 4] = i * 4 * mDivisions;
//		      mFFTPoints[i * 4 + 2] = i * 4 * mDivisions;
//		      byte rfk = fft.[mDivisions * i];
//		      byte ifk = fft.[mDivisions * i + 1];
//		      float magnitude = (rfk * rfk + ifk * ifk);
//		      int dbValue = (int) (10 * Math.log10(magnitude));
//
//		      if(mTop)
//		      {
//		        mFFTPoints[i * 4 + 1] = 0;
//		        mFFTPoints[i * 4 + 3] = (dbValue * 2 - 10);
//		      }
//		      else
//		      {
//		        mFFTPoints[i * 4 + 1] = rect.height();
//		        mFFTPoints[i * 4 + 3] = rect.height() - (dbValue * 2 - 10);
//		      }
//		    }
	}

	@Override
	public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform,
			int samplingRate) {
		// TODO Auto-generated method stub
		
	}
}
