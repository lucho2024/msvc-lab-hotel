package com.sunset.rider.lab.msvclabhotel.repository;

import com.sunset.rider.lab.msvclabhotel.model.documents.Hotel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface HotelRepository extends ReactiveMongoRepository<Hotel,String> {


    Flux<Hotel> findByNameIgnoreCaseContainingOrCityIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(String name, String city, String description);

    Flux<Hotel> findByCountryIdIgnoreCaseContaining(String countryId);
}
