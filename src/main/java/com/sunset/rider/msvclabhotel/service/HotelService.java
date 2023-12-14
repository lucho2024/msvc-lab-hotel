package com.sunset.rider.msvclabhotel.service;

import com.sunset.rider.msvclabhotel.model.documents.Hotel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HotelService {


    Flux<Hotel> findAll();

    Mono<Hotel> findyById(String id);

    Mono<Hotel> save(Hotel hotel);

    Mono<Void> delete(String id);


    Mono<Hotel>update(Hotel hotel);
}
