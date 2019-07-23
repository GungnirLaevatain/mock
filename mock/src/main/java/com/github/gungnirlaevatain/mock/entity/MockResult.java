package com.github.gungnirlaevatain.mock.entity;

import lombok.Data;

/**
 * The class Mock result.
 * 结果配置
 *
 * @author gungnirlaevatain
 * @version 2019 -06-24 16:19:00
 * @since JDK 1.8
 */
@Data
public class MockResult {
    /**
     * The Path.
     * JsonPath取值的路径
     */
    private String path;
    /**
     * The Expected.
     * 期望值
     */
    private String expected;
    /**
     * The Result.
     * 取到的值和期望值一致时返回的结果值
     */
    private String result;

    public void merge(MockResult mockResult) {

    }
}
