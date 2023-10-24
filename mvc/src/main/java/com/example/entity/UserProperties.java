package com.example.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author czq
 * @date 2023/10/18 10:28
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "aa")
public class UserProperties {

    private String name;
}
