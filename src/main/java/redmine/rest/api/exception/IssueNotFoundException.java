package redmine.rest.api.exception;

public class IssueNotFoundException extends RuntimeException {

    public static final String ISSUE_NOT_FOUNT_MESSAGE = "No such issue exists. Issue is required";

    public IssueNotFoundException() {
        super(ISSUE_NOT_FOUNT_MESSAGE);
    }

    public IssueNotFoundException(String message) {
        super(message);
    }
}
