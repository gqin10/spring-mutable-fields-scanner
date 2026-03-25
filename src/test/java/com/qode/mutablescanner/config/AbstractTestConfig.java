package com.qode.mutablescanner.config;

import com.qode.mutablescanner.MutableScannerPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;

public class AbstractTestConfig {

    public static final String IMPL_PACKAGES = "com.qode.mutablescanner.service.impl";

    public static final String NOSCAN_PACKAGES = "com.qode.mutablescanner.service.noscanimpl";

    @Autowired
    protected ConfigurableListableBeanFactory beanFactory;

    @Bean
    public MutableScannerPostProcessor mutableScannerPostProcessor() {
        return new MutableScannerPostProcessor(beanFactory, IMPL_PACKAGES);
    }

}
