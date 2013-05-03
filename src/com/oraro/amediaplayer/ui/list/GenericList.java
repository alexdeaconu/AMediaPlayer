package com.oraro.amediaplayer.ui.list;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.oraro.amediaplayer.R;

/**
 * @author alexandru.deaconu
 * @date May 2, 2013, 10:53:32 PM
 * @version
 */
public class GenericList extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.generic_list, container);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
	}

	
}
