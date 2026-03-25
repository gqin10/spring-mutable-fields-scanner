package com.qode.mutablescanner.service.impl;

import com.qode.mutablescanner.annotation.AllowMutable;
import org.springframework.stereotype.Component;

@Component
public class HasAllowMutableFieldService {

    public static final String DEFAULT_VALUE = "allowMutableValue";

    @AllowMutable
    private String allowMutableValue = DEFAULT_VALUE;

    public void setAllowMutableValue(String allowMutableValue) {
        this.allowMutableValue = allowMutableValue;
    }

    public String getAllowMutableValue() {
        return allowMutableValue;
    }
}
