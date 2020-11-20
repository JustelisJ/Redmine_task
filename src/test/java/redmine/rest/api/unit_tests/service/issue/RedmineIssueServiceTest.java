package redmine.rest.api.unit_tests.service.issue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;
import redmine.rest.api.config.RestTemplateConfig;
import redmine.rest.api.service.issue.IssueService;
import redmine.rest.api.service.issue.RedmineIssueService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(RestTemplateConfig.class)
class RedmineIssueServiceTest {

    private static final String NP_1_ISSUE_NAME = "NP-1";
    private static final String NP_3_ISSUE_NAME = "NP-3";
    private static final long ISSUE_ID = 2L;

    @Autowired
    RestTemplate restTemplate;

    IssueService issueService;

    @BeforeEach
    void setUp() {
        issueService = new RedmineIssueService(restTemplate);
    }

    @Test
    void getIssueIdFromName() {
        Optional<Long> id = issueService.getIssueIdFromName(NP_1_ISSUE_NAME);
        assertTrue(id.isPresent());
        assertEquals(ISSUE_ID, id.get());
    }

    @Test
    void dontGetIssueIdFromName() {
        Optional<Long> id = issueService.getIssueIdFromName(NP_3_ISSUE_NAME);
        assertTrue(id.isEmpty());
    }
}