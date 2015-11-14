package kiblerdude.fpw.common;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import io.dropwizard.lifecycle.Managed;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;

public class SerializationService implements Managed {
	
	private static final Logger LOG = Logger.getLogger(SerializationService.class);

	private final ObjectMapper mapper;

	public SerializationService() {
		mapper = new ObjectMapper();
	}

	@Override
	public void start() throws Exception {
		LOG.info("Starting Serialization Service");
	}

	@Override
	public void stop() throws Exception {
		LOG.info("Stopping Serialization Service");
	}

	/**
	 * Serializes the provided <code>object</code> instance into a <code>json</code> String.
	 * @param object
	 * @return
	 */
	public <T extends Object> Optional<String> serialize(T object) {
		checkNotNull(object, "Object to serialize is null");
		
		try {
			return Optional.of(mapper.writeValueAsString(object));
		} catch (JsonProcessingException e) {
			LOG.error(e.getMessage());
			return Optional.absent();
		}
	}

	/**
	 * Deserializes the provided <code>json</code> String into a new instance of <code>clazz</code>.
	 * @param json
	 * @param clazz
	 * @return
	 */
	public <T extends Object> Optional<T> deserialize(String json, Class<T> clazz) {
		checkNotNull(json, "Json to deserialize is null");
		checkNotNull(clazz, "Class to deserialize to is null");
		
		try {
			return Optional.of(mapper.readValue(json, clazz));
		} catch (IOException e) {
			LOG.error(e.getMessage());
			return Optional.absent();
		}
	}
}
