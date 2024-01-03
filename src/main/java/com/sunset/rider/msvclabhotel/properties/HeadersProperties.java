package com.sunset.rider.msvclabhotel.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "headers" )
@Component
public class HeadersProperties {

    private List<String> required;
}
