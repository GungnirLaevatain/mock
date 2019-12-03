package com.github.gungnirlaevatain.mock.sample.handler;

import com.github.gungnirlaevatain.mock.annotation.MockHandler;

@MockHandler
public class MockHandlerBaseOnAnnotation {

    public int testInt(String name, Integer id) {
        if (id == null) {
            return 1111;
        }
        return id;
    }
}
