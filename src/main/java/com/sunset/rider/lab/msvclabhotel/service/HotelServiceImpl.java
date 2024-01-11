package com.sunset.rider.lab.msvclabhotel.service;

import com.sunset.rider.lab.msvclabhotel.model.documents.Hotel;
import com.sunset.rider.lab.msvclabhotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HotelServiceImpl implements HotelService {


    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Flux<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Mono<Hotel> findyById(String id) {
        return hotelRepository.findById(id);
    }

    @Override
    public Mono<Hotel> save(Hotel hotel) {
        return hotelRepository.save(hotel) ;
    }

    @Override
    public Mono<Void> delete(String id) {
        return hotelRepository.deleteById(id);
    }

    @Override
    public Mono<Hotel> update(Hotel hotel) {
        return hotelRepository.save(hotel);
    }
}
