package com.sunset.rider.lab.msvclabhotel.service;

import com.sunset.rider.lab.msvclabhotel.model.documents.Hotel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface HotelService {


    Flux<Hotel> findAll();

    Mono<Hotel> findyById(String id);

    Mono<Hotel> save(Hotel hotel);

    Mono<Void> delete(String id);


    Mono<Hotel>update(Hotel hotel);


    Flux<Hotel> findByNameCityDescription(String value);

    Flux<Hotel> findByCountryId(String countryId);
}
