package com.sunset.rider.msvclabhotel.controller;


import com.sunset.rider.msvclabhotel.model.documents.Comment;
import com.sunset.rider.msvclabhotel.model.request.CommentRequest;
import com.sunset.rider.msvclabhotel.model.utils.ErrorNotFound;
import com.sunset.rider.msvclabhotel.model.utils.Utils;
import com.sunset.rider.msvclabhotel.properties.HeadersProperties;
import com.sunset.rider.msvclabhotel.service.CommentService;
import com.sunset.rider.msvclabhotel.service.HotelService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/hotel/comment")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private HeadersProperties headersProperties;

    @GetMapping
    public Mono<ResponseEntity<?>> FindAll(@RequestHeader MultiValueMap<String, String> headers) {
        Utils.validHeaders(headers,headersProperties.getRequired());
        return commentService.findAll()
                .collectList()
                .flatMap(comment -> {
                    if (comment.isEmpty()) {
                        return Mono.just(new ResponseEntity<>(ErrorNotFound.errorAll(), HttpStatus.NOT_FOUND));
                    } else {
                        return Mono.just(new ResponseEntity<>(comment, HttpStatus.OK));
                    }
                });

    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<?>> findById(@RequestHeader MultiValueMap<String, String> headers,@PathVariable String id) {
        Utils.validHeaders(headers,headersProperties.getRequired());
        return commentService.findById(id)
                .map(comment -> new ResponseEntity<>(comment, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity(ErrorNotFound.error(id), HttpStatus.NOT_FOUND));

    }


    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@RequestHeader MultiValueMap<String, String> headers,@PathVariable String id) {
        Utils.validHeaders(headers,headersProperties.getRequired());
        return commentService.deleteComment(id).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
    }

    @GetMapping("/hotel/{id}")
    public Mono<ResponseEntity<?>> findByHotelId(@RequestHeader MultiValueMap<String, String> headers,@PathVariable String id) {
        Utils.validHeaders(headers,headersProperties.getRequired());
        return commentService.findByHotelId(id)
                .collectList()
                .flatMap(hotel -> {

                    if (hotel.isEmpty()) {
                        return Mono.just(new ResponseEntity<>(ErrorNotFound.errorAll(), HttpStatus.NOT_FOUND));
                    } else {
                        return Mono.just(new ResponseEntity<>(hotel, HttpStatus.OK));
                    }
                });
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> save(@RequestHeader MultiValueMap<String, String> headers,
                                                          @RequestBody @Valid Mono<CommentRequest> commentRequest) {
        Utils.validHeaders(headers,headersProperties.getRequired());
        Map<String, Object> respuesta = new HashMap<>();
        List<Integer> stars = new ArrayList<>();


        return commentRequest
                .flatMap(c -> {
                    return commentService.save(buildDbObject(c, null, null))
                            .zipWith(commentService.findByHotelId(c.getHotelId())
                                    .collectList()
                                    .zipWith(hotelService.findyById(c.getHotelId()))
                                    .flatMap(tupla -> {
                                        tupla.getT1().forEach(h -> stars.add(h.getStars()));
                                        log.info("lista" + tupla.getT1());
                                        Float avg = (float) (stars.stream().reduce(0, Integer::sum) / stars.size());
                                        tupla.getT2().setStars(avg);
                                        tupla.getT2().setUpdatedAt(LocalDateTime.now());
                                        log.info("promedio " + avg);
                                        return hotelService
                                                .save(tupla.getT2());
                                    })

                            )
                            .map(tuple2 -> {
                                respuesta.put("comment", tuple2.getT1());
                                respuesta.put("timestamp", new Date());

                                log.info("entre");


                                return ResponseEntity.created(URI.create("/comment/".concat(c.getHotelId())))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(respuesta);
                            });


                })
                .onErrorResume(t -> {
                    return Mono.just(t).cast(WebExchangeBindException.class)
                            .flatMap(e -> Mono.just(e.getFieldErrors()))
                            .flatMapMany(Flux::fromIterable)
                            .map(fieldError -> "El campo : " + fieldError.getField() + " "
                                    + fieldError.getDefaultMessage())
                            .collectList()
                            .flatMap(list -> {
                                respuesta.put("errores", list);
                                respuesta.put("timestamp", new Date());
                                return Mono.just(ResponseEntity.badRequest().body(respuesta));
                            });
                });

    }

    public Comment buildDbObject(CommentRequest commentRequest, String id, Comment comment) {

        return Comment.builder()
                .id(StringUtils.isEmpty(id) ? null : id)
                .guestId(commentRequest.getGuestId())
                .stars(commentRequest.getStars())
                .hotelId(commentRequest.getHotelId())
                .comment(commentRequest.getComment())
                .createdAt(StringUtils.isEmpty(id) ? LocalDateTime.now() : comment.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
