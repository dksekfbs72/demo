package zerobase.demo.common.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import zerobase.demo.common.exception.AlreadyOpenClosedException;
import zerobase.demo.common.exception.NonExistentStoreException;
import zerobase.demo.common.exception.NonExistentUserException;
import zerobase.demo.common.exception.NotAuthorizedException;

// @RestControllerAdvice(basePackageClasses = OwnerStoreController.class)
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Exception handleAllException() {
		return new Exception("정의되지 않은 예외 발생");
	}

	@ExceptionHandler(NonExistentUserException.class)
	public NonExistentUserException handleNonExistentUserException(NonExistentUserException ex) {
		return ex;
	}

	@ExceptionHandler(NotAuthorizedException.class)
	public NotAuthorizedException handleNotAuthorizedException(NotAuthorizedException ex) {
		return ex;
	}

	@ExceptionHandler(AlreadyOpenClosedException.class)
	public AlreadyOpenClosedException handleAlreadyOpenClosedException(AlreadyOpenClosedException ex) {
		return ex;
	}

	@ExceptionHandler(NonExistentStoreException.class)
	public NonExistentStoreException handleNonExistentStoreException(NonExistentStoreException ex) {
		return ex;
	}
}
