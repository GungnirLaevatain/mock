package com.github.gungnirlaevatain.mock.sample.annotation;

import com.github.gungnirlaevatain.mock.sample.TestResult;

import java.util.Date;

public interface AnnotationTestService {

    int testInt(String name, Integer id);

    int testInt(Integer id);

    String testString(String name);

    TestResult testTestResult(String name, Date date);
}
