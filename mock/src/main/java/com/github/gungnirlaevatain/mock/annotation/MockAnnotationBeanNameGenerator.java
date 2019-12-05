package com.github.gungnirlaevatain.mock.annotation;

import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import java.util.Map;
import java.util.Set;

public class MockAnnotationBeanNameGenerator extends AnnotationBeanNameGenerator {

    @Override
    protected boolean isStereotypeWithNameValue(String annotationType, Set<String> metaAnnotationTypes, Map<String, Object> attributes) {
        boolean result = super.isStereotypeWithNameValue(annotationType, metaAnnotationTypes, attributes);
        if (result) {
            return true;
        }
        boolean isStereotype = annotationType.equals(MockHandler.class.getName()) ||
                metaAnnotationTypes.contains(MockHandler.class.getName());

        return (isStereotype && attributes != null && attributes.containsKey("value"));
    }
}
