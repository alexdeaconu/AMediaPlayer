package com.oraro.amediaplayer.ui.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.oraro.amediaplayer.R;
import com.oraro.amediaplayer.log.MPLog;
import com.oraro.amediaplayer.ui.Refreshable;

/**
 * @author alexandru.deaconu
 * @date May 2, 2013, 9:57:20 PM
 * @version
 */
public class ListViewController<T extends SelectableItem> implements Refreshable{

	private String TAG = "ListViewController";
	
	private List<T> list;
	private ListFragment listFragment;
	private GenericAdapter<T> mAdapter;

	public ListViewController(ListFragment listFragment) {
		mAdapter = new GenericAdapter<T>(listFragment.getView().getContext(), R.layout.generic_item);
		list = new ArrayList<T>();
		
		this.listFragment = listFragment;
		this.listFragment.setListAdapter(mAdapter);
		
		this.listFragment.getListView().setOnItemClickListener(new OnItemClickListener() {


			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(ListViewController.this.list != null && !ListViewController.this.list.isEmpty()) {
					
					int currentPos = mAdapter.getCurrentPosition();
					MPLog.i(TAG, "Selected position is: "+position);
					if(position != currentPos && currentPos != GenericAdapter.NO_SELECTION) {
						//remove the sub-items from the list
						T selectedItem = mAdapter.getItem(currentPos);

						if(selectedItem.getSubItemList() != null) {
							Iterator<T> iterator = (Iterator<T>) selectedItem.getSubItemList().iterator();
							
							MPLog.i(TAG, "Removing sub-items");
							while(iterator.hasNext()) {	
								MPLog.i(TAG, "Removing item... ");
								list.remove(iterator.next());
							}
							
						}
					}
					
					mAdapter.setCurrentSelected(position);
					mAdapter.notifyDataSetChanged();
					ListViewController.this.list.get(position).execute();
				}
			}
		});
	}
	
	public void setList(List<T> list) {
		this.list= list;
		mAdapter.setList(list);
	}

	
	/**
	 * Adds an item in the list
	 * @param selectableItem an item which can be clicked by the user
	 */
	public void add(T selectableItem) {
		if(selectableItem == null) {
			return;
		}
		
		list.add(selectableItem);
		mAdapter.add(selectableItem);
	}
	
	
	public void refreshUI() {
		mAdapter.notifyDataSetChanged();
	}

}
