package zerobase.demo.exception;

public class NonExistentUserException extends RuntimeException{
	private static final String MESSAGE = "존재하지 않는 사용자입니다.";

	public NonExistentUserException() {
		super(MESSAGE);
	}
}
