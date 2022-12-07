package zerobase.demo.common.exception;

public class NonExistentUserException extends RuntimeException{
	private static String MESSAGE = "존재하지 않는 사용자입니다. 요청한 유저 id : ";

	public NonExistentUserException(Integer userId) {
		super(MESSAGE + userId);
	}
}
