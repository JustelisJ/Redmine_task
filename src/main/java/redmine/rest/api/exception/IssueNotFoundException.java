package redmine.rest.api.exception;

public class IssueNotFoundException extends Exception {

    public static final String ISSUE_NOT_FOUNT_MESSAGE = "No such issue exists";
    public static final String ISSUE_DOES_NOT_EXIST = "\" issue does not exist";

    public IssueNotFoundException() {
        super(ISSUE_NOT_FOUNT_MESSAGE);
    }

    public IssueNotFoundException(String name) {
        super("\"" + name + ISSUE_DOES_NOT_EXIST);
    }
}
