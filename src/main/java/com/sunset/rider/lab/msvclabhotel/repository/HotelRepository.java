package com.sunset.rider.lab.msvclabhotel.repository;

import com.sunset.rider.lab.msvclabhotel.model.documents.Hotel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface HotelRepository extends ReactiveMongoRepository<Hotel,String> {
}
