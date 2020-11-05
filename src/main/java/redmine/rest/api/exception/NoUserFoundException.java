package redmine.rest.api.exception;

public class NoUserFoundException extends RuntimeException {

    public NoUserFoundException() {
        super("No such user exists. User is required");
    }

    public NoUserFoundException(String message) {
        super(message);
    }
}
