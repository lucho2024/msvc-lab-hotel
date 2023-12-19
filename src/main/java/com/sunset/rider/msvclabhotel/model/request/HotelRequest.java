package com.sunset.rider.msvclabhotel.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HotelRequest {



    @NotEmpty(message = "El nombre no debe ser vacio o nulo")
    private  String name;

    @NotEmpty(message = "El pais no debe ser vacio o nulo")
    private  String country;

    @NotEmpty(message = "las estrellas no debe ser vacio o nulo")
    private  String stars;


}
