package zerobase.demo.exception;

public class NonExistentStoreException extends RuntimeException{
	private static String MESSAGE = "존재하지 않는 점포입니다. 요청한 점포 id : ";

	public NonExistentStoreException(Integer id) {
		super(MESSAGE + id);
	}
}
