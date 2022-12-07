package zerobase.demo.common.exception;

public class UserNotEmailAuthException extends RuntimeException {
    public UserNotEmailAuthException(String error) {
        super(error);
    }
}
