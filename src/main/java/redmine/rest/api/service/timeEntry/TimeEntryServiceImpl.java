package redmine.rest.api.service.timeEntry;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.model.redmineData.TimeEntityData;

import java.nio.charset.Charset;

@Service
public class TimeEntryServiceImpl implements TimeEntryService {

    private RestTemplate restTemplate;
    private final String url;

    public TimeEntryServiceImpl(RestTemplate restTemplate, @Value("${redmine.url}") String url) {
        this.restTemplate = restTemplate;
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "password"));
        this.url = url;
    }

    @Override
    public TimeEntityData getTimeEntries() {
        TimeEntityData timeEntries = restTemplate.getForObject(url+"/time_entries.json", TimeEntityData.class);
        return timeEntries;
    }
}
