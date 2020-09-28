package app.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = {"app"})
@EnableFeignClients(basePackages = {"org.openapitools"})
@PropertySources({
        @PropertySource("classpath:/application.properties")
})
public class TestConfig {
}
