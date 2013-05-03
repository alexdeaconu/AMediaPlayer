package com.oraro.amediaplayer.ui.list;

import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;

import com.oraro.amediaplayer.R;
import com.oraro.amediaplayer.ui.activities.BaseActivity;


/**
 * @author alexandru.deaconu
 * @date May 2, 2013, 9:57:33 PM
 * @version
 */
public abstract class BaseListActivity<T extends SelectableItem> extends BaseActivity {

	private ListViewController<T> leftListController;
//	private ListViewController<T> rightListController;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.leftListController = new ListViewController<T>((ListFragment) getFragmentManager().findFragmentById(R.id.leftList));
//		this.rightListController = new ListViewController<T>((ListFragment) getFragmentManager().findFragmentById(R.id.rightList));
	}

	
//	public void setRightList(List<T> rightList) {
//		this.rightListController.setList(rightList);
//	}
	
	public void setLeftList(List<T> leftList) {
		this.leftListController.setList(leftList);
	}

	@Override
	protected int getLayoutRes() {
		return R.layout.base_list;
	}
	
	
}
