package com.afkl.cases.df.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

import java.lang.reflect.Method;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
public class TravelAPIAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
        log.error("UncaughtException with message ({}) and method name ({}) and params ({})", throwable.getMessage(), method.getName(), obj);
        throw new TravelAPIException(throwable.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
