package redmine.rest.api.exception;

public class UserNotFoundException extends Exception {

    public static final String USER_NOT_FOUND_MESSAGE = "No such user exists. User is required";
    public static final String USER_DOES_NOT_EXIST = "\" user does not exist";

    public UserNotFoundException() {
        super(USER_NOT_FOUND_MESSAGE);
    }

    public UserNotFoundException(String name) {
        super("\"" + name + USER_DOES_NOT_EXIST);
    }
}
