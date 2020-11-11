package redmine.rest.api.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("No such user exists. User is required");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
