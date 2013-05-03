package com.oraro.amediaplayer.ui.list;


/**
 * Root class for all objects that are used in the application. All database
 * queries and data synchronization should process Item objects
 * 
 * @author alexandru.deaconu
 * @date May 1, 2013, 1:44:47 PM
 * @version
 */
public abstract class Item {
	
	private long id;

	public Item(long id2) {
		this.id = id2;
	}

	public Item() {
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
}
