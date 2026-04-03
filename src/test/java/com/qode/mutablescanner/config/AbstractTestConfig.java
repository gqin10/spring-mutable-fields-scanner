package com.qode.mutablescanner.config;

import com.qode.mutablescanner.MutableScannerPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class AbstractTestConfig {

    public static final String IMPL_PACKAGES = "com.qode.mutablescanner.service.impl";

    public static final String NOSCAN_PACKAGES = "com.qode.mutablescanner.service.noscanimpl";

    public static final String SCAN_MUTABLE_PACKAGES = "com.qode.mutablescanner.service.mutableimpl";

    @Autowired
    protected ConfigurableListableBeanFactory beanFactory;

    @Value("${mutableScanner.isContinueOnMutableFieldFound:N}")
    private String continueOnMutableFieldFlag;

    @Bean
    public MutableScannerPostProcessor mutableScannerPostProcessor() {
        return new MutableScannerPostProcessor(beanFactory, continueOnMutableFieldFlag, IMPL_PACKAGES, SCAN_MUTABLE_PACKAGES);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
