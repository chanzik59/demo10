package com.example.config;

import com.example.condition.UserServiceCondition;
import com.example.entity.UserProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author czq
 * @date 2023/10/18 10:32
 * @Description:
 */
@Conditional(UserServiceCondition.class)
@EnableConfigurationProperties(UserProperties.class)
@Configuration
public class UserConfig {
}
