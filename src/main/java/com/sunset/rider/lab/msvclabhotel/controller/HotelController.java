package com.sunset.rider.lab.msvclabhotel.controller;

import com.sunset.rider.lab.msvclabhotel.properties.HeadersProperties;
import com.sunset.rider.lab.msvclabhotel.model.documents.Hotel;
import com.sunset.rider.lab.msvclabhotel.model.request.HotelRequest;
import com.sunset.rider.lab.msvclabhotel.utils.ErrorNotFound;
import com.sunset.rider.lab.msvclabhotel.utils.Utils;
import com.sunset.rider.lab.msvclabhotel.service.HotelService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1")
@Slf4j
public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private HeadersProperties headersProperties;

    @GetMapping("${apis.hotel.find-all}")
    public Mono<ResponseEntity<Flux<Hotel>>> getAll(@RequestHeader MultiValueMap<String, String> headers) {

        Utils.validHeaders(headers,headersProperties.getRequired());

        return Mono.just(
                ResponseEntity.ok().body(hotelService.findAll())
        );
    }

    @GetMapping("${apis.hotel.get-hotel-by-id}")
    public Mono<ResponseEntity<Hotel>> findById(@RequestHeader MultiValueMap<String, String> headers,@PathVariable String id) {


        Utils.validHeaders(headers,headersProperties.getRequired());

        return hotelService.findyById(id).map(
                hotel -> ResponseEntity.ok().body(hotel)
        ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("${apis.hotel.create-hotel}")
    public Mono<ResponseEntity<Map<String, Object>>> save(@RequestHeader MultiValueMap<String, String> headers,
                                                          @Valid @RequestBody Mono<HotelRequest> hotel) {
        Utils.validHeaders(headers,headersProperties.getRequired());
        Map<String, Object> respuesta = new HashMap<>();


        return hotel.flatMap(h -> {
                    return hotelService.save(createHotelDb(h, null, null))
                            .map(hotelResponse -> {
                                respuesta.put("hotel", hotelResponse);
                                respuesta.put("timestamp", new Date());

                                return ResponseEntity.created(URI.create("/hotel/".concat(hotelResponse.getId())))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(respuesta);
                            });
                })
                .onErrorResume(t -> {
                    return Mono.just(t).cast(WebExchangeBindException.class)
                            .flatMap(e -> Mono.just(e.getFieldErrors()))
                            .flatMapMany(Flux::fromIterable)
                            .map(fieldError -> "El campo : " + fieldError.getField() + " "
                                    + fieldError.getDefaultMessage())
                            .collectList()
                            .flatMap(list -> {
                                respuesta.put("errores", list);
                                respuesta.put("timestamp", new Date());
                                return Mono.just(ResponseEntity.badRequest().body(respuesta));
                            });
                });


    }

    @DeleteMapping("${apis.hotel.deleted-hotel-by-id}")
    public Mono<ResponseEntity<Void>> delete(@RequestHeader MultiValueMap<String, String> headers,@PathVariable String id) {
        Utils.validHeaders(headers,headersProperties.getRequired());
        return hotelService.delete(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));


    }

    @PutMapping("${apis.hotel.updated-by-hotel-id}")
    public Mono<ResponseEntity<Map<String, Object>>> update(@RequestHeader MultiValueMap<String, String> headers,
                                                            @PathVariable String id,
                                                            @Valid @RequestBody Mono<HotelRequest> hotelRequest) {

        Utils.validHeaders(headers,headersProperties.getRequired());
        Map<String, Object> respuesta = new HashMap<>();

        return hotelService.findyById(id)
                .zipWith(hotelRequest)
                .flatMap(tupla -> hotelService.save(createHotelDb(tupla.getT2(), id, tupla.getT1())))
                .map(hotel -> {
                    respuesta.put("hotel", hotel);
                    respuesta.put("timestamp", new Date());
                    return ResponseEntity.created(URI.create("/hotel/".concat(hotel.getId())))
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(respuesta);
                })
                .defaultIfEmpty(new ResponseEntity(ErrorNotFound.error(id), HttpStatus.NOT_FOUND))
                .onErrorResume(t -> {

                    return Mono.just(t).cast(WebExchangeBindException.class)
                            .flatMap(e -> Mono.just(e.getFieldErrors()))
                            .flatMapMany(Flux::fromIterable)
                            .map(fieldError -> "El campo : " + fieldError.getField() + " "
                                    + fieldError.getDefaultMessage())
                            .collectList()
                            .flatMap(list -> {
                                respuesta.put("errores", list);
                                respuesta.put("timestamp", new Date());
                                return Mono.just(ResponseEntity.badRequest().body(respuesta));
                            });
                });


    }

    public Hotel createHotelDb(HotelRequest hotelRequest, String id, Hotel hotel) {

        return Hotel.builder()
                .id(StringUtils.isEmpty(id) ? null : id)
                .name(hotelRequest.getName())
                .country(hotelRequest.getCountry())
                .stars(hotelRequest.getStars())
                .createdAt(StringUtils.isEmpty(id) ? LocalDateTime.now() : hotel.getCreatedAt())
                .history(hotelRequest.getHistory())
                .city(hotelRequest.getCity())
                .description(hotelRequest.getDescription())
                .updatedAt(LocalDateTime.now())
                .build();
    }

}
