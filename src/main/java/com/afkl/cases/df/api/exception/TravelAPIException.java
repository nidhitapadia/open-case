package com.afkl.cases.df.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import org.springframework.http.HttpStatus;

/**
 * The API Exception
 */
@Data
@AllArgsConstructor
public class TravelAPIException extends RuntimeException {
    private HttpStatus httpStatus;

    public TravelAPIException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
