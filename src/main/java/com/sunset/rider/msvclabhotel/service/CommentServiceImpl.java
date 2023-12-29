package com.sunset.rider.msvclabhotel.service;

import com.sunset.rider.msvclabhotel.model.documents.Comment;
import com.sunset.rider.msvclabhotel.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Flux<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Flux<Comment> findByHotelId(String id) {
        return commentRepository.findByHotelId(id);
    }

    @Override
    public Mono<Comment> findById(String id) {
        return commentRepository.findById(id);
    }

    @Override
    public Mono<Comment> save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Mono<Void> deleteComment(String id) {
        return commentRepository.deleteById(id);
    }
}
