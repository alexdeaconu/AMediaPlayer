package com.oraro.amediaplayer.ui.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.ListFragment;
import android.content.Context;
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
	
	protected Context mContext;

	private List<T> list;
	private ListFragment listFragment;
	private GenericAdapter<T> mAdapter;

	public ListViewController(ListFragment listFragment) {
		mContext = listFragment.getView().getContext();
		mAdapter = new GenericAdapter<T>(mContext, R.layout.generic_item);
		list = new ArrayList<T>();
		
		this.listFragment = listFragment;
		this.listFragment.setListAdapter(mAdapter);
		
		this.listFragment.getListView().setOnItemClickListener(new OnItemClickListener() {


			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				selectItem(position, true);
			}
		});
	}
	
	
	public void selectItem(int position, boolean executeItem) {
		if(ListViewController.this.list != null && !ListViewController.this.list.isEmpty()) {
			
			int currentPos = mAdapter.getCurrentPosition();
			MPLog.i(TAG, "Selected position is: "+position);
			if(position != currentPos && currentPos >= 0 && currentPos < list.size()) {
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

			if(executeItem) {
				ListViewController.this.list.get(position).execute();
			}
		}
	}

	
	/**
	 * Moves the selection to the next item in the list
	 * @param executeItem
	 *            if true executes the item (e.g. playing the audio)
	 */
	public void moveNext(boolean executeItem) {
		int currPos = mAdapter.getCurrentPosition();
		mAdapter.setCurrentSelected(currPos++);
		selectItem(currPos, executeItem);
	}
	
	
	/**
	 * Moves the selection to the next item in the list
	 * @param executeItem
	 *            if true executes the item (e.g. playing the audio)
	 */
	public void movePrevious(boolean executeItem) {
		int currPos = mAdapter.getCurrentPosition();
		currPos--;
		mAdapter.setCurrentSelected(currPos);
		selectItem(currPos, executeItem);
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


	public GenericAdapter<T> getmAdapter() {
		return mAdapter;
	}
	
}
