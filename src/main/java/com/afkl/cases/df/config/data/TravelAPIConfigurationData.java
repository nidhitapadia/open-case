package com.afkl.cases.df.config.data;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * The class to read Travel api configuration data.
 */
@ConfigurationProperties(prefix = "travel.service.simple")
@Component
@Data
public class TravelAPIConfigurationData {
    private String baseUri;
    private String airportsUri;
    private String airportCodeUri;
    private String faresUri;
}