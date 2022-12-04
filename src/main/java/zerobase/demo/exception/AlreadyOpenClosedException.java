package zerobase.demo.exception;

public class AlreadyOpenClosedException extends RuntimeException{
	private static String MESSAGE = "이미 열려있거나 닫혀있습니다.";

	public AlreadyOpenClosedException() {
		super(MESSAGE);
	}
}
