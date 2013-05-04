package com.oraro.amediaplayer.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SPM = Shared Preferences Manager. This class should be used to access any
 * persisted data through the shared preferences.
 * 
 * @author alexandru.deaconu
 * @date May 4, 2013, 1:50:44 PM
 * @version
 */
public class SPM {

	public static final String PREFS_NAME = "oraro_shared_prefs";

	private Context mContext;
	private volatile static SPM instance;
	
	
	private SPM(Context context) {
		this.mContext = context;
	}
	
	public static final SPM getInstance(Context context) {
		if(instance == null) {
			synchronized(SPM.class) {
				instance = new SPM(context);
			}
		}
		return instance;
	}
	
	/**
	 * Updates the setting in the shared preferences. If the setting does not
	 * exist, it will be created.
	 * 
	 * @param sharedPrefs the name of the shared preference (xml file)
	 * @param key the key to be updated
	 * @param value the value the key should updated with
	 */
	public void putInt(String sharedPrefs, String key, int value) {
		SharedPreferences sp = mContext.getSharedPreferences(sharedPrefs, 0);
		Editor editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	/**
	 * Updates the setting in the shared preferences. If the setting does not
	 * exist, it will be created.
	 * 
	 * @param sharedPrefs the name of the shared preference (xml file)
	 * @param key the key to be updated
	 * @param value the value the key should updated with
	 */
	public void putLong(String sharedPrefs, String key, long value) {
		SharedPreferences sp = mContext.getSharedPreferences(sharedPrefs, 0);
		Editor editor = sp.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	
	/**
	 * Updates the setting in the shared preferences. If the setting does not
	 * exist, it will be created.
	 * 
	 * @param sharedPrefs the name of the shared preference (xml file)
	 * @param key the key to be updated
	 * @param value the value the key should updated with
	 */
	public void putString(String sharedPrefs, String key, String value) {
		SharedPreferences sp = mContext.getSharedPreferences(sharedPrefs, 0);
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	
	/**
	 * Updates the setting in the shared preferences. If the setting does not
	 * exist, it will be created.
	 * 
	 * @param sharedPrefs the name of the shared preference (xml file)
	 * @param key the key to be updated
	 * @param value the value the key should updated with
	 */
	public void putBool(String sharedPrefs, String key, boolean value) {
		SharedPreferences sp = mContext.getSharedPreferences(sharedPrefs, 0);
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	
	/**
	 * Updates the setting in the shared preferences. If the setting does not
	 * exist, it will be created.
	 * 
	 * @param sharedPrefs the name of the shared preference (xml file)
	 * @param key the key to be updated
	 * @param value the value the key should updated with
	 */
	public boolean getBool(String sharedPrefs, String key) {
		SharedPreferences sp = mContext.getSharedPreferences(sharedPrefs, 0);
		boolean value = sp.getBoolean(key, false);
		return value;
	}
	
	
	/**
	 * Updates the setting in the shared preferences. If the setting does not
	 * exist, it will be created.
	 * 
	 * @param sharedPrefs the name of the shared preference (xml file)
	 * @param key the key to be updated
	 * @param value the value the key should updated with
	 */
	public int getInt(String sharedPrefs, String key) {
		SharedPreferences sp = mContext.getSharedPreferences(sharedPrefs, 0);
		int value = sp.getInt(key, -1);
		return value;
	}
	
	
	/**
	 * Updates the setting in the shared preferences. If the setting does not
	 * exist, it will be created.
	 * 
	 * @param sharedPrefs the name of the shared preference (xml file)
	 * @param key the key to be updated
	 * @param value the value the key should updated with
	 */
	public long getLong(String sharedPrefs, String key) {
		SharedPreferences sp = mContext.getSharedPreferences(sharedPrefs, 0);
		long value = sp.getInt(key, -1);
		return value;
	}
	
	
	/**
	 * Updates the setting in the shared preferences. If the setting does not
	 * exist, it will be created.
	 * 
	 * @param sharedPrefs the name of the shared preference (xml file)
	 * @param key the key to be updated
	 * @param value the value the key should updated with
	 */
	public String getString(String sharedPrefs, String key) {
		SharedPreferences sp = mContext.getSharedPreferences(sharedPrefs, 0);
		String value = sp.getString(key, null);
		return value;
	}
}
