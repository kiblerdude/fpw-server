package kiblerdude.fpw;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class RedisConfiguration extends Configuration {

    @JsonProperty(value="host")
    private String host;	
	
    @JsonProperty(value="port")
    private Integer port;
    
    public String getHost() {
    	return host;
    }

    public Integer getPort() {
        return port;
    }
}
