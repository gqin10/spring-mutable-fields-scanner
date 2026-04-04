package com.qode.mutablescanner;

import com.qode.mutablescanner.config.*;
import com.qode.mutablescanner.exception.MutableFieldNotAllowedException;
import com.qode.mutablescanner.service.impl.ChildServiceA;
import com.qode.mutablescanner.service.impl.ChildServiceB;
import com.qode.mutablescanner.service.impl.HasAllowMutableFieldService;
import com.qode.mutablescanner.service.mutableimpl.ScanHasMutableService;
import com.qode.mutablescanner.service.noscanimpl.NoScanService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MutableScannerPostProcessorTest {

    @Test
    public void beanHasNoMutableFields_expectSuccess() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ExpectSuccessConfig.class);
        context.scan(AbstractTestConfig.IMPL_PACKAGES);
        context.refresh();
        Assertions.assertNotNull(context.getBean(MutableScannerPostProcessor.class));
        Assertions.assertNotNull(context.getBean(ChildServiceB.class));
        Assertions.assertNotNull(context.getBean(ChildServiceA.class));
    }

    @Test
    public void testNoBeanForNoScanPackage_expectSuccess() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ExpectSuccessConfig.class);
        context.scan(AbstractTestConfig.IMPL_PACKAGES);
        context.refresh();
        Exception exception = null;
        try {
            Assertions.assertNull(context.getBean(NoScanService.class));
        } catch (NoSuchBeanDefinitionException e) {
            exception = e;
        }
        if (exception == null) {
            Assertions.fail("No NoSuchBeanDefinitionException thrown");
        }
    }

    @Test
    public void beanHasMutableField_expectFail() {
        Exception exception = null;
        try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
            context.register(HasMutableExpectFailConfig.class);
            context.scan(AbstractTestConfig.IMPL_PACKAGES, AbstractTestConfig.NOSCAN_PACKAGES, AbstractTestConfig.SCAN_MUTABLE_PACKAGES);
            context.refresh();
            Assertions.assertNotNull(context.getBean(MutableScannerPostProcessor.class));
            ScanHasMutableService scanHasMutableService = context.getBean(ScanHasMutableService.class);
        } catch (Exception e) {
            exception = e;
            Assertions.assertEquals(MutableFieldNotAllowedException.class, e.getCause().getClass());
        }
        if (exception == null) {
            Assertions.fail("No MutableFieldNotAllowedException thrown");
        }
    }

    @Test
    public void testBeanOutsideScanPackage_beanHasMutableField_expectSuccess() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(BeanOutsideScanPackageExpectSuccessConfig.class);
        context.scan(AbstractTestConfig.IMPL_PACKAGES); // does not scan noscanimpl
        context.refresh();
        Assertions.assertNotNull(context.getBean(MutableScannerPostProcessor.class));
    }

    @Test
    public void testAllowMutableAnnotation_expectSuccess() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ExpectSuccessConfig.class);
        context.scan(AbstractTestConfig.IMPL_PACKAGES);
        context.refresh();
        Assertions.assertEquals(0, context.getBeansOfType(NoScanService.class).size());

        HasAllowMutableFieldService hasMutableService = context.getBean(HasAllowMutableFieldService.class);
        Assertions.assertEquals(HasAllowMutableFieldService.DEFAULT_VALUE, hasMutableService.getAllowMutableValue());
        hasMutableService.setAllowMutableValue("newValue");
        Assertions.assertEquals("newValue", hasMutableService.getAllowMutableValue());
    }

    @Test
    public void testContinueOnMutableFieldFoundConfig_expectNoError() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ContinueOnMutableFieldFoundConfig.class);
        context.scan(AbstractTestConfig.IMPL_PACKAGES, AbstractTestConfig.NOSCAN_PACKAGES, AbstractTestConfig.SCAN_MUTABLE_PACKAGES);
        context.refresh();
        MutableScannerPostProcessor postProcessor = context.getBean(MutableScannerPostProcessor.class);
        Assertions.assertNotNull(postProcessor);
        Assertions.assertNotNull(context.getBean(ScanHasMutableService.class));
        Assertions.assertEquals(1, postProcessor.getBeanMutableFieldMap().size());
        Assertions.assertNotNull(postProcessor.getBeanMutableFieldMap().get("scanHasMutableService"));
        Assertions.assertEquals("notAllowedMutableField", postProcessor.getBeanMutableFieldMap().get("scanHasMutableService").get(0));
    }

    @Test
    public void testConfigureNotContinueOnMutableFieldFound_expectException() {
        Exception exception = null;
        try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
            context.register(HasMutableExpectFailConfig.class);
            context.scan(AbstractTestConfig.IMPL_PACKAGES, AbstractTestConfig.NOSCAN_PACKAGES, AbstractTestConfig.SCAN_MUTABLE_PACKAGES);
            context.refresh();
            Assertions.assertNotNull(context.getBean(MutableScannerPostProcessor.class));
            ScanHasMutableService scanHasMutableService = context.getBean(ScanHasMutableService.class);
        } catch (Exception e) {
            exception = e;
            Assertions.assertEquals(MutableFieldNotAllowedException.class, e.getCause().getClass());
        }
        if (exception == null) {
            Assertions.fail("No MutableFieldNotAllowedException thrown");
        }
    }

}
