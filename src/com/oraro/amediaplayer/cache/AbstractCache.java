package com.oraro.amediaplayer.cache;

import java.util.Map.Entry;

import android.util.LruCache;

/**
 * Class that acts as a cache for storing objects, like bitmaps
 * 
 * @author alexandru.deaconu
 * @date May 27, 2013, 3:47:43 PM
 * @version
 */
public abstract class AbstractCache<K, V> {

	private LruCache<K, V> cache;

	AbstractCache(int size) {
		cache = getLruCache(size);
	}
	
	public synchronized void put(Entry<K, V> keyValueElement) {
		if(keyValueElement != null && cache.get(keyValueElement.getKey()) == null) {
			cache.put(keyValueElement.getKey(), keyValueElement.getValue());
		}
	}
	
	public synchronized V get(K key) {
		return cache.get(key);
	}
	
	public int size() {
		return cache.size();
	}
	
	
	/**
	 * Method called in the constructor. 
	 * @param size the maximum size of the cache in bytes
	 * @return the customized LruCache.
	 */
	abstract LruCache<K, V> getLruCache(int size);
}
