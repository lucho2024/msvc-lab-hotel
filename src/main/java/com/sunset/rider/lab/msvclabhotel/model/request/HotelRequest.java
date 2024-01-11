package com.sunset.rider.lab.msvclabhotel.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotEmpty(message = "El description no debe ser vacio o nulo")
    private String description;

    @NotNull(message = "las estrellas no debe ser vacio o nulo")

    private  Float stars;

    @NotEmpty(message = "El pais no debe ser vacio o nulo")
    private  String country;
    @NotEmpty(message = "La ciudad no debe ser vacio o nulo")
    private String city;
    @NotEmpty(message = "La historia no debe ser vacio o nulo")
    private String history;






}
