package com.oraro.amediaplayer.ui.activities;

import android.app.Activity;
import android.os.Bundle;

import com.oraro.amediaplayer.ui.Refreshable;

/**
 * Base screen of the entire application. All screens will extend this class
 * 
 * @author alexandru.deaconu
 * @date Dec 16, 2012 9:45:39 PM 2012
 * @version
 */
public abstract class BaseActivity extends Activity implements Refreshable{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutRes());
	}

	/**
	 * Register here the broadcast receivers and intent filters
	 */
	protected void registerFilters() {
		// TODO Auto-generated method stub
	}

	/**
	 * @return the layout that will be used by this activity
	 */
	protected abstract int getLayoutRes();
		
}
