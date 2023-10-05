package com.integrated.techhub.common.config;

import com.integrated.techhub.common.auth.jwt.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class PropertiesConfig {

}
