package com.afkl.cases.df.api.v1.fares.resources;

import com.afkl.cases.df.api.v1.airports.resource.Airport;
import lombok.Data;

/**
 * The resource Fare.
 */
@Data
public class Fare {

    double amount;
    Currency currency;
    Airport origin, destination;

}
