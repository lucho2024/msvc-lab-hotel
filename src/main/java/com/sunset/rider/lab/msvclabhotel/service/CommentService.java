package com.sunset.rider.lab.msvclabhotel.service;

import com.sunset.rider.lab.msvclabhotel.model.documents.Comment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CommentService {

    Flux<Comment> findAll();

    Flux<Comment> findByHotelId(String id);

    Mono<Comment> findById(String id);

    Mono<Comment> save(Comment comment);

    Mono<Void> deleteComment(String id);
}
