package com.sunset.rider.msvclabhotel.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Data
@AllArgsConstructor
public class HeaderExeption extends RuntimeException{

    Map<String,Object> headersRe;
}
