package redmine.rest.api.exception;

public class NoIssueFoundException extends RuntimeException {

    public NoIssueFoundException() {
        super("No such issue exists. Issue is required");
    }

    public NoIssueFoundException(String message) {
        super(message);
    }
}
