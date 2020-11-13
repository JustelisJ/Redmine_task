package redmine.rest.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder,
                                     @Value("${redmine.logger.username}") String username,
                                     @Value("${redmine.logger.password}") String password,
                                     ObjectMapper mapper) {
        RestTemplate template = restTemplateBuilder.build();
        template.getInterceptors().add(new BasicAuthenticationInterceptor(username, password));
        template.getMessageConverters().add(0, createMappingJacksonHttpMessageConverter(mapper));
        return template;
    }

    private MappingJackson2HttpMessageConverter createMappingJacksonHttpMessageConverter(ObjectMapper mapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(mapper);
        return converter;
    }

}
