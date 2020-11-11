package redmine.rest.api.exception;

public class UserNotFoundException extends RuntimeException {

    public static final String USER_NOT_FOUND_MESSAGE = "No such user exists. User is required";

    public UserNotFoundException() {
        super(USER_NOT_FOUND_MESSAGE);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
