package com.qode.mutablescanner;

import com.qode.mutablescanner.scanner.FieldScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.Arrays;
import java.util.List;

public class MutableScannerPostProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MutableScannerPostProcessor.class);

    private final ConfigurableListableBeanFactory beanFactory;

    private final List<String> basePackages;

    @Autowired
    public MutableScannerPostProcessor(
            ConfigurableListableBeanFactory beanFactory, String... basePackages) {
        this.beanFactory = beanFactory;

        if (basePackages.length == 0) {
            LOGGER.info("no basePackage is passed");
        }
        this.basePackages = Arrays.asList(basePackages);
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

        FieldScanner.scan(bean.getClass(), beanFactory);
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

}
