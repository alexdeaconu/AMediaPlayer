package com.oraro.amediaplayer.ui.video;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.opengl.GLSurfaceView.Renderer;

/**
 * Extended Rendered class. Should be implemented by all classes that handle
 * animations generated using audio samples (e.g. fft or frequency samples)
 * 
 * @author alexandru.deaconu
 * @version
 * @created May 22, 2013 2:42:10 PM
 */
public abstract class AbstractRenderer implements Renderer {

	protected int mDivisions;

	public AbstractRenderer(int divisions) {
		this.mDivisions = divisions;
	}
	
	public abstract void onRender(Canvas canvas, byte[] data, Rect rect);

}
