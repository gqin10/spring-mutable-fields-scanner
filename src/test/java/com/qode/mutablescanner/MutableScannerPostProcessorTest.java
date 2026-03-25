package com.qode.mutablescanner;

import com.qode.mutablescanner.config.AbstractTestConfig;
import com.qode.mutablescanner.config.BeanOutsideScanPackageExpectSuccessConfig;
import com.qode.mutablescanner.config.ExpectSuccessConfig;
import com.qode.mutablescanner.config.HasMutableExpectFailConfig;
import com.qode.mutablescanner.exception.MutableFieldNotAllowedException;
import com.qode.mutablescanner.service.impl.ChildServiceB;
import com.qode.mutablescanner.service.noscanimpl.NoScanService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MutableScannerPostProcessorTest {

    @Test
    public void beanHasNoMutableFields_expectSuccess() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ExpectSuccessConfig.class);
        context.scan(AbstractTestConfig.IMPL_PACKAGES);
        Assertions.assertNotNull(context.getBean(MutableScannerPostProcessor.class));
        Assertions.assertNotNull(context.getBean(ChildServiceB.class));
    }

    @Test
    public void testNoBeanForNoScanPackage_expectSuccess() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ExpectSuccessConfig.class);
        context.scan(AbstractTestConfig.IMPL_PACKAGES);
        Assertions.assertEquals(0, context.getBeansOfType(NoScanService.class).size());
    }

    @Test
    public void beanHasMutableField_expectFail() {
        try {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HasMutableExpectFailConfig.class);
            context.scan(AbstractTestConfig.IMPL_PACKAGES, AbstractTestConfig.NOSCAN_PACKAGES);
            Assertions.assertNotNull(context.getBean(MutableScannerPostProcessor.class));
        } catch (Exception e) {
            Assertions.assertEquals(MutableFieldNotAllowedException.class, e.getCause().getClass());
        }
    }

    @Test
    public void testBeanOutsideScanPackage_beanHasMutableField_expectSuccess() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanOutsideScanPackageExpectSuccessConfig.class);
        context.scan(AbstractTestConfig.IMPL_PACKAGES); // does not scan noscanimpl
        Assertions.assertNotNull(context.getBean(MutableScannerPostProcessor.class));
    }

}
