package nio.cache.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * cache基于内存的实现类
 * @author zhangqingxing
 * 
 */
public class MemoryCache implements Cache {

	private static Map<String, Value> dataMap;

	static {
		dataMap = new ConcurrentHashMap<>();
	}

	/**
	 *
	 * 存放数据
	 * @param token
	 * @param key
	 * @param data
	 * @param expires
	 * 单位为妙
	 */
	@Override
	public void put(String token, String key, Object data, int expires) {
		if (token == null)
			return;
		Value value = dataMap.get(token);
		if (value == null) {
			value = new Value();
			dataMap.put(token, value);
		}
		value.setExpires(new Date(new Date().getTime() + expires * 1000));
		value.putData(key, data);
	}

	@Override
	public Object get(String token, String key) {
		if (token == null)
			return null;
		Value value = dataMap.get(token);
		if (value == null)
			return null;
		if (value.getExpires().getTime() < System.currentTimeMillis()) {
			dataMap.remove(token);
			return null;
		}
		return value.getData(key);
	}

	public static class Value {
		private Date expires;
		private Map<String, Object> data = new HashMap<>();

		public Date getExpires() {
			return expires;
		}

		public void setExpires(Date expires) {
			this.expires = expires;
		}

		public void putData(String key, Object value) {
			data.put(key, value);
		}

		public Object getData(String key) {
			return data.get(key);
		}
	}

}
