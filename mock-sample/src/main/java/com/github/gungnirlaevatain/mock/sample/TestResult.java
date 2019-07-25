package com.github.gungnirlaevatain.mock.sample;

import lombok.Data;

import java.util.Date;

/**
 * The class Proxy test result.
 * 测试的对象返回值
 *
 * @author GungnirLaevatain
 * @version 2019 -07-22 23:08:07
 * @since 1.0
 */
@Data
public class TestResult {

    private String a;
    private Integer b;
    private Date c;
    private TestResult d;
}
