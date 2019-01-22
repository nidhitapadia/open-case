package com.afkl.cases.df.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@ControllerAdvice
public class TravelAPIExceptionHandler {

    /**
     * Handle http server error exception.
     *
     * @param e the e
     * @return the response entity
     */
    @ExceptionHandler(HttpServerErrorException.class)
    public HttpEntity handleGlobalException(HttpServerErrorException e) {
        return new ResponseEntity(e.getStatusCode());
    }

    /**
     * Handle illegal argument exception.
     *
     * @return the response entity
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public HttpEntity handleBadRequest(IllegalArgumentException ex) {
        log.error("Handling IllegalArgumentException with message ({}})", ex.getMessage());
        return new ResponseEntity<Object>("Bad Request", BAD_REQUEST);
    }

    /**
     * Handle bind exception.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBindException(BindException ex) {
        log.error("Handling BindingException with message ({})", ex.getMessage());
        return new ResponseEntity<Object>("Bad Request", BAD_REQUEST);
    }

    /**
     * Handle method argument not valid exception.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Handling MethodArgumentNotValidException with message ({}})", ex.getMessage());
        return new ResponseEntity<Object>("Bad Request", HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle http message not readable exception.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Handling HttpMessageNotReadableException with message ({}})", ex.getMessage());
        return new ResponseEntity<Object>("Bad Request", HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle http message not readable exception.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler({TravelAPIException.class})
    public ResponseEntity<?> handleHttpMessageNotReadableException(TravelAPIException ex) {
        log.error("Handling TravelAPIException with message ({}})", ex.getMessage());
        return new ResponseEntity<Object>(ex.getHttpStatus());
    }

    /**
     * Handle exception.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleException(Exception ex) {
        log.error("Handling unknown Exception of type ({}) with message ({}})", ex.getClass().getName(), ex.getMessage());
        return new ResponseEntity<Object>("Bad Request", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
