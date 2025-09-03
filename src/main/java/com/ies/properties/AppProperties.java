package com.ies.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "admin")
public class AppProperties {


    // It is used to load application properties
    private Map<String, String> message = new HashMap<>();
}
