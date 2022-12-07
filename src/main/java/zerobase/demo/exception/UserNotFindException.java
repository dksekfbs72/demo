package zerobase.demo.exception;

public class UserNotFindException extends RuntimeException {
    public UserNotFindException(String error) {
        super(error);
    }
}
