package com.afkl.cases.df.api.v1.fares.controller;

import com.afkl.cases.df.api.common.TravelAPIConstants;
import com.afkl.cases.df.api.v1.fares.resources.Fare;
import com.afkl.cases.df.service.FareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The controller for fare resource
 */
@RestController
@RequestMapping(TravelAPIConstants.API_V1_BASE_ENDPOINT_URL)
public class FareController {

    private final FareService fareService;

    /**
     * Instantiates a new Fare controller.
     *
     * @param fareService the fare service
     */
    @Autowired
    public FareController(final FareService fareService) {
        this.fareService = fareService;
    }


    /**
     * Calculate fare fare.
     *
     * @param origin      the origin
     * @param destination the destination
     * @param currency    the currency
     * @return the fare
     */
    @GetMapping(TravelAPIConstants.FARES_ENDPOINT_URL)
    public ResponseEntity<Fare> calculateFare(@PathVariable("origin") String origin,
                                              @PathVariable("destination") String destination,
                                              @RequestParam(value = "currency", defaultValue = "EUR") String currency) {
        Fare fare = fareService.getFare(origin, destination, currency);
        return fare != null ? ResponseEntity.ok(fare) : ResponseEntity.notFound().build();
    }

}
