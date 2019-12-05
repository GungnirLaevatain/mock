package com.github.gungnirlaevatain.mock.sample.annotation;

import com.github.gungnirlaevatain.mock.annotation.MockPoint;
import com.github.gungnirlaevatain.mock.sample.TestResult;
import com.github.gungnirlaevatain.mock.sample.handler.MockHandlerBaseOnAnnotation;
import com.github.gungnirlaevatain.mock.sample.handler.MockHandlerBaseOnInterface;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AnnotationTestServiceImpl implements AnnotationTestService {
    @Override
    @MockPoint(handler = MockHandlerBaseOnAnnotation.class)
    public int testInt(String name, Integer id) {
        return 0;
    }

    @Override
    @MockPoint(handler = MockHandlerBaseOnInterface.class)
    public int testInt(Integer id) {
        return 0;
    }

    @Override
    @MockPoint(handler = MockHandlerBaseOnInterface.class)
    public String testString(String name) {
        return null;
    }

    @Override
    @MockPoint(handlerName = "mockHandler")
    public TestResult testTestResult(String name, Date date) {
        return null;
    }
}
