package com.qode.mutablescanner;

import com.qode.mutablescanner.exception.MutableFieldNotAllowedException;
import com.qode.mutablescanner.scanner.FieldScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MutableScannerPostProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MutableScannerPostProcessor.class);

    private static final String EXCEPTION_REASON = "fields are global variable";

    private final ConfigurableListableBeanFactory beanFactory;

    private final List<String> basePackages;

    private final boolean isContinueOnMutableFieldFound;

    private Map<String, List<String>> beanMutableFieldMap;

    @Autowired
    public MutableScannerPostProcessor(
            ConfigurableListableBeanFactory beanFactory, String isContinueOnMutableFieldFound, String... basePackages) {
        this.beanFactory = beanFactory;
        this.isContinueOnMutableFieldFound = "Y".equals(isContinueOnMutableFieldFound);

        if (basePackages.length == 0) {
            LOGGER.info("no basePackage is passed");
        }
        this.basePackages = Arrays.asList(basePackages);

        if (this.isContinueOnMutableFieldFound) {
            beanMutableFieldMap = new HashMap<>();
        }
    }

    private boolean isWithinScanBasePackages(String packageName) {
        for (String basePackage : basePackages) {
            if (packageName.startsWith(basePackage)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        String packageName = bean.getClass().getPackageName();
        if (!isWithinScanBasePackages(packageName)) {
            return bean;
        }

        if (beanFactory.containsBeanDefinition(beanName) && !beanFactory.getBeanDefinition(beanName).isSingleton()) {
            return bean;
        }

        List<String> mutableFields = FieldScanner.scan(bean.getClass(), beanFactory);

        if (!this.isContinueOnMutableFieldFound && !mutableFields.isEmpty()) {
            throw new MutableFieldNotAllowedException(String.format("field %s not allowed, reason [%s]", mutableFields, EXCEPTION_REASON));
        } else if (!mutableFields.isEmpty()) {
            LOGGER.warn("fields [{}] not allowed in [{}], reason [{}]", mutableFields, beanName, EXCEPTION_REASON);
            beanMutableFieldMap.put(beanName, mutableFields);
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    public Map<String, List<String>> getBeanMutableFieldMap() {
        return beanMutableFieldMap;
    }

}
