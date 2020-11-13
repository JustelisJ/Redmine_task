package redmine.rest.api.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.simpleDateFormat("yyyy-MM-dd");
        builder.failOnUnknownProperties(false);
        builder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        builder.autoDetectFields(true);
        builder.featuresToEnable(SerializationFeature.WRAP_ROOT_VALUE);
        builder.featuresToDisable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
        return builder;
    }

}
