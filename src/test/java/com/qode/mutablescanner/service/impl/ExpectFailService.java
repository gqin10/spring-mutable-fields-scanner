package com.qode.mutablescanner.service.impl;

import com.qode.mutablescanner.service.IExpectFailService;

public class ExpectFailService implements IExpectFailService {

    // this class should hit error when scanning
    private String anyValue;

    @Override
    public void expectFail() {
        // do nothing
    }
}
