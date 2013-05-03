package com.oraro.amediaplayer.ui.list;

/**
 * Class that should be extended by all decorator classes
 * 
 * @author alexandru.deaconu
 * @date May 2, 2013, 10:54:43 PM
 * @version
 */
public abstract class DeviceDecorator extends SelectableItem {

	private SelectableItem item;

	public DeviceDecorator(SelectableItem item ) {
		this.item = item;
	}

	public SelectableItem getItem() {
		return item;
	}
	
}
