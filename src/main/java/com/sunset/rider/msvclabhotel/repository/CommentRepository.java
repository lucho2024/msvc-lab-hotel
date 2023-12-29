package com.sunset.rider.msvclabhotel.repository;

import com.sunset.rider.msvclabhotel.model.documents.Comment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CommentRepository extends ReactiveMongoRepository<Comment,String> {

    Flux<Comment> findByHotelId(String hotelId);


}
