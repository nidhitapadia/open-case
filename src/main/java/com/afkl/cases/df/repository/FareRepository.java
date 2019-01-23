package com.afkl.cases.df.repository;

import com.afkl.cases.df.api.v1.fares.resources.Fare;
import com.afkl.cases.df.config.data.TravelAPIConfigurationData;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class FareRepository {

    private final OAuth2RestTemplate oAuth2RestTemplate;
    private final TravelAPIConfigurationData travelAPIConfigurationData;

    public FareRepository(final OAuth2RestTemplate oAuth2RestTemplate,
                          final TravelAPIConfigurationData travelAPIConfigurationData) {
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
    @Async
    public CompletableFuture<Fare> getFare(final String origin, final String destination, final String currency) {
        return CompletableFuture.supplyAsync(() -> getTraverson(origin, destination, currency)
                .follow()
                .toObject(Fare.class));
    }

    private Traverson getTraverson(final String origin, final String destination, final String currency) {
        Traverson traverson = new Traverson(getUri(origin, destination, currency), MediaTypes.HAL_JSON);
        traverson.setRestOperations(oAuth2RestTemplate);
        return traverson;
    }

    private URI getUri(final String origin, final String destination, final String currency) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(travelAPIConfigurationData.getFaresUri());
        Optional.ofNullable(currency).ifPresent(s -> builder.queryParam("currency", s));
        return builder.buildAndExpand(origin, destination).toUri();
    }

}
