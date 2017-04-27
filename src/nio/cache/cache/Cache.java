package nio.cache.cache;

/**
 * cache接口类
 * @author zhangqingxing
 *
 */
public interface Cache {
	/**
	 * 存储数据
	 * @param token
	 * @param key
	 * @param data
	 * @param expires
	 */
	void put(String token, String key, Object data, int expires);

	/**
	 * 获取数据
	 * @param token
	 * @param key
	 * @return
	 */
	Object get(String token, String key);
}
