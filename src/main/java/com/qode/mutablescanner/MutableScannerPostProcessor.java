package com.qode.mutablescanner;

import com.qode.mutablescanner.scanner.FieldScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MutableScannerPostProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MutableScannerPostProcessor.class);

    private final ConfigurableListableBeanFactory beanFactory;

    @Autowired
    public MutableScannerPostProcessor(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!bean.getClass().getPackageName().contains("com.qode.mutablescanner")) {
            // skip scan
            return bean;
        }

        if (beanFactory.containsBeanDefinition(beanName) && !beanFactory.getBeanDefinition(beanName).isSingleton()) {
            return bean;
        }

        FieldScanner.scan(bean.getClass(), beanFactory);
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

}
