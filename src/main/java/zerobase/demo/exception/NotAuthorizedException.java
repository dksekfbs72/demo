package zerobase.demo.exception;

public class NotAuthorizedException extends RuntimeException{
	private static final String MESSAGE = "권한이 없습니다.";

	public NotAuthorizedException() {
		super(MESSAGE);
	}
}
