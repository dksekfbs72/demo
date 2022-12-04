package zerobase.demo.exception;

public class NonExistentStoreException extends RuntimeException{
	private static final String MESSAGE = "존재하지 않는 점포입니다.";

	public NonExistentStoreException() {
		super(MESSAGE);
	}
}
