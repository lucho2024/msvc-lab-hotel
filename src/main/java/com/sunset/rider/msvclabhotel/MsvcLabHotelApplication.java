package com.sunset.rider.msvclabhotel;

import com.sunset.rider.msvclabhotel.model.documents.Comment;
import com.sunset.rider.msvclabhotel.model.documents.Hotel;
import com.sunset.rider.msvclabhotel.repository.CommentRepository;
import com.sunset.rider.msvclabhotel.repository.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@SpringBootApplication
@Slf4j
public class MsvcLabHotelApplication implements CommandLineRunner {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(MsvcLabHotelApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        mongoTemplate.dropCollection("hotels").subscribe();
        mongoTemplate.dropCollection("comments").subscribe();

        Flux.just(
                        Hotel.builder().name("inter")
                                .country("CO")
                                .stars(1F)
                                .description("super hotel")
                                .city("cali")
                                .history("historia")
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build(),
                        Hotel.builder().name("hscb")
                                .country("CO")
                                .stars(1F)
                                .description("super hotel")
                                .city("cali")
                                .history("historia")
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build()
                ).flatMap(hotel -> hotelRepository.save(hotel))
                .subscribe(hotel -> log.info("Insert :" + hotel.getId()));

        Flux.just(
                        Comment.builder().comment("muy bueno").hotelId("asasdd12").guestId("aassd112")
                                .stars(5).build(),
                        Comment.builder().comment("muy malo").hotelId("asasdd12").guestId("aassd112")
                                .stars(5).build()
                ).flatMap(comment -> commentRepository.save(comment))
                .subscribe(comment -> log.info("Insert : " + comment.getId()));
    }
}
