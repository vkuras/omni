package app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
//TODO use onee pase package later
@Configuration
@ComponentScan(basePackages = {"app"})
@PropertySources({
        @PropertySource("classpath:/test-data-provider-service.properties")
})

public class AppConfig {
}
