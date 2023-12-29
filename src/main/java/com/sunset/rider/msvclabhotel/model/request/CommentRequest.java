package com.sunset.rider.msvclabhotel.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    @NotEmpty(message = "no debe ser vacio o nulo")
    private String guestId;
    @NotEmpty(message = "no debe ser vacio o nulo")
    private String comment;
    @NotEmpty(message = "no debe ser vacio o nulo")
    private String hotelId;
    @NotNull(message = "no debe ser vacio o nulo")
    private Integer stars;
}
