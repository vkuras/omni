package config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = {"config", "annotations", "constants","base","org.openapitools"})
@EnableFeignClients(basePackages = {"org.openapitools"})
public class TestConfig {
}
