package com.qode.mutablescanner;

import com.qode.mutablescanner.service.IUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        TestConfig.class
})
public class MutableScannerPostProcessorTest {

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private IUserService userService;

    @Test
    public void successTest() {
        // prepare context no error
    }
}
