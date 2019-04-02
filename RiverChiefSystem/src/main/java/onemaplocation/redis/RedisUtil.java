package onemaplocation.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	// 服务器IP地址、端口、密码
	private static String ADDR = "localhost";
	private static int PORT = 6379;
	private static String AUTH = "abc123";
	// 连接超时的时间
	private static int TIMEOUT = 1000;
	
	// 连接实例的最大连接数
	private static int MAX_ACTIVE = 1024;
	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 50;
	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
	private static int MAX_WAIT = 1000;
	//连接耗尽时是否阻塞, false报异常,true阻塞直到超时, 默认true
	private static boolean blockWhenExhausted = false;
	
	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;

	private static JedisPool jedisPool = null;

	/**
	 * 初始化连接池
	 */
	static {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(MAX_ACTIVE);
		config.setMaxIdle(MAX_IDLE);
		config.setBlockWhenExhausted(blockWhenExhausted);
		config.setMaxWaitMillis(MAX_WAIT);
		config.setTestOnBorrow(TEST_ON_BORROW);
		jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
		//jedisPool = new JedisPool(config, ADDR);
	}
	
	/**
	 *  获取Jedis实例
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		
		if (jedisPool != null) {
			Jedis resource = jedisPool.getResource();
			return resource;
		} else {
			return null;
		}
	}
	
	/**
	 *  释放资源
	 * @param jedis
	 */
	public static void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}

	}

}
