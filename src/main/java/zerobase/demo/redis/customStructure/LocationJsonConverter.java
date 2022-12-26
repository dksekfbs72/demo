package zerobase.demo.redis.customStructure;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LocationJsonConverter {
	private final ObjectMapper mapper;

	public LocationJsonConverter(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	public String LocationToJson(Location location) throws JsonProcessingException {
		return mapper.writeValueAsString(location);
	}

	public Location JsonToLocation(String json) throws JsonProcessingException {


		return mapper.readValue(json, Location.class);
	}


}
