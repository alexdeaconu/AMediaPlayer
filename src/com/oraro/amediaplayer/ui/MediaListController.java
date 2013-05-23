package com.oraro.amediaplayer.ui;

import android.app.ListFragment;

import com.oraro.amediaplayer.player.AudioPlayer;
import com.oraro.amediaplayer.ui.list.ListViewController;
import com.oraro.amediaplayer.ui.list.SelectableItem;


/**
 * @author alexandru.deaconu
 * @version 
 * @created May 23, 2013 1:24:44 PM
 */
public class MediaListController extends ListViewController<SelectableItem> implements MediaPlayerController{

	public MediaListController(ListFragment listFragment) {
		super(listFragment);
	}

	@Override
	public long play() {
		selectItem(getmAdapter().getCurrentPosition(), true);
		return 0;
	}

	@Override
	public void pause() {
		AudioPlayer.getInstance(mContext).pause();
	}

	@Override
	public void stop() {
		AudioPlayer.getInstance(mContext).stopAudio();
	}

	@Override
	public void next() {
		super.moveNext(true);
	}

	@Override
	public void previous() {
		super.movePrevious(true);
	}

	@Override
	public void seek(int percentOfTheMedia) {
		AudioPlayer.getInstance(mContext).seek(percentOfTheMedia);
	}

}
