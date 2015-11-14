package kiblerdude.fpw;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FPWConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();
    
    @Valid
    @NotNull
    @JsonProperty(value = "redis")
    private RedisConfiguration redis = new RedisConfiguration();

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }
    
    public RedisConfiguration getRedisConfiguration() {
    	return redis;
    }
}
