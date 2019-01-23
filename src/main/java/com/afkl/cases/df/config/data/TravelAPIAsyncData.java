package com.afkl.cases.df.config.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * The class to read Travel api configuration data.
 */
@ConfigurationProperties(prefix = "travel.api.async.executor")
@Component
@Data
public class TravelAPIAsyncData {
    private int corePoolSize;
    private int maxPoolSize;
    private int queueCapacity;
    private String threadNamePrefix;
}