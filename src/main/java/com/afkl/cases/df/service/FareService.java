package com.afkl.cases.df.service;

import com.afkl.cases.df.api.exception.TravelAPIException;
import com.afkl.cases.df.api.v1.airports.resource.Airport;
import com.afkl.cases.df.api.v1.fares.resources.Fare;
import com.afkl.cases.df.repository.AirportRepository;
import com.afkl.cases.df.repository.FareRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * The class to get fare details
 */
@Service
@Slf4j
public class FareService {

    private final FareRepository fareRepository;
    private final AirportRepository airportRepository;

    /**
     * Instantiates a new Fare service.
     *
     * @param fareRepository the fareRepository
     */
    public FareService(final FareRepository fareRepository,
                       final AirportRepository airportRepository) {
        this.fareRepository = fareRepository;
        this.airportRepository = airportRepository;
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
        log.info("Getting fare details for origin({}), destination({}) and currency({})", origin, destination, currency);
        try {
            CompletableFuture<Fare> asyncFare = fareRepository.getFare(origin, destination, currency);
            CompletableFuture<Airport> asyncOrigin = airportRepository.getAirportForCode(origin);
            CompletableFuture<Airport> asyncDestination = airportRepository.getAirportForCode(destination);

            return asyncFare.thenCombineAsync(asyncOrigin, (f, o) -> {
                f.setOrigin(o);
                return f;
            })
                    .thenCombineAsync(asyncDestination, (f, d) -> {
                        f.setDestination(d);
                        return f;
                    })
                    .get();
        } catch (InterruptedException e) {
            log.error("InterruptedException with message ({}) and cause ({}) while getting fare details", e.getMessage(), e.getCause());
            throw new TravelAPIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ExecutionException e) {
            log.error("ExecutionException with message ({}) and cause ({}) while getting fare details", e.getMessage(), e.getCause());
            throw new TravelAPIException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
