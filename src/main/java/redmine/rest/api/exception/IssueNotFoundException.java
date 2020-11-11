package redmine.rest.api.exception;

public class IssueNotFoundException extends RuntimeException {

    public IssueNotFoundException() {
        super("No such issue exists. Issue is required");
    }

    public IssueNotFoundException(String message) {
        super(message);
    }
}
