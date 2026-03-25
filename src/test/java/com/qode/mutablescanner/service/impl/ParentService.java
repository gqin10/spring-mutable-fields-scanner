package com.qode.mutablescanner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParentService {

    private final ChildServiceA childServiceA;

    @Autowired
    private ChildServiceB childServiceB;

    @Autowired
    public ParentService(ChildServiceA childServiceA) {
        this.childServiceA = childServiceA;
    }
}
