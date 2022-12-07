package zerobase.demo.common.exception;

public class UserNotFindException extends RuntimeException {
    public UserNotFindException(String error) {
        super(error);
    }
}
