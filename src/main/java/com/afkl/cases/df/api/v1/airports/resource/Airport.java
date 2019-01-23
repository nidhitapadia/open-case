package com.afkl.cases.df.api.v1.airports.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

/**
 * The resource Airport.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Airport extends ResourceSupport {
    private String code, name, description;

    public Airport() {
        super();
    }

    public Airport(String code) {
        this();
        this.code = code;
    }
}
