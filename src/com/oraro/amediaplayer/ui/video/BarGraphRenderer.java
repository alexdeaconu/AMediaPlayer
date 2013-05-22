package com.oraro.amediaplayer.ui.video;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class BarGraphRenderer extends AbstractRenderer
{
  private int mDivisions;
  private Paint mPaint;
  private boolean mTop;

  /**
   * Renders the FFT data as a series of lines, in histogram form
   * @param divisions - must be a power of 2. Controls how many lines to draw
   * @param paint - Paint to draw lines with
   * @param top - whether to draw the lines at the top of the canvas, or the bottom
   */
	public BarGraphRenderer(int divisions, Paint paint, boolean top) {
		super(divisions);
		mDivisions = divisions;
		mPaint = paint;
		mTop = top;
	}

	
	public void onRender(Canvas canvas, byte[] data, Rect rect) {

		float[] mFFTPoints = new float[data.length * 4];
		for (int i = 0; i < data.length / mDivisions; i++) {
			mFFTPoints[i * 4] = i * 4 * mDivisions;
			mFFTPoints[i * 4 + 2] = i * 4 * mDivisions;
			byte rfk = data[mDivisions * i];
			byte ifk = data[mDivisions * i + 1];
			float magnitude = (rfk * rfk + ifk * ifk);
			int dbValue = (int) (10 * Math.log10(magnitude));

			if (mTop) {
				mFFTPoints[i * 4 + 1] = 0;
				mFFTPoints[i * 4 + 3] = (dbValue * 2 - 10);
			} else {
				mFFTPoints[i * 4 + 1] = rect.height();
				mFFTPoints[i * 4 + 3] = rect.height() - (dbValue * 2 - 10);
			}
		}

		canvas.drawLines(mFFTPoints, mPaint);
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
