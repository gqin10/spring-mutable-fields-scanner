package com.qode.mutablescanner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;

public class ContinueOnMutableFieldFoundConfig extends HasMutableExpectFailConfig {

    @Bean
    public MapPropertySource propertySource() {
        return new MapPropertySource(
                "propertySource",
                Map.of("mutableScanner.isContinueOnMutableFieldFound", "Y")
        );
    }
}
