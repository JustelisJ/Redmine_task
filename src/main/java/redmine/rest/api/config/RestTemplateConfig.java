package redmine.rest.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder,
                                     @Value("${redmine_logger_username}") String username,
                                     @Value("${redmine_logger_password}") String password) {
        RestTemplate template = restTemplateBuilder.build();
        template.getInterceptors().add(new BasicAuthenticationInterceptor(username, password));
        return template;
    }

}
