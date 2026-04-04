package com.qode.mutablescanner;

import com.qode.mutablescanner.writer.AbstractMutableFieldWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MutableScannerApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MutableScannerApplicationListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        MutableScannerPostProcessor postProcessor = null;
        try {
            postProcessor = event.getApplicationContext().getBean(MutableScannerPostProcessor.class);
            if (postProcessor.getBeanMutableFieldMap() == null || postProcessor.getBeanMutableFieldMap().isEmpty()) {
                return;
            }
            postProcessor.getBeanMutableFieldMap().forEach((key, mutableFieldList) -> {
                LOGGER.warn("beanName [{}] has mutable fields {}", key, mutableFieldList);
            });
        } catch (BeansException e) {
            LOGGER.error("hit error", e);
        }

        if (postProcessor == null) {
            return;
        }

        final Map<String, List<String>> mutableFieldMap = postProcessor.getBeanMutableFieldMap();
        List<AbstractMutableFieldWriter> writers = getMutableFieldWriters(event.getApplicationContext());
        writers.forEach(writer -> {
            try {
                writer.write(mutableFieldMap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private List<AbstractMutableFieldWriter> getMutableFieldWriters(ApplicationContext appContext) {
        return appContext.getBeansOfType(AbstractMutableFieldWriter.class).values().stream().toList();
    }
}
