package com.github.gungnirlaevatain.mock.sample.proxy;

import com.github.gungnirlaevatain.mock.sample.MockSampleApplication;
import com.github.gungnirlaevatain.mock.sample.annotation.AnnotationTestService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MockSampleApplication.class)
public class AnnotationTest {

    @Autowired
    private AnnotationTestService annotationTestService;


    @Test
    public void testInt() {
        int result = annotationTestService.testInt("a", null);
        Assert.assertEquals(1111, result);
        result = annotationTestService.testInt("bb", 1234);
        Assert.assertEquals(1234, result);
    }

    @Test
    public void testInt2() {
        int result = annotationTestService.testInt(1);
        Assert.assertEquals(2222, result);
    }

    @Test
    public void testString() {
        String result = annotationTestService.testString("111");
        Assert.assertEquals("1111", result);
    }

}
