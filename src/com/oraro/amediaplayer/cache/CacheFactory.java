package com.oraro.amediaplayer.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author alexandru.deaconu
 * @date May 27, 2013, 4:45:25 PM
 * @version
 */
@SuppressWarnings("rawtypes")
public class CacheFactory {

	public enum CacheTypes {
		BITMAP
	}

	private static Map<CacheTypes, AbstractCache> mapCache;
	
	static {
		Map<CacheTypes, AbstractCache> map = new HashMap<CacheTypes, AbstractCache>();
		map.put(CacheTypes.BITMAP, new BitmapCache(4 * 1024 * 1024));
		mapCache = Collections.unmodifiableMap(map);
	}
	
	public static AbstractCache getCache(CacheTypes type) {
		return mapCache.get(type);
	}
	
}
