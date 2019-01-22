package com.afkl.cases.df.api.v1.airports.controller;

import com.afkl.cases.df.api.common.TravelAPIConstants;
import com.afkl.cases.df.service.AirportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.afkl.cases.df.api.common.TravelAPIConstants.AIRPORTS_ENDPOINT_URL;
import static com.afkl.cases.df.api.common.TravelAPIConstants.API_V1_BASE_ENDPOINT_URL;

/**
 * The controller for airports resources
 */
@RestController
@RequestMapping(API_V1_BASE_ENDPOINT_URL+ AIRPORTS_ENDPOINT_URL)
public class AirportController {

    private final AirportService airportService;

    /**
     * Instantiates a new Airport controller.
     *
     * @param airportService the airport service
     */
    public AirportController(final AirportService airportService) {
        this.airportService = airportService;
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
    @GetMapping()
    public ResponseEntity<?> getAirports(@RequestParam(required = false) final String term,
                                         @RequestParam(defaultValue = "en", required = false) final String lang,
                                         @RequestParam(required = false) final Integer page,
                                         @RequestParam(required = false) final Integer size) {
        return ResponseEntity.ok(airportService.getAirports(term, lang, page, size));
    }

    /**
     * Gets airport with code.
     *
     * @param code the code
     * @return the airport with code
     */
    @GetMapping(TravelAPIConstants.AIRPORT_CODE_ENDPOINT_URL)
    public ResponseEntity<?> getAirportWithCode(@PathVariable("code") String code) {
        return ResponseEntity.ok(airportService.getAirportForCode(code));
    }
}
