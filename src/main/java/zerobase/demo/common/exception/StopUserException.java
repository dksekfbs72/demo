package zerobase.demo.common.exception;

public class StopUserException extends RuntimeException {
    public StopUserException(String error) {
        super(error);
    }
}
