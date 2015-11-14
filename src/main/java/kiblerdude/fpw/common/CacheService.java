package kiblerdude.fpw.common;

import static com.google.common.base.Preconditions.checkNotNull;

import io.dropwizard.lifecycle.Managed;
import kiblerdude.fpw.RedisConfiguration;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import com.google.common.base.Optional;

/**
 * Cache Service for reading and writing data to a cache.
 * 
 * @author kiblerj
 *
 */
public class CacheService implements Managed {
	private static final Logger LOG = Logger.getLogger(CacheService.class);
	
	private static final int DEFAULT_EXPIRATION = 3600;
	
	private final RedisConfiguration configuration;
	private final SerializationService serialization;
	
	// currently implementation uses Redis for cache
	private JedisPool redis;
	
	public CacheService(RedisConfiguration configuration, SerializationService serialization) {
		this.configuration = checkNotNull(configuration, "RedisConfiguration is null");
		this.serialization = checkNotNull(serialization, "SerializationService is null");
	}
	
	@Override
	public void start() throws Exception {
		LOG.info("Starting CacheService");		
		redis = new JedisPool(configuration.getHost(), configuration.getPort());
	}

	@Override
	public void stop() throws Exception {
		LOG.info("Stopping CacheService");
		redis.close();
		redis.destroy();
	}
	
	/**
	 * Sets the value of the provided <code>key</code> in Redis to the provided <code>object</code>.
	 * @param key
	 * @param object
	 * @return
	 */
	public <T extends Object> boolean set(String key, T object) {
		checkNotNull(key, "Key is null");
		checkNotNull(object, "Object is null");
		
		Optional<String> json = serialization.serialize(object);
		
		if (json.isPresent()) {
			Jedis connection = null;
			try {
				connection = redis.getResource();
				connection.setex(key, DEFAULT_EXPIRATION, json.get());
				return true;
			} finally {
				redis.returnResource(connection);
			}
		}
		
		return false;
	}
	
	/**
	 * Gets the value of the provided <code>key</code> as an instance of the provided <code>clazz</code>.
	 * @param key
	 * @return
	 */
	public <T extends Object> Optional<T> get(String key, Class<T> clazz) {
		checkNotNull(key, "Key is null");
		checkNotNull(clazz, "Class is null");
		
		Jedis connection = null;
		try {
			connection = redis.getResource();
			String json = connection.get(key);
			
			if (json == null) {
				return Optional.absent();
			}
			
			return serialization.deserialize(json, clazz);
		} finally {
			redis.returnResource(connection);
		}
	}
	
	/**
	 * Removes the provided <code>key</code> from cache.
	 * @param key
	 * @return
	 */
	public <T extends Object> boolean delete(String key) {
		checkNotNull(key, "Key is null");
		
		Jedis connection = null;
		try {
			connection = redis.getResource();
			return connection.del(key) > 0;
		} finally {
			redis.returnResource(connection);
		}
	}
}
