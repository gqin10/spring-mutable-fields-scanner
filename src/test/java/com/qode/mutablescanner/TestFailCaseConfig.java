package com.qode.mutablescanner;

import com.qode.mutablescanner.service.IExpectFailService;
import com.qode.mutablescanner.service.impl.ExpectFailService;
import org.springframework.context.annotation.Bean;

public class TestFailCaseConfig {

    @Bean
    public IExpectFailService expectFailService() {
        return new ExpectFailService();
    }

}
