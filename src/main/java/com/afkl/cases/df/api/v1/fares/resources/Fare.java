package com.afkl.cases.df.api.v1.fares.resources;

import lombok.Data;

/**
 * The resource Fare.
 */
@Data
public class Fare {

    double amount;
    Currency currency;
    String origin, destination;

}
