package com.andrei.taskmicroservice.utils.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "task.api")
public class TaskAPIProperties {

    @Value("${task.api.base.urlFirst}")
    private String baseUrlFirst;

    @Value("${task.api.base.urlSecond}")
    private String baseUrlSecond;

    @Value("${callUrlFirst}")
    private String testBaseUrlFirst;

    @Value("${callUrlSecond}")
    private String testBaseUrlSecond;

    @Value("${header}")
    private String header;
}
