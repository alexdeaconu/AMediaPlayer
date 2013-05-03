package com.oraro.amediaplayer.ui;

/**
 * @author alexandru.deaconu
 * @date May 2, 2013, 10:54:25 PM
 * @version
 */
public interface Refreshable {

	/**
	 * Updates the currents activity's strings after a synchronization. E.g. If
	 * the activity is created before the sync, it will contain old language.
	 */
	public void refreshUI();
}
