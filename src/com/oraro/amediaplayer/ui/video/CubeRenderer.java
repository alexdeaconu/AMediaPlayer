package com.oraro.amediaplayer.ui.video;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.audiofx.Visualizer;
import android.opengl.GLU;

import com.oraro.amediaplayer.player.AudioPlayer;

/**
 * @author alexandru.deaconu
 * @date May 19, 2013, 4:29:27 PM
 * @version
 */
public class CubeRenderer extends AbstractRenderer {

    private Cube mCube = new Cube();
    private float mCubeRotation = (float) -0.5;
	private Visualizer visual;
	private Context mContext;

    public CubeRenderer(Context context, int divisions) {
    	super(divisions);
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
	public void onRender(Canvas canvas, byte[] data, Rect rect) {
		// TODO Auto-generated method stub
		
	}

}
