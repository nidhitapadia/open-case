package com.afkl.cases.df.service;

import com.afkl.cases.df.api.exception.TravelAPIException;
import com.afkl.cases.df.api.v1.airports.controller.AirportController;
import com.afkl.cases.df.api.v1.airports.resource.Airport;
import com.afkl.cases.df.config.data.TravelAPIConfigurationData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

/**
 * The class to get Airport details.
 */
@Service
@Slf4j
public class AirportService {

    private final OAuth2RestTemplate oAuth2RestTemplate;
    private final TravelAPIConfigurationData travelAPIConfigurationData;

    /**
     * Instantiates a new Airport service.
     *
     * @param oAuth2RestTemplate         the o auth 2 rest template
     * @param travelAPIConfigurationData the travel api configuration data
     */
    public AirportService(final OAuth2RestTemplate oAuth2RestTemplate, final TravelAPIConfigurationData travelAPIConfigurationData) {
        this.oAuth2RestTemplate = oAuth2RestTemplate;
        this.travelAPIConfigurationData = travelAPIConfigurationData;
    }

    /**
     * Gets airports.
     *
     * @param term the term
     * @param lang the lang
     * @param page the page
     * @param size the size
     * @return the airports
     */
    public PagedResources<Airport> getAirports(final String term, final String lang, final Integer page, final Integer size) {

        ParameterizedTypeReference<PagedResources<Airport>> pagedResourcesParameterizedTypeReference =
                new ParameterizedTypeReference<PagedResources<Airport>>() {
                };
        try {
            PagedResources<Airport> airports = getTraverson(getUriBuilderAirports(term, lang, page, size).build().toUri())
                    .follow()
                    .toObject(pagedResourcesParameterizedTypeReference);

            Optional.ofNullable(airports)
                    .ifPresent(airportResources -> airportResources
                            .forEach(airport -> airport.add(ControllerLinkBuilder
                                    .linkTo(AirportController.class)
                                    .slash(airport.getCode())
                                    .withSelfRel())));
            return airports;
        } catch (OAuth2AccessDeniedException ex) {
            log.error("Authentication error ({})", ex.getMessage());
            throw new TravelAPIException(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Gets airport for code.
     *
     * @param code the code
     * @return the airport for code
     */
    public Airport getAirportForCode(final String code) {
        return getTraverson(UriComponentsBuilder.fromUriString(travelAPIConfigurationData.getAirportCodeUri())
                .buildAndExpand(code)
                .toUri())
                .follow().toObject(Airport.class);
    }

    private Traverson getTraverson(final URI uri) {
        Traverson traverson = new Traverson(uri, MediaTypes.HAL_JSON);
        traverson.setRestOperations(oAuth2RestTemplate);
        return traverson;
    }

    private UriComponentsBuilder getUriBuilderAirports(final String term,
                                                       final String lang,
                                                       final Integer page,
                                                       final Integer size) {
        final UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(travelAPIConfigurationData.getAirportsUri());
        Optional.ofNullable(term).ifPresent(t -> builder.queryParam("term", t));
        Optional.ofNullable(lang).ifPresent(t -> builder.queryParam("lang", t));
        Optional.ofNullable(page).ifPresent(t -> builder.queryParam("page", t));
        Optional.ofNullable(size).ifPresent(t -> builder.queryParam("size", t));
        return builder;
    }
}
