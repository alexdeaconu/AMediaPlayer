/**
 * 
 */
package com.oraro.amediaplayer.ui.list;

import java.util.ArrayList;
import java.util.List;


/**
 * @author alexandru.deaconu
 *
 */
public abstract class SelectableItem extends Item implements SelectableComponent{

	private List<SelectableComponent> subItemList;
	private int leftImageResId;
	private String text;
	private String subtitle;
	private int rightImageResId;
	
	public SelectableItem() {
		super();
	}
	
	public SelectableItem(int id, int leftImageResId, String text, int rightImageRes, String subtitle) {
		super(id);
		this.leftImageResId = leftImageResId;
		this.text = text;
		this.rightImageResId = rightImageRes;
		this.subtitle = subtitle;
	}
	
	
	@Override
	public void add(SelectableComponent component) {
		if(subItemList == null) {
			subItemList = new ArrayList<SelectableComponent>();
		}
		subItemList.add(component);
	}

	@Override
	public void remove(SelectableComponent comp) {
		if(subItemList != null) {
			subItemList.remove(comp);
		}
		
	}

	@Override
	public SelectableComponent get(int index) {
		if(subItemList == null && (index <0 || index > subItemList.size())) {
			return null;
		}
		
		return subItemList.get(index);
	}
	

	@Override
	public int getLeftImageId() {
		return leftImageResId;
	}

	
	@Override
	public String getDescription() {
		return text;
	}
	

	public String getSubtitle() {
		return subtitle;
	}

	@Override
	public int getRightImageId() {
		return rightImageResId;
	}

	public List<SelectableComponent> getSubItemList() {
		return subItemList;
	}
	
}
