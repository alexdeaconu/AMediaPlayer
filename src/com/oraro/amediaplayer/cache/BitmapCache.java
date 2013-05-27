package com.oraro.amediaplayer.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * @author alexandru.deaconu
 * @date May 27, 2013, 4:57:50 PM
 * @version
 */
class BitmapCache extends AbstractCache<Integer, Bitmap> {

	BitmapCache(int maxSize) {
		super(maxSize);
		// TODO Auto-generated constructor stub
	}

	@Override
	LruCache<Integer, Bitmap> getLruCache(int maxSize) {
		return new LruCache<Integer, Bitmap>(maxSize) {

			@Override
			protected int sizeOf(Integer key, Bitmap value) {
				return value.getByteCount();
			}
			
		};
	}

}
