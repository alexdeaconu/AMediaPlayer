package com.oraro.amediaplayer.ui.list;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.oraro.amediaplayer.R;
import com.oraro.amediaplayer.log.MPLog;

/**
 * @author alexandru.deaconu
 * @date May 2, 2013, 10:53:45 PM
 * @version
 */
public class GenericAdapter<T extends SelectableItem> extends ArrayAdapter<T> {

	private Context mContext;
	private int mLayoutId;
	private int currentPosition = NO_SELECTION;

	public GenericAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.mContext = context;
		this.mLayoutId = textViewResourceId;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		SelectableItem item = getItem(position);
		
		if(row == null) {
			LayoutInflater inflater= ((Activity)mContext).getLayoutInflater();
			row = inflater.inflate(mLayoutId, parent, false);
		}
		
		//TODO change image
		ImageView leftImage = (ImageView) row.findViewById(R.id.leftImage); 
		ImageView rightImage = (ImageView) row.findViewById(R.id.rightImage);
		TextView content = (TextView) row.findViewById(R.id.content);
		content.setText(item.getDescription());
		
		if (position == getCurrentPosition()) {
			row.setBackgroundResource(R.drawable.list_normal );
		} 
		else {
			row.setBackgroundResource(R.drawable.list_focus);
		}
		
		return row;
	}


	/**
	 * Method for setting a list with the defined items
	 * @param items
	 */
	public void setList(List<T> items){
		super.clear();

		if(items!=null) {
			for(T item: items){
				this.add(item);
			}
		}
		notifyDataSetChanged();
	}
	

	/**
	 * Sets the currently selected item from the list
	 * @param position
	 */
	public void setCurrentSelected(int position) {
		this.currentPosition = position;
	}


	public int getCurrentPosition() {
		MPLog.i("GenericAdapter", "Current position is: "+currentPosition);
		return currentPosition;
	}
	
	
}
