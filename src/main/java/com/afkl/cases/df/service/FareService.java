package com.afkl.cases.df.service;

import com.afkl.cases.df.api.v1.fares.resources.Fare;
import com.afkl.cases.df.config.data.TravelAPIConfigurationData;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The class to get fare details
 */
@Service
public class FareService {
    private final OAuth2RestTemplate oAuth2RestTemplate;
    private final TravelAPIConfigurationData travelAPIConfigurationData;

    /**
     * Instantiates a new Fare service.
     *
     * @param oAuth2RestTemplate         the oAuth2RestTemplate
     * @param travelAPIConfigurationData the travel api configuration data
     */
    public FareService(final OAuth2RestTemplate oAuth2RestTemplate, final TravelAPIConfigurationData travelAPIConfigurationData) {
        this.oAuth2RestTemplate = oAuth2RestTemplate;
        this.travelAPIConfigurationData = travelAPIConfigurationData;
    }

    /**
     * Gets fare.
     *
     * @param origin      the origin
     * @param destination the destination
     * @param currency    the currency
     * @return the fare
     */
    public Fare getFare(final String origin, final String destination, final String currency) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(travelAPIConfigurationData.getFaresUri());
        Optional.ofNullable(currency).ifPresent(s -> builder.queryParam("currency", s));
        return oAuth2RestTemplate.getForObject(builder.build().toUriString(), Fare.class, getUriParamsMap(origin, destination));
    }

    private Map<String, String> getUriParamsMap(final String origin, final String destination) {
        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("origin", origin);
        uriParams.put("destination", destination);
        return uriParams;
    }
}
