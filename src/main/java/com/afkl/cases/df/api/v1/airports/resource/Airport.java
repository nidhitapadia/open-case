package com.afkl.cases.df.api.v1.airports.resource;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

/**
 * The resource Airport.
 */
@Data
public class Airport extends ResourceSupport {
    private String code, name, description;
}
