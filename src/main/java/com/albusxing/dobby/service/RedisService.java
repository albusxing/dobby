package com.albusxing.dobby.service;
import java.io.Serializable;

/**
 * @author liguoqing
 */
public interface RedisService {
	/**
	 * 存储数据
	 */
	void set(String key, String value);

	/**
	 * 获取数据
	 */
	String get(String key);

	/**
	 * 设置超期时间
	 */
	boolean expire(String key, long expire);

	/**
	 * 删除数据
	 */
	void remove(String key);

	/**
	 * 自增操作
	 *
	 * @param delta 自增步长
	 */
	Long increment(String key, long delta);

	/**
	 * 存储数据
	 *
	 * @author youpan
	 * @param key
	 * @param value
	 * @param expire 单位秒
	 */
	void set(String key, String value, long expire);


	/**
	 * 判断缓存中是否有对应的value
	 *
	 * @param key
	 * @return
	 */
	boolean exists(final Serializable key);

	/**
	 * read cache
	 *
	 * @param key
	 * @return
	 */
	Object getStr(final Serializable key);

	/**
	 * add into canche
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	boolean setStr(final Serializable key, Object value);

	/**
	 * add into cache with expiration time
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setStr(final Serializable key, Object value, Long expireTime);
}