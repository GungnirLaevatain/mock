package com.github.gungnirlaevatain.mock.sample.handler;

import com.github.gungnirlaevatain.mock.annotation.MockHandler;
import com.github.gungnirlaevatain.mock.sample.TestResult;

import java.util.Date;

@MockHandler(value = "mockHandler")
public class MockHandlerBaseOnAnnotation {

    public int testInt(String name, Integer id) {
        if (id == null) {
            return 1111;
        }
        return id;
    }

    public TestResult testTestResult(String a, Date c) {
        TestResult testResult = new TestResult();
        testResult.setA(a);
        testResult.setC(c);
        return testResult;
    }
}
