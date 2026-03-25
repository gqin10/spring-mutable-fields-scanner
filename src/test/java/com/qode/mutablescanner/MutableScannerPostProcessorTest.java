package com.qode.mutablescanner;

import com.qode.mutablescanner.exception.MutableFieldNotAllowedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MutableScannerPostProcessorTest {

    @Test
    public void expectSuccess() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
    }

    @Test
    public void expectFail() {
        try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestFailCaseConfig.class);
        } catch (Exception e) {
            Assertions.assertEquals(MutableFieldNotAllowedException.class, e.getCause().getClass());
        }
    }

}
