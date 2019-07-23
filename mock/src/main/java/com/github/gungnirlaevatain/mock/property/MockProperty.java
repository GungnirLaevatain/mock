package com.github.gungnirlaevatain.mock.property;

import com.github.gungnirlaevatain.mock.entity.MockEntity;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class Mock property.
 *
 * @author gungnirlaevatain
 * @version 2019 -06-24 16:22:44
 * @since JDK 1.8
 */
@Data
public class MockProperty {

    public static final String PREFIX = "mock";
    public static final String RESOURCE = "classpath:mock/*.yml";

    private List<MockEntity> entities = new ArrayList<>();

    public void merge(MockProperty mockProperty) {
        List<MockEntity> extraEntities = mockProperty.getEntities();
        Map<String, MockEntity> entityMap = new HashMap<>(entities.size());
        entities.forEach(entity -> entityMap.put(entity.getClassName(), entity));
        extraEntities.stream()
                .filter(entity -> !StringUtils.isEmpty(entity.getClassName()))
                .forEach(entity -> {
                    if (entityMap.containsKey(entity.getClassName())) {
                        entityMap.get(entity.getClassName()).merge(entity);
                    } else {
                        entityMap.put(entity.getClassName(), entity);
                        entities.add(entity);
                    }
                });

    }

}
