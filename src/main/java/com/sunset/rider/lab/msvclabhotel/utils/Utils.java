package com.sunset.rider.lab.msvclabhotel.utils;

import com.sunset.rider.lab.exceptions.exception.HeaderExeption;
import org.springframework.util.MultiValueMap;


import java.time.LocalDateTime;
import java.util.*;

public class Utils {


    public static void validHeaders(MultiValueMap<String, String> headers,List<String> headersValid) {

        List<String> headersOut = new ArrayList<>();

        List<String> headersin = new ArrayList<>();
        headers.entrySet().stream().forEach(k -> {
            headersin.add(k.getKey());

        });

        headersValid.forEach(h -> {
            if (!headersin.contains(h)) {
                headersOut.add(h);
            }
        });

        if(!headersOut.isEmpty()){
            Map<String,Object> headersRe= new HashMap<>();

            headersRe.put("Error","Headers are missing : "+headersOut.toString());
            headersRe.put("timestamp", LocalDateTime.now());

            throw new HeaderExeption(headersRe);

        }

    }
}
