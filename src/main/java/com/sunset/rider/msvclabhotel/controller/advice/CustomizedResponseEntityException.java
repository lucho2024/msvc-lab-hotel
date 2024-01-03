package com.sunset.rider.msvclabhotel.controller.advice;

import com.sunset.rider.msvclabhotel.exception.HeaderExeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class CustomizedResponseEntityException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(HeaderExeption.class)
    public final Mono<ResponseEntity<?>> handlerHeaderException(HeaderExeption ex){

        return  Mono.just(new ResponseEntity<>(ex.getHeadersRe(), HttpStatus.BAD_REQUEST));
    }
}
