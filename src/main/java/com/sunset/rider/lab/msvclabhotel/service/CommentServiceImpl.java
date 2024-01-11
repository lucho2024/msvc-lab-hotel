package com.sunset.rider.lab.msvclabhotel.service;

import com.sunset.rider.lab.msvclabhotel.repository.CommentRepository;
import com.sunset.rider.lab.msvclabhotel.model.documents.Comment;
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
