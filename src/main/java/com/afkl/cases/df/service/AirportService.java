package com.afkl.cases.df.service;

import com.afkl.cases.df.api.exception.TravelAPIException;
import com.afkl.cases.df.api.v1.airports.controller.AirportController;
import com.afkl.cases.df.api.v1.airports.resource.Airport;
import com.afkl.cases.df.config.data.TravelAPIConfigurationData;
import com.afkl.cases.df.repository.AirportRepository;
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
import java.util.concurrent.ExecutionException;

/**
 * The class to get Airport details.
 */
@Service
@Slf4j
public class AirportService {

    private final AirportRepository airportRepository;

    /**
     * Instantiates a new Airport service.
     *
     * @param airportRepository         airportRepository
     */
    public AirportService(final AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
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

        try {
            return airportRepository.getAirports(term, lang, page, size).get();
        } catch (InterruptedException e) {
            log.error("InterruptedException with message ({}) and cause ({}) while getting fare details", e.getMessage(), e.getCause());
            throw new TravelAPIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ExecutionException e) {
            log.error("ExecutionException with message ({}) and cause ({}) while getting fare details", e.getMessage(), e.getCause());
            throw new TravelAPIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets airport for code.
     *
     * @param code the code
     * @return the airport for code
     */
    public Airport getAirportForCode(final String code) {
        try {
            return airportRepository.getAirportForCode(code).get();
        } catch (InterruptedException e) {
            log.error("InterruptedException with message ({}) and cause ({}) while getting fare details", e.getMessage(), e.getCause());
            throw new TravelAPIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ExecutionException e) {
            log.error("ExecutionException with message ({}) and cause ({}) while getting fare details", e.getMessage(), e.getCause());
            throw new TravelAPIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
