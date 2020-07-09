package wkda.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan(basePackages = {"wkda"})
@PropertySources({
        @PropertySource("classpath:/test-data-provider.properties")
})

public class AppConfig {
}
