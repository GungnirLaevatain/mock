package com.github.gungnirlaevatain.mock.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The class Mock method.
 *
 * @author gungnirlaevatain
 * @version 2019 -06-24 16:19:00
 * @since 1.0
 */
@Data
public class MockMethod {
    /**
     * The Method.
     * 方法名称
     */
    private String method;
    /**
     * The Param class.
     * 参数列表
     */
    private List<String> paramClass = new ArrayList<>();
    /**
     * The Default result.
     * 默认返回值
     */
    private String defaultResult;
    /**
     * The Results.
     * 返回值配置
     */
    private List<MockResult> results = new ArrayList<>();

    public String uniqueName() {
        return method + "#" + paramClass.toString();
    }

    public void merge(MockMethod mockMethod) {
        results.addAll(mockMethod.getResults());
    }
}
