package com.qode.mutablescanner.config;

import com.qode.mutablescanner.MutableScannerPostProcessor;

public class BeanOutsideScanPackageExpectSuccessConfig extends AbstractTestConfig {

    @Override
    public MutableScannerPostProcessor mutableScannerPostProcessor() {
        // exclude noscanimpl package
        return new MutableScannerPostProcessor(beanFactory, "com.qode.mutable.scanner.service.impl");
    }
}
