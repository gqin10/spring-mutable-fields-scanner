package com.qode.mutablescanner.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:throwErrorOnMutableField.properties")
public class ThrowErrorOnMutableFieldFoundConfig extends HasMutableExpectFailConfig {

}
