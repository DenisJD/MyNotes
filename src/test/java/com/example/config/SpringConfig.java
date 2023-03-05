package com.example.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

import static com.example.config.SpringConfig.TEST_PROFILE;

@Configuration
@Profile(TEST_PROFILE)
@ComponentScan(basePackages = "com.example")
@PropertySource(value = "classpath:/config/application.yml")
public class SpringConfig {

    public static final String TEST_PROFILE = "test";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }
}
