package com.oraro.amediaplayer.ui.video;

import android.app.Fragment;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Class that displays the content of a media file
 * 
 * @author alexandru.deaconu
 * @date May 19, 2013, 4:20:28 PM
 * @version
 */
public class VideoPlayer extends Fragment {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		GLSurfaceView view = new GLSurfaceView(getActivity());
		view.setRenderer(new OpenGLRenderer(getActivity()));
		return view;
	}
	
	
	/**
	 * Replaces the current view with a view identified by it's resource id
	 * @param resId the resource id of the new view
	 */
	public void setView(int resId) {
		View currentView = this.getView();
		ViewGroup parentView = (ViewGroup) currentView.getParent();
		
		int currentIdx = parentView.indexOfChild(currentView);
		currentView = getActivity().getLayoutInflater().inflate(resId, parentView, false);
		parentView.addView(currentView, currentIdx);
	}
	
}
