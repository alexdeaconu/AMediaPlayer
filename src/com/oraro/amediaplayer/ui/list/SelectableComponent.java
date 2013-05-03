package com.oraro.amediaplayer.ui.list;

import java.util.List;

/**
 * Interface used for all items. It eases the handling of items that have
 * sub-items and sub-sub-items.
 * 
 * @author alexandru.deaconu
 * @date May 2, 2013, 10:54:06 PM
 * @version
 */
public interface SelectableComponent {

	public void add(SelectableComponent component);
	
	public void remove(SelectableComponent comp);
	
	public SelectableComponent get(int index);
	
	public List<SelectableComponent> getSubItemList();
	
	public abstract void execute();
	
	public abstract int getLeftImageId();

	public abstract int getRightImageId();
	
	/**
	 * The string displayed in the text between the left and right images
	 * @return
	 */
	public String getDescription();
}
