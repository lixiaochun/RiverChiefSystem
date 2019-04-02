package onemaplocation.redis;

import java.util.Map;

import redis.clients.jedis.Jedis;

public class RedisHashService {

	/**
	 * 获取redis的一个键值对
	 * @param key
	 * @param field
	 * @return
	 */
	public String hget(String key, String field) {
		// 获取连接
		Jedis jedis = null;
		String value = null;
		try { 
			jedis = RedisUtil.getJedis();
			if(jedis != null) {
				value = jedis.hget(key, field);
			}
		
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			// 关闭连接
			jedis.close();
		}

		return value;
	}

	/**
	 * 设置redis的一个键值对
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Long hset(String key, String field, String value) {
		// 获取连接
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = RedisUtil.getJedis();
			if(jedis != null) {
				result = jedis.hset(key, field, value);
			}
		
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			// 关闭连接
			jedis.close();
		}
		
		return result;

	}
	
	/**
	 * 删除redis的一个键值对
	 * @param key
	 * @param field
	 * @return
	 */
	public Long hdel(String key, String field) {
		// 获取连接
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = RedisUtil.getJedis();
			if(jedis != null) {
				result = jedis.hdel(key, field);
			}
		
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			// 关闭连接
			jedis.close();
		}
		
		return result;

	}
	
	/**
	 * 获取redis的所有的键值对
	 * @param key
	 * @return
	 */
	public Map<String, String> hgetAll(String key) {
		// 获取连接
		Jedis jedis = null;
		Map<String, String> result = null;
		try {
			jedis = RedisUtil.getJedis();
			if(jedis != null) {
				result = jedis.hgetAll(key);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			// 关闭连接
			jedis.close();
		}
		
		return result;

	}
	

}
