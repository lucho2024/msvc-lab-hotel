package com.sunset.rider.msvclabhotel.repository;

import com.sunset.rider.msvclabhotel.model.documents.Hotel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface HotelRepository extends ReactiveMongoRepository<Hotel,String> {
}
