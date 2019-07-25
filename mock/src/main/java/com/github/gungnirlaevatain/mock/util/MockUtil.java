package com.github.gungnirlaevatain.mock.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.github.gungnirlaevatain.mock.entity.MockEntity;
import com.github.gungnirlaevatain.mock.entity.MockMethod;
import com.github.gungnirlaevatain.mock.entity.MockResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * The class Mock util.
 * mock时使用的工具类
 *
 * @author gungnirlaevatain
 * @version 2019 -06-25 16:24:26
 * @since 1.0
 */
@Slf4j
public class MockUtil {

    /**
     * Find result from mock method.
     * 对应的mock方法配置内根据入参找到对应的返回值
     *
     * @param mock    the mock
     * @param objects the objects
     * @return the string
     * @author gungnirlaevatain
     */
    public static String findResultFromMockMethod(MockMethod mock, Object[] objects) {
        String result = mock.getDefaultResult();
        for (MockResult mockResult : mock.getResults()) {
            Object res = JSONPath.eval(objects, mockResult.getPath());
            if (res == null && mockResult.getExpected() == null) {
                result = null;
                break;
            } else if (res != null) {
                if (String.valueOf(res).equals(mockResult.getExpected())) {
                    result = mockResult.getResult();
                    break;
                }
            }
        }
        return result;
    }


    /**
     * Create result.
     * 根据返回类型构建返回值
     *
     * @param result     the result
     * @param returnType the return type
     * @return the object
     * @author gungnirlaevatain
     */
    public static Object createResult(String result, Class<?> returnType) {
        if (result == null || returnType == Void.class) {
            return null;
        }
        if (ClassUtil.isBaseClass(returnType)) {
            return ClassUtil.convertBaseValue(returnType, result);
        } else if (Date.class == returnType) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return format.parse(result);
            } catch (ParseException e) {
                log.error("can not parse source to date for {}", result, e);
                return null;
            }
        } else {
            return JSON.parseObject(result, returnType);
        }
    }


    public static void registerMockEntity(MockEntity entity, List<MockEntity> mockEntities) {
        try {
            if (StringUtils.isEmpty(entity.getClassName())) {
                return;
            }
            entity.setMockClass(Class.forName(entity.getClassName()));
            mockEntities.add(entity);
        } catch (ClassNotFoundException e) {
            log.error("can not found class for class name {} from context", entity.getClassName());
        }
    }
}
