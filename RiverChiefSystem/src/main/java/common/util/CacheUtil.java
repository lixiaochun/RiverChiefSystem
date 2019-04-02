package common.util;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
/**
*
*此静态类为cache工具类的基础类，其原理是通过继承或者直接使用cache<k,v>接口的方法
*来实现直接使用缓存，类似于map的键值对。
* 
*/
public class CacheUtil {

	private static EhCacheManager cacheManager = ((EhCacheManager) SpringContextHolder.getBean(EhCacheManager.class));
    
	public static Object get(String cacheName, String key) {
		return get0(cacheName, key);
	}

	private static Object get0(String cacheName, String key) {
		Object value = getCache(cacheName).get(key);
		return value == null ? null : value;
	}
	
	public static void put(String cacheName, String key, Object value) {
		put0(cacheName, key, value);
	}

	private static void put0(String cacheName, String key, Object value) {
		getCache(cacheName).put(key, value);
	}

	public static void remove(String cacheName, String key) {
		remove0(cacheName, key);
	}

	private static void remove0(String cacheName, String key) {
		getCache(cacheName).remove(key);
	}

	private static Cache<String, Object> getCache(String cacheName) {
		Cache<String, Object> cache = cacheManager.getCache(cacheName);
		if (cache == null)
			return null;
		else
			return cache;
	}

	public static EhCacheManager getCacheManager() {
		return cacheManager;
	}

}
