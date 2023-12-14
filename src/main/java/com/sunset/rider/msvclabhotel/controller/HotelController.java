package com.sunset.rider.msvclabhotel.controller;

import com.sunset.rider.msvclabhotel.model.documents.Hotel;
import com.sunset.rider.msvclabhotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping
    public Mono<ResponseEntity<Flux<Hotel>>> getAll() {

        return Mono.just(
                ResponseEntity.ok().body(hotelService.findAll())
        );
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Hotel>> findById(@PathVariable String id) {
        return hotelService.findyById(id).map(
                hotel -> ResponseEntity.ok().body(hotel)
        ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> save(@RequestBody Mono<Hotel> hotel) {

        Map<String, Object> respuesta = new HashMap<>();


        return hotel.flatMap(h -> {
            h.setCreatedAt(LocalDateTime.now());
            h.setUpdatedAt(LocalDateTime.now());

            return hotelService.save(h)
                    .map(hotelResponse -> {
                        respuesta.put("hotel", hotelResponse);
                        respuesta.put("timestamp", new Date());

                        return ResponseEntity.created(URI.create("/hotel/".concat(hotelResponse.getId())))
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(respuesta);
                    });
        });


    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {

        return hotelService.delete(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));


    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Hotel>> update(@PathVariable String id){
        return hotelService.findyById(id)
                .map(hotel -> ResponseEntity.created(URI.create("/hotel/".concat(hotel.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(hotel))
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }
}
