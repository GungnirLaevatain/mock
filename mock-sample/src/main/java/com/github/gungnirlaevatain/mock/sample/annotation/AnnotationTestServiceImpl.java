package com.github.gungnirlaevatain.mock.sample.annotation;

import com.github.gungnirlaevatain.mock.annotation.MockPoint;
import com.github.gungnirlaevatain.mock.sample.handler.MockHandlerBaseOnAnnotation;
import com.github.gungnirlaevatain.mock.sample.handler.MockHandlerBaseOnInterface;
import org.springframework.stereotype.Service;

@Service
public class AnnotationTestServiceImpl implements AnnotationTestService {
    @Override
    @MockPoint(hanlder = MockHandlerBaseOnAnnotation.class)
    public int testInt(String name, Integer id) {
        return 0;
    }

    @Override
    @MockPoint(hanlder = MockHandlerBaseOnInterface.class)
    public int testInt(Integer id) {
        return 0;
    }

    @Override
    @MockPoint(hanlder = MockHandlerBaseOnInterface.class)
    public String testString(String name) {
        return null;
    }
}
