package zerobase.demo.exception;

public class UserNotEmailAuthException extends RuntimeException {
    public UserNotEmailAuthException(String error) {
        super(error);
    }
}
