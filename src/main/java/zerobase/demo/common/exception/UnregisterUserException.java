package zerobase.demo.common.exception;

public class UnregisterUserException extends RuntimeException {
    public UnregisterUserException(String error) {
        super(error);
    }
}
