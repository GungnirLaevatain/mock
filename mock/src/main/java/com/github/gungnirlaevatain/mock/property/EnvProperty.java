package com.github.gungnirlaevatain.mock.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

/**
 * The class Env property.
 *
 * @author gungnirlaevatain
 * @version 2019 -10-30 16:17:14
 * @since 1.0
 */
@Data
@ConfigurationProperties(prefix = EnvProperty.PREFIX)
public class EnvProperty {
    public static final String PREFIX = "commons.mock";
    public static final String DEFAULT_LOCATION = "classpath:mock/*.yml";

    private Boolean enable;
    private String configLocation = DEFAULT_LOCATION;
    private List<String> handlerScan = Collections.emptyList();
}
